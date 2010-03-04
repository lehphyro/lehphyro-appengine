package com.lehphyro.eatj;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class EatJMailer {

	private static final String SENDER = "lehphyro@gmail.com";
	private static final String TO = SENDER;
	
	private static final String SERVER_DOWN_SUBJECT = "O servidor EatJ.com está fora do ar.\nLink: http://www.eatj.com";
	private static final String SERVER_DOWN_BODY = "O servidor EatJ.com está fora do ar.";

	public void mailServerDown() throws MessagingException {
		Session session = Session.getDefaultInstance(new Properties());
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(SENDER));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		message.setSubject(SERVER_DOWN_SUBJECT);
		message.setText(SERVER_DOWN_BODY);
		
		Transport.send(message);
	}

}
