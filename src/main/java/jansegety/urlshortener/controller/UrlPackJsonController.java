package jansegety.urlshortener.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import jansegety.urlshortener.controller.jsondto.CreateShortUrlDto;
import jansegety.urlshortener.controller.jsondto.CreateShortUrlDto.Result;
import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.service.UrlPackService;
import jansegety.urlshortener.service.encoding.Encoder;
import jansegety.urlshortener.util.UrlMaker;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/urlpack/util")
@RequiredArgsConstructor
public class UrlPackJsonController {

	private final UrlPackService urlPackService;
	private final Encoder<Long, String> encoder;
	
	
	/*
	 * 등록된 application인지 탐색하고 
	 * 만약 있다면 해당 어플리케이션의 urlPack으로 등록한다.
	 * 만약 없다면 어플리케이션을 등록해달라는 안내페이지로 이동시킨다.
	 */
	@PostMapping("/shorturl")
	public CreateShortUrlDto create(@RequestParam("url") String originalUrl,
			@SessionAttribute User clientUser) {
		
		//성공 로직
		UrlPack urlPack = new UrlPack();
		urlPack.setUser(clientUser); //유저 할당
		urlPack.setOriginalUrl(originalUrl);
		urlPackService.regist(urlPack);
		urlPack.createValueEncoded(encoder);
		
		CreateShortUrlDto createShortUrlDto = makeCreateShortUrlDto(urlPack);
		
		return createShortUrlDto;
	}


	private CreateShortUrlDto makeCreateShortUrlDto(UrlPack urlPack) {
		CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto();
		
		Result result = createShortUrlDto.new Result();
		result.setOrgUrl(urlPack.getOriginalUrl());
		result.setValueEncoded(urlPack.getValueEncoded());
		result.setUrl(UrlMaker.makeUrlWithDomain(urlPack.getValueEncoded()));
		
		createShortUrlDto.setResult(result);
		return createShortUrlDto;
	}
	
}
