/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Service.KhuyenMaiService;
import java.security.SecureRandom;

/**
 *
 * @author 
 */
public class RandomStringGenerator {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
         // Độ dài chuỗi bạn muốn sinh
        KhuyenMaiService kmService = new KhuyenMaiService();
        for (String arg : args) {
            
        }
        String randomString = generateRandomString(10);
            System.out.println("Random String: " + randomString);

    }
}
