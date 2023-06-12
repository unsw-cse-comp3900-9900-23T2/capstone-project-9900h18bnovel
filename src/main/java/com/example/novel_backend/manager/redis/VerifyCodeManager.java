package com.example.novel_backend.manager.redis;

import com.example.novel_backend.core.common.constant.CacheConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;

/**
 * Captcha Management
 *
 * @author :Contanstin
 * @create 2023/6/12 14:00
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VerifyCodeManager {

    private final StringRedisTemplate stringRedisTemplate;

    private final JavaMailSender javaMailSender;

    /**
     * Send verify code
     * @param toEmail
     */
    public void sendVerificationCode(String toEmail) {
        String code = generateVerificationCode();

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("novelhub9900@163.com");
        mail.setTo(toEmail);
        mail.setSubject("<Novel> Verification code " + code);
        mail.setText("Your verification code is " + code + ". Do not share it with anyone.\n" +
                "The verification code expires in five minutes, please register as soon as possible." +
                "\n\nRegards,\nNovel members");

        javaMailSender.send(mail);

        storeVerificationCode(toEmail, code);
    }

    /**
     * Generate verification code
     *
     * @return
     */
    private String generateVerificationCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    /**
     * Store code to redis
     *
     * @param email
     * @param code
     */
    private void storeVerificationCode(String email, String code) {
        stringRedisTemplate.opsForValue().set(CacheConsts.EMAIL_VERIFY_CODE_CACHE_KEY + email
                , code, Duration.ofMinutes(5));
        log.info("Stored verification code {} for email {}", code, email);
    }

    /**
     * Verify email verification code
     */
    public boolean verifyCodeOk(String email, String verifyCode) {
        return Objects.equals(stringRedisTemplate.opsForValue()
                .get(CacheConsts.EMAIL_VERIFY_CODE_CACHE_KEY + email), verifyCode);
    }

    /**
     * Remove CAPTCHA from Redis
     */
    public void removeVerifyCode(String email) {
        stringRedisTemplate.delete(CacheConsts.EMAIL_VERIFY_CODE_CACHE_KEY + email);
    }
}
