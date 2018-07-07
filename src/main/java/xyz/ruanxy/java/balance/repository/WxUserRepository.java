package xyz.ruanxy.java.balance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.WxUser;

import java.util.Optional;

@Repository
public interface WxUserRepository extends CrudRepository<WxUser, Long> {
    Optional<WxUser> getWxUserByOpenId(String openId);
}
