package Email.Utils;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import Email.Models.EmailModel;
import Email.Utils.EmailUtil;

/**
 * Created by v_frankyfu on 2017/1/14.
 * 发送邮件
 */
public class SendEmail {
    /**
     * 发送简单的邮件
     * @param emailModel
     */
    public static void SendSimpleMail(EmailModel emailModel) {
        //设置Properties
        Properties props = EmailUtil.SetSmtpProperties(emailModel);

        //获取一个session
        Session session = EmailUtil.GetSession(emailModel, props);

        try {
            //创建一个默认MimeMessage对象，并设置发件人、收件人、主题（From、To、Subject）在消息中。
            Message message = EmailUtil.SetMimeMessage(emailModel, session);

            //发送邮件
            Transport transport = session.getTransport("smtps");
            transport.connect(emailModel.GetHost(), Integer.parseInt(emailModel.GetPort()), emailModel.GetUsername(), emailModel.GetPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("发送邮件...成功！");
        } catch (Exception e) {
            System.out.println("发送邮件...失败！");
            e.printStackTrace();
        }
    }

    /**
     * 发送带有附件的邮件
     * @param emailModel
     */
    public static void SendAttachmentMail(EmailModel emailModel) {
        //设置Properties
        Properties props = EmailUtil.SetSmtpProperties(emailModel);

        //获取一个session
        Session session = EmailUtil.GetSession(emailModel, props);

        try {
            //创建一个默认MimeMessage对象，并设置发件人、收件人、主题（From、To、Subject）在消息中。
            Message message = EmailUtil.SetMimeMessageWithAttachment(emailModel, session);

            //发送邮件
            Transport transport = session.getTransport("smtps");
            transport.connect(emailModel.GetHost(), Integer.parseInt(emailModel.GetPort()), emailModel.GetUsername(), emailModel.GetPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("发送邮件...成功！");
        } catch (Exception e) {
            System.out.println("发送邮件...失败！");
            e.printStackTrace();
        }
    }

    public static void SendInlineMail(EmailModel emailModel) {
        //设置Properties
        Properties props = EmailUtil.SetSmtpProperties(emailModel);

        //获取一个session
        Session session = EmailUtil.GetSession(emailModel, props);

        try {
            //创建一个默认MimeMessage对象，并设置发件人、收件人、主题（From、To、Subject）在消息中。
            Message message = EmailUtil.SetMimeMessageWithInlineImages(emailModel, session);

            //发送邮件
            Transport transport = session.getTransport("smtps");
            transport.connect(emailModel.GetHost(), Integer.parseInt(emailModel.GetPort()), emailModel.GetUsername(), emailModel.GetPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("发送邮件...成功！");
        } catch (Exception e) {
            System.out.println("发送邮件...失败！");
            e.printStackTrace();
        }
    }
}
