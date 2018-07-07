package xyz.ruanxy.java.balance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.controller.AuthController;
import xyz.ruanxy.java.balance.exception.AppException;
import xyz.ruanxy.java.balance.model.Role;
import xyz.ruanxy.java.balance.model.RoleName;
import xyz.ruanxy.java.balance.model.User;
import xyz.ruanxy.java.balance.model.WxUser;
import xyz.ruanxy.java.balance.payload.*;
import xyz.ruanxy.java.balance.payload.weixin.JscodeResponse;
import xyz.ruanxy.java.balance.payload.weixin.WxSignUpRequest;
import xyz.ruanxy.java.balance.repository.RoleRepository;
import xyz.ruanxy.java.balance.repository.UserRepository;
import xyz.ruanxy.java.balance.repository.WxUserRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxUserService {
    private final static Logger logger = LoggerFactory.getLogger(WxUserService.class);

    @Autowired
    WxUserRepository wxUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpClientService httpClientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Value("${wx.app_id}")
    String appId;

    @Value("${wx.secret}")
    String appSecret;

    public boolean ifUserExistByOpenId(String openId){
        Optional<WxUser> wxUserOption = wxUserRepository.getWxUserByOpenId(openId);
        return wxUserOption.isPresent();
    }

    public JscodeResponse getJsCode(String code) throws IOException, URISyntaxException {
        logger.info("Code: {}", code);

        String url = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        String resp = httpClientService.doGet(url, params);
        JscodeResponse jscodeResponse = objectMapper.readValue(resp, JscodeResponse.class);

        return jscodeResponse;
    }

//    public User create(WxSignUpRequest request){
//        SignUpRequest signUpRequest = request.getUser();
//
//        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
//                signUpRequest.getEmail(), signUpRequest.getPassword());
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//        user.setRoles(Collections.singleton(userRole));
//
//        User result = userRepository.save(user);
//
//        WxUser wxUser = new WxUser();
//        wxUser.setOpenId(request.getOpenId());
//        wxUser.setUid(result.getId());
//
//        wxUserRepository.save(wxUser);
//
//        return user;
//    }
}
