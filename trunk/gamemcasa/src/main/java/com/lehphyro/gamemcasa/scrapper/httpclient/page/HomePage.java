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

public class HomePage {

	private static final URI MY_GAMES_ADDRESS = URI.create("http://www.gamemcasa.com.br/Forms/MeusGames01.aspx");

	private static final ImmutableList<BasicNameValuePair> BASE_PARAMS = ImmutableList.of(
		new BasicNameValuePair("ctl00$MenuEsquerdo1$ConsCep1$txtCep", StringUtils.EMPTY),
		new BasicNameValuePair("ctl00$MenuEsquerdo1$ConsCep1$txtCep_MaskedEditExtender_ClientState", StringUtils.EMPTY),
		new BasicNameValuePair("ctl00$MenuSuperior1$ddlPlataforma", "0"),
		new BasicNameValuePair("ctl00$MenuSuperior1$lnkAcessar.x", "32"),
		new BasicNameValuePair("ctl00$MenuSuperior1$lnkAcessar.y", "13"),
		new BasicNameValuePair("ctl00$MenuSuperior1$txtBusca", StringUtils.EMPTY)
	);

	private final HttpClient httpClient;

	public HomePage(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public LoginPage access() throws IOException, HtmlParsingException {
		HtmlParser parser = new HtmlParser();
		HtmlNode page = parser.parse(HttpHelper.get(Pages.ADDRESS, httpClient));
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
		builder.add(new BasicNameValuePair(Pages.PREVIOUS_PAGE_ID, form.findNodeById(Pages.PREVIOUS_PAGE_ID).getValue()));
		builder.add(new BasicNameValuePair(Pages.VIEW_STATE_ID, form.findNodeById(Pages.VIEW_STATE_ID).getValue()));
		
		URI postUri = Pages.ADDRESS.resolve(form.getAction());
		page = parser.parse(HttpHelper.post(postUri, builder.build(), httpClient));

		return new LoginPage(httpClient, page);
	}

	public MyGamesPage myGames() throws IOException {
		HtmlParser parser = new HtmlParser();
		HtmlNode myGamesPage = parser.parse(HttpHelper.get(MY_GAMES_ADDRESS, httpClient));
		return new MyGamesPage(myGamesPage);
	}
}
