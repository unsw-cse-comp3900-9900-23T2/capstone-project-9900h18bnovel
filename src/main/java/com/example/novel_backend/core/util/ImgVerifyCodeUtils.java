package com.example.novel_backend.core.util;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * Image Captcha Tools
 *
 * @author :Contanstin
 * @create 2023/6/13 14:30
 */
@UtilityClass
public class ImgVerifyCodeUtils {

    /**
     * Generate random strings of numbers only
     */
    private final String randNumber = "0123456789";

    /**
     * Image width
     */
    private final int width = 100;

    /**
     * Photo High
     */
    private final int height = 38;

    private final Random random = new Random();

    /**
     * Get the font
     */
    private Font getFont() {
        return new Font("Fixed", Font.PLAIN, 23);
    }


    /**
     * Generate check digit images
     */
    public String genVerifyCodeImg(String verifyCode) throws IOException {
        // The BufferedImage class is an Image class with a buffer,
        // and the Image class is a class used to describe image information.
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // Graphics object that generates an Image object,
        // which can be used to perform various drawing operations on the image
        Graphics g = image.getGraphics();
        //Image size
        g.fillRect(0, 0, width, height);
        //Font size
        //Font colour
        g.setColor(new Color(204, 204, 204));
        // Draw interference lines
        // Number of interference lines
        int lineSize = 40;
        for (int i = 0; i <= lineSize; i++) {
            drawLine(g);
        }
        // Draw random characters
        drawString(g, verifyCode);
        g.dispose();
        // Converting images to Base64 strings
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", stream);
        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }

    /**
     * Drawing strings
     */
    private void drawString(Graphics g, String verifyCode) {
        for (int i = 1; i <= verifyCode.length(); i++) {
            g.setFont(getFont());
            g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                    .nextInt(121)));
            g.translate(random.nextInt(3), random.nextInt(3));
            g.drawString(String.valueOf(verifyCode.charAt(i - 1)), 13 * i, 23);
        }
    }

    /**
     * Plotting interference lines
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * Get a random check digit
     */
    public String getRandomVerifyCode(int num) {
        int randNumberSize = randNumber.length();
        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < num; i++) {
            String rand = String.valueOf(randNumber.charAt(random.nextInt(randNumberSize)));
            verifyCode.append(rand);
        }
        return verifyCode.toString();
    }

}
