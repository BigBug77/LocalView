package Email.Utils;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import Email.Models.EmailModel;
import Email.Utils.EmailUtil;

/**
 * Created by v_frankyfu on 2017/1/14.
 * 接收邮件
 */
public class RecvEmail {
    public static void RecvMail(EmailModel emailModel) throws MessagingException, IOException {
        Properties properties = EmailUtil.SetPop3Properties(emailModel);

        Store store = EmailUtil.GetStore(emailModel, properties);

        //创建一个文件夹并打开它
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);
        //从folder中获取这些邮件信息并打印出来
        Message[] messages  = emailFolder.getMessages();

        int count = 0;
        for (Message message : messages) {
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (++count));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent().toString());
        }

        System.out.println("邮件总数：" + count);

        //关闭store和Folder
        emailFolder.close(false);
        store.close();
    }
}
