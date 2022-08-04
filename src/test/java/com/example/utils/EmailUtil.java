package com.example.utils;

import com.sun.istack.internal.logging.Logger;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtil {
    /**
     * 发送邮件
     * @param is  表格输出流
     * @param fileName  发送的文件名称 (例：xxxx统计表)
     * @param reUserMail  收件人邮箱（例：12345678@qq.com）
     */
    public static boolean sendMail(InputStream is, String fileName, String reUserMail){
        //日志打印
        Logger log= Logger.getLogger(EmailUtil.class);
        log.info("[ 开始发送邮件... ]");
        Transport transport = null;
        try{
            System.setProperty("mail.mime.splitlongparameters","false");
            Properties props = new Properties();
            // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
            props.put("mail.smtp.host","smtp.exmail.qq.com");
            // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
            props.put("mail.smtp.auth", "true");
            // 用刚刚设置好的props对象构建一个session
            Session session = Session.getDefaultInstance(props);
            // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
            // 用（你可以在控制台（console)上看到发送邮件的过程）
            session.setDebug(false);
            // 用session为参数定义消息对象
            MimeMessage message = new MimeMessage(session);
            // 加载发件人地址
            message.setFrom(new InternetAddress("2337886287@qq.com"/*删除此段-发件邮箱*/));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reUserMail));
            // 加载标题
            message.setSubject(fileName);
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("请查看附件");
            multipart.addBodyPart(contentPart);
            // 添加附件
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(is, "application/msexcel");
            // 添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 添加附件的标题
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            messageBodyPart.setFileName(MimeUtility.encodeText(fileName+".xls"));
            multipart.addBodyPart(messageBodyPart);

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect("smtp.qq.com", "2337886287@qq.com"/*删除此段-发件人名称*/, "nstqzopmfrkbebbd"/*删除此段-发件人邮箱密码*/);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                transport.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
