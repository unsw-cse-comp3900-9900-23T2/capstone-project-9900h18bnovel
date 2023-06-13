package com.example.novel_backend.manager.redis;

import com.example.novel_backend.core.common.constant.CacheConsts;
import com.example.novel_backend.core.util.ImgVerifyCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
     * Generate a image CAPTCHA and place it in Redis
     */
    public String genImgVerifyCode(String sessionId) throws IOException {
        String verifyCode = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String img = ImgVerifyCodeUtils.genVerifyCodeImg(verifyCode);
        stringRedisTemplate.opsForValue().set(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId,
                verifyCode, Duration.ofMinutes(5));
        return img;
    }

    /**
     * Verify the image verification code
     */
    public boolean imgVerifyCodeOk(String sessionId, String verifyCode) {
        return Objects.equals(stringRedisTemplate.opsForValue()
                .get(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId), verifyCode);
    }

    /**
     * Remove IMG CAPTCHA from Redis
     */
    public void removeImgVerifyCode(String sessionId) {
        stringRedisTemplate.delete(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId);
    }

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
     * Generate email verification code
     *
     * @return
     */
    private String generateVerificationCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    /**
     * Store email code to redis
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
     * Remove Email CAPTCHA from Redis
     */
    public void removeVerifyCode(String email) {
        stringRedisTemplate.delete(CacheConsts.EMAIL_VERIFY_CODE_CACHE_KEY + email);
    }
}
