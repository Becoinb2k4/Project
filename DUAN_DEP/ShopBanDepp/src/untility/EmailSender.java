/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package untility;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author 
 */
public class EmailSender {private final static String emailGui = "1tovanduong2000@gmail.com";
    private final static String matKhau = "lqsk nibb spdf fdje";
    
    public static void guiMail(String emailNhan, 
            String tieuDe, String noiDung) 
            throws AddressException, MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailGui, matKhau);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailGui));
        message.setRecipients(
          Message.RecipientType.TO, InternetAddress.parse(emailNhan));
        
        // Tiêu đề
        message.setSubject(tieuDe);
        
        // Nội dung
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(noiDung, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
    
    // Hàm để test
    public static void main(String[] args) throws MessagingException, InterruptedException {
        int i = 0;
        while(true){
            i++;
            EmailSender.guiMail("duongtvph36481@fpt.edu.vn", "WARNING ->" +1 , "nàiiiiii" );
            //EmailSender.guiMail("1tovanduong2000@gmail.com", "warning" +1 , "nàiiiiii" );
            System.out.println(i);
            Thread.sleep(5000);
            
        }
        
    }
    
}
