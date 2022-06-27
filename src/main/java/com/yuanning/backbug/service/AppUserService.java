package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.entity.ConfirmationToken;
import com.yuanning.backbug.exceptionHandler.MessageEnum;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Supplier supplier = () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG));
        return appUserRepository.findByEmail(email).orElseThrow(supplier);
    }

    // 注册用户
    public Result<String> signUpUser(AppUser appUser) {
        //repository里通过邮箱查找用户
        Optional<AppUser> user = appUserRepository.findByEmail(appUser.getEmail());

        //如果用户已存在,throw error
        if (user.isPresent()) {
            //throw new IllegalStateException("email already taken");
            return MessageUtil.error(MessageEnum.Email_occupied.getCode(), MessageEnum.Email_occupied.getMessage());
        }

        //用户不存在，继续注册

        //加密密码
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        //将原先的密码替换为成加密后的密码
        appUser.setPassword(encodedPassword);
        //保存新用户
        appUserRepository.save(appUser);

        // 创建新token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        // 保存新token
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: SEND EMAIL

        return MessageUtil.success(token);
    }

    // 确认用户（通过邮箱）
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
