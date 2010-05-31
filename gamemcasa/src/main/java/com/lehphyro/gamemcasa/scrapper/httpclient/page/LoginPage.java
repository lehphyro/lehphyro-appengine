package com.lehphyro.gamemcasa.scrapper.httpclient.page;

import java.io.*;
import java.net.*;

import org.apache.commons.lang.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.message.*;

import com.google.common.collect.*;
import com.google.common.collect.ImmutableList.*;
import com.lehphyro.gamemcasa.scrapper.httpclient.*;
import com.lehphyro.gamemcasa.scrapper.httpclient.parsing.*;

import static com.lehphyro.gamemcasa.scrapper.httpclient.page.Pages.*;

public class LoginPage {

	private static final String EMAIL_ID = "ctl00_ContentPlaceHolder1_txtEmail";
	private static final String EMAIL_NAME = "ctl00$ContentPlaceHolder1$txtEmail";
	private static final String PASSWORD_NAME = "ctl00$ContentPlaceHolder1$txtSenha";

	private static final ImmutableList<BasicNameValuePair> BASE_PARAMS = ImmutableList.of(
		new BasicNameValuePair("ctl00$ContentPlaceHolder1$btnEntrar", "ENTRAR"),
		new BasicNameValuePair("ctl00$MenuEsquerdo1$ConsCep1$txtCep", StringUtils.EMPTY),
		new BasicNameValuePair("ctl00$MenuEsquerdo1$ConsCep1$txtCep_MaskedEditExtender_ClientState", StringUtils.EMPTY),
		new BasicNameValuePair("ctl00$MenuSuperior1$ddlPlataforma", "0"),
		new BasicNameValuePair("ctl00$MenuSuperior1$txtBusca", StringUtils.EMPTY)
	);
	
	private final HttpClient httpClient;
	private final HtmlNode page;
	
	public LoginPage(HttpClient httpClient, HtmlNode page) {
		this.httpClient = httpClient;
		this.page = page;
		
		Validate.isTrue(page.existsById(EMAIL_ID), "Pagina de login nao recebeu o HTML correto");
	}

	public HomePage login(String username, String password) throws IOException, HtmlParsingException {
		HtmlNode form = page.findNodeById("aspnetForm");

		Builder<NameValuePair> builder = ImmutableList.builder();
		builder.addAll(BASE_PARAMS);
		
		if (page.existsById(Pages.EVENT_ARGUMENT_ID)) {
			builder.add(new BasicNameValuePair(Pages.EVENT_ARGUMENT_ID, form.findNodeById(Pages.EVENT_ARGUMENT_ID).getValue()));
		} else {
			builder.add(new BasicNameValuePair(Pages.EVENT_ARGUMENT_ID, StringUtils.EMPTY));
		}
		
		if (page.existsById(Pages.EVENT_TARGET_ID)) {
			builder.add(new BasicNameValuePair(Pages.EVENT_TARGET_ID, form.findNodeById(Pages.EVENT_TARGET_ID).getValue()));
		} else {
			builder.add(new BasicNameValuePair(Pages.EVENT_TARGET_ID, StringUtils.EMPTY));
		}
		
		if (page.existsById(Pages.EVENT_VALIDATION_ID)) {
			builder.add(new BasicNameValuePair(Pages.EVENT_VALIDATION_ID, form.findNodeById(Pages.EVENT_VALIDATION_ID).getValue()));
		} else {
			builder.add(new BasicNameValuePair(Pages.EVENT_VALIDATION_ID, StringUtils.EMPTY));
		}
		
		if (page.existsById(LAST_FOCUS_ID)) {
			builder.add(new BasicNameValuePair(LAST_FOCUS_ID, form.findNodeById(LAST_FOCUS_ID).getValue()));
		} else {
			builder.add(new BasicNameValuePair(LAST_FOCUS_ID, StringUtils.EMPTY));
		}

		builder.add(new BasicNameValuePair(PREVIOUS_PAGE_ID, form.findNodeById(PREVIOUS_PAGE_ID).getValue()));
		builder.add(new BasicNameValuePair(VIEW_STATE_ID, form.findNodeById(VIEW_STATE_ID).getValue()));
		builder.add(new BasicNameValuePair(EMAIL_NAME, username));
		builder.add(new BasicNameValuePair(PASSWORD_NAME, password));

		URI postUri = ADDRESS.resolve(form.getAction());
		HttpHelper.post(postUri, builder.build(), httpClient);

		return new HomePage(httpClient);
	}

}
