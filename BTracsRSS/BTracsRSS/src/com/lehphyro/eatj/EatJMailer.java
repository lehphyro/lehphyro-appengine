package com.lehphyro.eatj;

import java.io.*;

import com.google.appengine.api.mail.*;

public class EatJMailer {

	private static final String SENDER = "lehphyro@gmail.com";
	private static final String TO = SENDER;
	
	private static final String SERVER_DOWN_SUBJECT = "O servidor EatJ.com está fora do ar.\nLink: http://www.eatj.com";
	private static final String SERVER_DOWN_BODY = "O servidor EatJ.com está fora do ar.";
	
	private static final String ERROR_SUBJECT = "Erro ao pingar servidor EatJ.com";

	public void mailServerDown() throws IOException {
		MailService service = MailServiceFactory.getMailService();
		MailService.Message message = new MailService.Message(SENDER, TO, SERVER_DOWN_SUBJECT, SERVER_DOWN_BODY);
		service.send(message);
	}

	public void mailError(Throwable t) throws IOException {
		StringWriter writer = new StringWriter();
		t.printStackTrace(new PrintWriter(writer));
		
		MailService service = MailServiceFactory.getMailService();
		MailService.Message message = new MailService.Message(SENDER, TO, ERROR_SUBJECT, writer.toString());
		service.send(message);
	}
}
