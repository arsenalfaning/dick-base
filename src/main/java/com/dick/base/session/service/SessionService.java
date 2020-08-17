package com.dick.base.session.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dick.base.exception.BaseRuntimeException;
import com.dick.base.exception.ExceptionProperties;
import com.dick.base.exception.UsernameDuplicatedException;
import com.dick.base.mapper.BaseUserMapper;
import com.dick.base.model.BaseUser;
import com.dick.base.security.BaseUserDetails;
import com.dick.base.session.dto.SignInParameter;
import com.dick.base.session.dto.SignInResult;
import com.dick.base.session.dto.SignUpParameter;
import com.dick.base.session.dto.UserBaseInfo;
import com.dick.base.util.BaseRedisProperties;
import com.dick.base.util.IdUtil;
import com.dick.base.util.PasswordUtil;
import com.dick.base.util.TokenUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
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
     * 用户注册
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

    /**
     * 用户登录
     * @param signInParameter
     * @return
     */
    public SignInResult signIn(SignInParameter signInParameter) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(signInParameter.getUsername());
        BaseUser result = baseUserMapper.selectOne(new QueryWrapper<>(baseUser));
        if (result != null) {
            if (PasswordUtil.checkPassword(signInParameter.getPassword(), result.getSalt(), result.getPassword())) {
                SignInResult signInResult = new SignInResult();
                signInResult.setId(result.getId());
                signInResult.setSignUpTime(LocalDateTime.now());
                signInResult.setUsername(result.getUsername());
                signInResult.setToken(TokenUtil.generateUserToken());

                result.setSignInToken(signInResult.getToken());
                result.setSignUpTime(signInResult.getSignUpTime());
                baseUserMapper.updateById(result);

                UserBaseInfo baseInfo = new UserBaseInfo();
                baseInfo.setId(result.getId());
                baseInfo.setSignUpTime(LocalDateTime.now());
                baseInfo.setUsername(result.getUsername());
                //TODO baseInfo设置角色和权限
                redisTemplate.opsForValue().set(redisKeyForSignIn(signInResult.getToken()), baseInfo);
                return signInResult;
            }
        }
        throw new BaseRuntimeException(ExceptionProperties.getInstance().getUsernameOrPasswordError(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String key = redisKeyForSignIn(token);
        UserBaseInfo user = (UserBaseInfo) redisTemplate.opsForValue().get(key);
        return new BaseUserDetails(user);
    }

    protected String redisKeyForSignIn(String token) {
        return String.format(BaseRedisProperties.getInstance().getLoginToken(), token);
    }

    protected String redisKeyForPasswordReset(String token) {
        return String.format(BaseRedisProperties.getInstance().getPasswordResetToken(), token);
    }
}
