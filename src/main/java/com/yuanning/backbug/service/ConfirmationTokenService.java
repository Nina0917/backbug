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
}
