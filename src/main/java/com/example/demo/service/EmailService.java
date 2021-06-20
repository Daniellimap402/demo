package com.example.demo.service;

import com.example.demo.service.dto.ModeloEmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
@Transactional
public class EmailService {

    @Value("${mail.usuario}")
    private String usuario;

    @Value("${mail.senha}")
    private String senha;

    @Value("${mail.host}")
    private String hostMail;

    @Value("${mail.nome}")
    private String nomeRemetente;

    public void enviarEmail(ModeloEmailDTO modeloEmailDTO) throws MessagingException {

        Properties props = getProperties();

        Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });

        mailSession.setDebug(true);

        Message msg = getMessage(modeloEmailDTO, mailSession);

        encaminharEmail(mailSession, msg);
    }

    private void encaminharEmail(Session mailSession, Message msg) throws MessagingException {
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(hostMail, usuario, senha);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    private Message getMessage(ModeloEmailDTO modeloEmailDTO, Session mailSession) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(nomeRemetente));
        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(modeloEmailDTO.getDestinatario()));
        msg.setSubject(modeloEmailDTO.getAssunto());
        msg.setSentDate(new Date());
        msg.setContent(modeloEmailDTO.getMensagem(), "text/html;charset=UTF-8");
        return msg;
    }

    private Properties getProperties() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", hostMail);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return props;
    }
}
