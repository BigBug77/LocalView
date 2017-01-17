package Email.Models;

import java.util.List;
import java.util.ArrayList;
import Email.Models.Attachment;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by v_frankyfu on 2017/1/14.
 * 邮件model，存放邮件相关的信息
 * 服务器域名，用户名，密码
 */
public class EmailModel {
    private String username;        //账号
    private String password;        //密码 授权码
    private String host;            //邮件服务器
    private String port;            //端口

    private String to;              //收件人邮箱
    private String from;            //发送人邮箱
    private String cc;              //抄送
    private String bcc;             //暗送
    private String subject;         //主题
    private String text;            //正文
    private String html;            //html正文格式
    private List<Attachment> attachments;      //附件

    public EmailModel(){
        attachments = new ArrayList<>();
    }

    public String GetUsername() {
        return username;
    }

    public void SetUsername(String username) {
        this.username = username;
    }

    public String GetPassword() {
        return password;
    }

    public void SetPassword(String password) {
        this.password = password;
    }

    public String GetHost() {
        return host;
    }

    public void SetHost(String host) {
        this.host = host;
    }

    public String GetPort() {
        return port;
    }

    public void SetPort(String port) {
        this.port = port;
    }

    public String GetTo() {
        return to;
    }
    public void SetTo(String to) {
        this.to = to;
    }

    public String GetFrom() {
        return from;
    }

    public void SetFrom(String from) {
        this.from = from;
    }

    public String GetCc() {
        return cc;
    }

    public void SetCc(String cc) {
        this.cc = cc;
    }

    public String GetBcc() {
        return bcc;
    }

    public void SetBcc(String bcc) {
        this.bcc = bcc;
    }

    public String GetSubject() {
        return subject;
    }

    public void SetSubject(String subject) {
        this.subject = subject;
    }

    public String GetText() {
        return text;
    }

    public void SetText(String text) {
        this.text = text;
    }

    public String GetHtml() {
        return html;
    }

    public void SetHtml(String html) {
        this.html = html;
    }

    public List<Attachment> GetAttachments() {
        return attachments;
    }

    public void AddAttachment(String name, DataSource dataSource) {
        this.attachments.add(new Attachment(name, dataSource));
    }

    /**
     *
     * @param name
     * @param data 是二进制流， mimeType固定为application/octet-stream 不区分格式
     */
    public void AddAttachment(String name, final byte[] data, String type) {
        final ByteArrayDataSource dataSource = new ByteArrayDataSource(data, (type == null || "".equals(type)) ? "application/octet-stream" : type);
        dataSource.setName(name);
        this.attachments.add(new Attachment(name, dataSource));
    }
}
