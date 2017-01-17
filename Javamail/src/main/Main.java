package main;

import Test.SendAttachmentMail;
import Test.SendInlineMail;
import Test.SendSimpleMail;
import Test.RecvMail;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Start Test......");
        SendSimpleMail sendSimpleMail = new SendSimpleMail();
        sendSimpleMail.SendQQMail();
        sendSimpleMail.Send163Mail();

        SendAttachmentMail sendAttachmentMail = new SendAttachmentMail();
        sendAttachmentMail.SendQQMail();
        sendAttachmentMail.Send163Mail();

        SendInlineMail sendInlineMail = new SendInlineMail();
        sendInlineMail.SendQQMail();
        sendInlineMail.Send163Mail();

        RecvMail recvMail = new RecvMail();
        recvMail.RecvQQMail();
        recvMail.Recv163Mail();

        System.out.println("Start End......");
    }
}
