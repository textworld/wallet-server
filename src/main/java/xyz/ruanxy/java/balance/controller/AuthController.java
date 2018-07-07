package xyz.ruanxy.java.balance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.ruanxy.java.balance.exception.AppException;
import xyz.ruanxy.java.balance.model.Role;
import xyz.ruanxy.java.balance.model.RoleName;
import xyz.ruanxy.java.balance.model.User;
import xyz.ruanxy.java.balance.model.WxUser;
import xyz.ruanxy.java.balance.payload.*;
import xyz.ruanxy.java.balance.payload.weixin.JscodeResponse;
import xyz.ruanxy.java.balance.repository.RoleRepository;
import xyz.ruanxy.java.balance.repository.UserRepository;
import xyz.ruanxy.java.balance.repository.WxUserRepository;
import xyz.ruanxy.java.balance.security.JwtTokenProvider;
import xyz.ruanxy.java.balance.service.HttpClientService;
import xyz.ruanxy.java.balance.service.WxUserService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    WxUserRepository wxUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    HttpClientService httpClientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WxUserService wxUserService;

    @Value("${wx.app_id}")
    String appId;

    @Value("${wx.secret}")
    String appSecret;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        logger.info("LoginRequest: {}", loginRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

//    @PostMapping("/wx_signup")
//    public Map<String, String> wxSignUp(@RequestBody WxSignUpRequest signUpRequest){
//
//        try {
//            JscodeResponse response = wxUserService.getJsCode(signUpRequest.getCode());
//            signUpRequest.setOpenId(response.getOpenid());
//
//            User user = wxUserService.create(signUpRequest);
//
//            if(user != null && user.getId() > 0){
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                signUpRequest.getUser().getUsername(),
//                                signUpRequest.getUser().getPassword()
//                        )
//                );
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                String jwt = tokenProvider.generateToken(authentication);
//                Map<String, String> ret = new HashMap<>();
//                ret.put("jwt", jwt);
//                return ret;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    @GetMapping("/wx_code")
    public ResponseEntity<?> wxCode(@RequestParam("code") String code){
        logger.info(code);

        try {
            JscodeResponse response = wxUserService.getJsCode(code);
            WxUser wxUser = new WxUser();
            wxUser.setOpenId(response.getOpenid());

            Optional<WxUser> optional = wxUserRepository.getWxUserByOpenId(response.getOpenid());
            if (optional.isPresent()){
                logger.info("Username: {}", optional.get().getUser().getUsername());
                String token = tokenProvider.generateToken(optional.get().getUser());
                return ResponseEntity.ok(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body("");
    }
}
