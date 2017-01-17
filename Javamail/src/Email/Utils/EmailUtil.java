package Email.Utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.util.ByteArrayDataSource;

import Email.Models.EmailModel;
import Email.Models.Attachment;

/**
 * Created by v_frankyfu on 2017/1/13.
 * 构造Email用户的公共方法
 */
public class EmailUtil {

    /**
     * 设置smtp邮件属性
     * @return Properties
     */
    public static Properties SetSmtpProperties(EmailModel emailModel){
        Properties props = new Properties();
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailModel.GetHost());
        props.put("mail.smtp.port", emailModel.GetPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", emailModel.GetPort());
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    /**
     * 设置pop3邮件属性
     * @return Properties
     */
    public static Properties SetPop3Properties(EmailModel emailModel){
        Properties props = new Properties();
        props.put("mail.debug", "true");
        props.put("mail.pop3.host", emailModel.GetHost());
        props.put("mail.pop3.port", emailModel.GetPort());
        props.put("mail.pop3.starttls.enable", "true");
        return props;
    }

    /**
     * 获取一个新session
     * @return Session
     */
    public static Session GetSession(final EmailModel emailModel, Properties props){
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailModel.GetUsername(), emailModel.GetPassword());
                    }
                });

        session.setDebug(true); //开启session调试
        return session;
    }

    /**
     * 获取Store
     * @param emailModel
     * @param props
     * @return Store
     * @throws MessagingException
     */
    public static Store GetStore(EmailModel emailModel,Properties props) throws MessagingException{
        //获取session
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        //创建pop3 store
        Store store = session.getStore("pop3s");
        //连接pop服务器
        store.connect(emailModel.GetHost(),emailModel.GetUsername(),emailModel.GetPassword());

        return store;
    }

    /**
     * 构造一封文本格式邮件
     * @param emailModel
     * @param session
     * @return message
     * @throws AddressException
     * @throws MessagingException
     */
    public static Message SetMimeMessage(EmailModel emailModel, Session session) throws AddressException, MessagingException{
        //创建一个MimeMessage对象（Message类是创建和解析邮件内容的API，它的实例对象代表一封电子邮件。）
        Message message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress(emailModel.GetFrom()));

        //指明邮件的收件人
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailModel.GetTo()));

        if (!(emailModel.GetCc() == null || "".equals(emailModel.GetCc()))){
            message.addRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(emailModel.GetCc()));
        }

        if (!(emailModel.GetBcc() == null || "".equals(emailModel.GetBcc()))){
            message.addRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(emailModel.GetBcc()));
        }

        //邮件的标题
        message.setSubject(emailModel.GetSubject());

        //邮件的文本内容
        message.setContent(emailModel.GetText(), "text/plain;charset=UTF-8");

        //邮件的html内容 优先显示
        if(emailModel.GetHtml() != null){
            message.setContent(emailModel.GetHtml(), "text/html;charset=UTF-8");
        }

        return message;
    }

    /**
     * 构造带纯附件的邮件
     * @param emailModel
     * @param session
     * @return message
     * @throws AddressException
     * @throws MessagingException
     */
    public static Message SetMimeMessageWithAttachment(EmailModel emailModel, Session session) throws AddressException, MessagingException{
        //创建一个MimeMessage对象（Message类是创建和解析邮件内容的API，它的实例对象代表一封电子邮件。）
        Message message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress(emailModel.GetFrom()));

        //指明邮件的收件人
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailModel.GetTo()));

        if (!(emailModel.GetCc() == null || "".equals(emailModel.GetCc()))){
            message.addRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(emailModel.GetCc()));
        }

        if (!(emailModel.GetBcc() == null || "".equals(emailModel.GetBcc()))){
            message.addRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(emailModel.GetBcc()));
        }

        //邮件的标题
        message.setSubject(emailModel.GetSubject());
        message.setContent(emailModel.GetText(), "text/plain;charset=UTF-8");

        //创建一个Multipart message【可以包含多个MimeBodyPart】
        Multipart multipart = new MimeMultipart();

        //创建第一个MimeBodyPart，设置邮件内容【MimeBodyPart也可以再包含一个Multipart】
        BodyPart messageBodyPart = new MimeBodyPart();
        //邮件的文本内容
        messageBodyPart.setText(emailModel.GetText());
        multipart.addBodyPart(messageBodyPart);

        //创建第二个MimeBodyPart，设置邮件附件
        for (Attachment attachment : emailModel.GetAttachments()){
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(attachment.GetDataSource()));
            messageBodyPart.setFileName(attachment.GetName());
            multipart.addBodyPart(messageBodyPart);
    }

        //完成message的组装
        message.setContent(multipart);

        return message;
    }

    /**
     * 有BUG无法区分嵌入式图片和纯附件
     * @param emailModel
     * @param session
     * @return message
     * @throws AddressException
     * @throws MessagingException
     */
    public static Message SetMimeMessageWithInlineImages(EmailModel emailModel, Session session) throws AddressException, MessagingException{
        //创建一个MimeMessage对象（Message类是创建和解析邮件内容的API，它的实例对象代表一封电子邮件。）
        Message message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress(emailModel.GetFrom()));

        //指明邮件的收件人
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailModel.GetTo()));

        if (!(emailModel.GetCc() == null || "".equals(emailModel.GetCc()))){
            message.addRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(emailModel.GetCc()));
        }

        if (!(emailModel.GetBcc() == null || "".equals(emailModel.GetBcc()))){
            message.addRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(emailModel.GetBcc()));
        }

        //邮件的标题
        message.setSubject(emailModel.GetSubject());

        message.setContent(emailModel.GetText(), "text/plain;charset=UTF-8");

        Multipart allMultipart = new MimeMultipart();
        MimeBodyPart contentPart=new MimeBodyPart();

        //创建一个Multipart message【可以包含多个MimeBodyPart】
        MimeMultipart multipart=new MimeMultipart("related");

        MimeBodyPart htmlbodypart=new MimeBodyPart();
        htmlbodypart.setContent(emailModel.GetHtml(),"text/html;charset=UTF-8");
        multipart.addBodyPart(htmlbodypart);

        //创建第二个MimeBodyPart，设置邮件附件
        for (Attachment attachment : emailModel.GetAttachments()){
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(attachment.GetDataSource()));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
        }

        contentPart.setContent(multipart);
        allMultipart.addBodyPart(contentPart);

        //完成message的组装
        message.setContent(allMultipart);

        return message;
    }
}
