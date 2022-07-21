package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.ConfirmationToken;
import com.yuanning.backbug.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
/*
*
* 使用注册表单获取用户信息。
  使用我们在类中设置的默认值对密码进行编码BCryptPasswordEncoder并使用值创建用户。enabled=falseUser
  创建一个ConfirmationToken并将此令牌分配给用户。
  使用此令牌创建一个唯一的 url 并通过电子邮件发送。
  当用户单击该用户的链接更改enabled字段时true。
  删除令牌。
* */
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    // 向数据库里存token
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    // 通过String token找到token
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmationAt(String token) {

        int affectedRows = confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        return affectedRows;
    }

    // 删除token
    public void deleteConfirmationToken(String token) {
        confirmationTokenRepository.deleteByToken(token);
    }
}
