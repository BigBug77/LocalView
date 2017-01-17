package Test;

import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;

import Email.Models.EmailModel;
import Email.Utils.RecvEmail;

/**
 * Created by v_frankyfu on 2017/1/14.
 */
public class RecvMail {
    public void RecvQQMail(){
        System.out.println("************接收QQ邮件************");
    }

    public void Recv163Mail(){
        System.out.println("************接收163邮件************");
        EmailModel emailModel = new EmailModel();
        emailModel.SetUsername("adai2199@163.com");
        emailModel.SetPassword("");
        emailModel.SetPort("995");
        emailModel.SetHost("pop.163.com");

        try{
            RecvEmail.RecvMail(emailModel);
        }catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
