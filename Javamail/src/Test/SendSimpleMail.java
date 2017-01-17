package Test;

import Email.Models.EmailModel;
import Email.Utils.SendEmail;

/**
 * Created by v_frankyfu on 2017/1/14.
 */
public class SendSimpleMail {
    public void SendQQMail(){
        System.out.println("************发送Simple QQ邮件************");
        EmailModel emailModel = new EmailModel();
        emailModel.SetUsername("455101594@qq.com");
        emailModel.SetPassword("");
        emailModel.SetPort("465");
        emailModel.SetHost("smtp.qq.com");
        emailModel.SetFrom("455101594@qq.com");
        emailModel.SetTo("455101594@qq.com");
        emailModel.SetSubject("测试发送邮件");
        emailModel.SetText("这是一个测试内容！");

        SendEmail.SendSimpleMail(emailModel);
    }

    public void Send163Mail(){
        System.out.println("************发送Simple 163邮件************");
        EmailModel emailModel = new EmailModel();
        emailModel.SetUsername("adai2199@163.com");
        emailModel.SetPassword("");
        emailModel.SetPort("465");
        emailModel.SetHost("smtp.163.com");
        emailModel.SetFrom("adai2199@163.com");
        emailModel.SetTo("adai2199@163.com");
        emailModel.SetSubject("测试发送邮件");
        emailModel.SetText("这是一个测试内容！");

        SendEmail.SendSimpleMail(emailModel);
    }
}
