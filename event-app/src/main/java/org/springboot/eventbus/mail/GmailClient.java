package org.springboot.eventbus.mail;

import java.util.*;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
/**
 * Created by rdas on 1/3/2017.
 */
public class GmailClient {

    private String userName;
    private String password;
    private String sendingHost;
    private int sendingPort;
    private String from;
    private String to;
    private String subject;
    private String text;
    private String receivingHost;
//    private int receivingPort;

    public void setAccountDetails(String userName,String password){

        this.userName=userName;//sender's email can also use as User Name
        this.password=password;

    }

    public void sendGmail(String from, String to, String subject, String text){

        // This will send mail from -->sender@gmail.com to -->receiver@gmail.com

        this.from=from;
        this.to=to;
        this.subject=subject;
        this.text=text;

        // For a Gmail account--sending mails-- host and port shold be as follows

        this.sendingHost="smtp.gmail.com";
        this.sendingPort=465;

        Properties props = new Properties();

        props.put("mail.smtp.host", this.sendingHost);
        props.put("mail.smtp.port", String.valueOf(this.sendingPort));
        props.put("mail.smtp.user", this.userName);
        props.put("mail.smtp.password", this.password);

        props.put("mail.smtp.auth", "true");

        Session session1 = Session.getDefaultInstance(props);

        Message simpleMessage = new MimeMessage(session1);

        //MIME stands for Multipurpose Internet Mail Extensions

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;

        try {

            fromAddress = new InternetAddress(this.from);
            toAddress = new InternetAddress(this.to);

        } catch (AddressException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(null, "Sending email to: " + to + " failed !!!", "Falied to Send!!!", JOptionPane.ERROR_MESSAGE);

        }

        try {

            simpleMessage.setFrom(fromAddress);

            simpleMessage.setRecipient(RecipientType.TO, toAddress);

            // to add CC or BCC use
            // simpleMessage.setRecipient(RecipientType.CC, new InternetAddress("CC_Recipient@any_mail.com"));
            // simpleMessage.setRecipient(RecipientType.BCC, new InternetAddress("CBC_Recipient@any_mail.com"));

            simpleMessage.setSubject(this.subject);

            simpleMessage.setText(this.text);

            //sometimes Transport.send(simpleMessage); is used, but for gmail it's different

            Transport transport = session1.getTransport("smtps");

            transport.connect (this.sendingHost,sendingPort, this.userName, this.password);

            transport.sendMessage(simpleMessage, simpleMessage.getAllRecipients());

            transport.close();

            JOptionPane.showMessageDialog(null, "Mail sent successfully ...","Mail sent",JOptionPane.PLAIN_MESSAGE);

        } catch (MessagingException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(null, "Sending email to: " + to + " failed !!!", "Falied to Send!!!", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void readGmail(){

        /*this will print subject of all messages in the inbox of sender@gmail.com*/

        this.receivingHost="imap.gmail.com";//for imap protocol

        Properties props2=System.getProperties();

        props2.setProperty("mail.store.protocol", "imaps");
        // I used imaps protocol here

        Session session2=Session.getDefaultInstance(props2, null);

        try {

            Store store=session2.getStore("imaps");

            store.connect(this.receivingHost,this.userName, this.password);

            Folder folder=store.getFolder("INBOX");//get inbox

            folder.open(Folder.READ_ONLY);//open folder only to read

            Message message[]=folder.getMessages();

            for(int i=0;i<message.length;i++){

                //print subjects of all mails in the inbox

                System.out.println(message[i].getSubject());

                //anything else you want

            }

            //close connections

            folder.close(true);

            store.close();

        } catch (Exception e) {

            System.out.println(e.toString());

        }

    }

    public static void main(String[] args) {

        String mailFrom=new String("rajeshranjan.d@gmail.com");

        //Sender must be a Gmail Account holder

        String mailTo=new String("rajeshranjan.d@gmail.com");

        //but here you can send to any type of mail account

        String senderPassword=new String("Rd668822");

        String senderUserName=new String("rajeshranjan.d");

        //Mention your email subject and content

        String mailSubject=new String("Testing Mail");

        String mailText=new String("Have an Nice Day ...........!!!");

        //Create a GmailClient object

        GmailClient newGmailClient=new GmailClient();

        //Setting up account details

        newGmailClient.setAccountDetails(senderUserName, senderPassword);

        //Send mail

        newGmailClient.sendGmail(mailFrom, mailTo, mailSubject, mailText);

        //Receive mails

        newGmailClient.readGmail();

    }

}
