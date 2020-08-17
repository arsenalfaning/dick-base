package com.dick.base.session.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dick.base.exception.UsernameDuplicatedException;
import com.dick.base.mapper.BaseUserMapper;
import com.dick.base.model.BaseUser;
import com.dick.base.security.BaseUserDetails;
import com.dick.base.session.dto.SignInResult;
import com.dick.base.session.dto.SignUpParameter;
import com.dick.base.util.BaseRedisProperties;
import com.dick.base.util.IdUtil;
import com.dick.base.util.PasswordUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService implements UserDetailsService {

    private BaseUserMapper baseUserMapper;
    private RedisTemplate<String, Object> redisTemplate;

    public SessionService(BaseUserMapper baseUserMapper, RedisTemplate<String, Object> redisTemplate) {
        this.baseUserMapper = baseUserMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 注册用户
     * @param signUpParameter
     */
    public void signUp(SignUpParameter signUpParameter) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(signUpParameter.getUsername());
        Integer count = baseUserMapper.selectCount(new QueryWrapper<>(baseUser));
        if (count > 0) {
            throw new UsernameDuplicatedException();
        }
        baseUser.setSalt(PasswordUtil.generatePasswordSalt());
        baseUser.setPassword(PasswordUtil.generatePassword(signUpParameter.getPassword(), baseUser.getSalt()));
        baseUser.setSignUpTime(LocalDateTime.now());
        baseUser.setId(IdUtil.nextId());
        baseUserMapper.insert(baseUser);
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String key = redisKeyForSignIn(token);
        SignInResult result = (SignInResult) redisTemplate.opsForValue().get(key);
        return new BaseUserDetails(result);
    }

    protected String redisKeyForSignIn(String token) {
        return String.format(BaseRedisProperties.getInstance().getLoginToken(), token);
    }
}
