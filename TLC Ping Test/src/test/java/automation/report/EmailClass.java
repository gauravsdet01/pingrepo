package automation.report;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailClass {
 public static void main(String[] args) {

  String host="smtp.live.com";
  final String user="richirich001@hotmail.com";//change accordingly
  final String password="Gaur@9719";//change accordingly
  
  String to="gauravtmca@gmail.com";//change accordingly

   //Get the session object
   Properties props = new Properties();
   props.put("mail.smtp.starttls.enable", "true");
   props.put("mail.smtp.host",host);
   props.put("mail.smtp.auth", "true");
   
   Session session = Session.getDefaultInstance(props,
    new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication(user,password);
      }
    });

   //Compose the message
    try {
     MimeMessage message = new MimeMessage(session);
     message.setFrom(new InternetAddress(user));
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
     message.setSubject("The Luxury Closet Functionalities Test Results");
     message.setText("PFA Report(Please download the report for better view)");
     
     
  // Create the message part
     BodyPart messageBodyPart = new MimeBodyPart();

     // Now set the actual message
     messageBodyPart.setText("This is message body");
     
  // Create a multipar message
     Multipart multipart = new MimeMultipart();

     
     // Part two is attachment
     messageBodyPart = new MimeBodyPart();
     String filename = "C:\\Users\\gaura\\eclipse-workspace\\TLC Web\\buildnumber.properties";
     DataSource source = new FileDataSource(filename);
     messageBodyPart.setDataHandler(new DataHandler(source));
     messageBodyPart.setFileName(filename);
     multipart.addBodyPart(messageBodyPart);

     // Send the complete message parts
     message.setContent(multipart);
     
    //send the message
     Transport.send(message);

     System.out.println("message sent successfully...");
 
     } catch (MessagingException e) {e.printStackTrace();}
 }
}