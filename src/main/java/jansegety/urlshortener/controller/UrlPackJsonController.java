package jansegety.urlshortener.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jansegety.urlshortener.controller.jsondto.CreateShortUrlDto;
import jansegety.urlshortener.controller.jsondto.CreateShortUrlDto.Result;
import jansegety.urlshortener.entity.ClientApplication;
import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.service.ClientApplicationService;
import jansegety.urlshortener.service.UrlPackService;
import jansegety.urlshortener.util.UrlMaker;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/urlpack/util")
@RequiredArgsConstructor
public class UrlPackJsonController {

	private final ApplicationContext applicationContext;
	private final UrlPackService urlPackService;
	private final ClientApplicationService clientApplicationService;
	
	
	/*
	 * 등록된 application인지 탐색하고 
	 * 만약 있다면 해당 어플리케이션의 urlPack으로 등록한다.
	 * 만약 없다면 어플리케이션을 등록해달라는 안내페이지로 이동시킨다.
	 */
	@PostMapping("/shorturl")
	public Object create(@RequestHeader Map<String, String> requestHeaders, @RequestParam("url") String longUrl,
			HttpServletResponse response) {
		
		//application 검증 로직
		String clientId = requestHeaders.get("urlshortener-client-id");
		String clientSecret = requestHeaders.get("urlshortener-client-secret");
		if(!StringUtils.hasText(clientId)||!StringUtils.hasText(clientSecret))
		{
			return "등록하신 application의 `urlshortener-client-id`와 `urlshortener-client-secret`를 보내주세요";
		}
		UUID clientIdUUID = UUID.fromString(clientId);
		UUID clientSecretUUID = UUID.fromString(clientSecret);
		
		Optional<ClientApplication> clientApplicationOp = clientApplicationService.findById(clientIdUUID);
		if(clientApplicationOp.isEmpty())
		{
			return "일치하는 client가 없습니다.";
		}
		
		ClientApplication clientApplication = clientApplicationOp.get();
		if(!clientApplication.equalsClientSecret(clientSecretUUID))
			return "secret이 일치하지 않습니다.";
		
		
		//user 검증 로직
		User user = clientApplication.getUser();
		if(user == null)
			throw new IllegalStateException("ClientApplication에 유저가 설정되어 있지 않습니다.");
		
		
		//성공 로직
		UrlPack urlPack = applicationContext.getBean(UrlPack.class);
		urlPack.setUser(user); //유저 할당
		urlPack.setLongUrl(longUrl);
		urlPackService.registAndEncoding(urlPack);
		
		CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto();
		createShortUrlDto.setMessage("ok");
		createShortUrlDto.setCode("200");
		
		Result result = createShortUrlDto.new Result();
		result.setOrgUrl(urlPack.getLongUrl());
		result.setValueEncoded(urlPack.getValueEncoded());
		result.setUrl(UrlMaker.makeUrlWithDomain(urlPack.getValueEncoded()));
		
		createShortUrlDto.setResult(result);
		
		response.setHeader("Connection", "keep-alive");
		
		return createShortUrlDto;
	}
	
	
	
}
