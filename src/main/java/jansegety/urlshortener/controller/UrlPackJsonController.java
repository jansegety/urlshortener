package jansegety.urlshortener.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jansegety.urlshortener.controller.jsondto.CreateShortUrlDto;
import jansegety.urlshortener.controller.jsondto.CreateShortUrlDto.Result;
import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.service.UrlPackService;
import jansegety.urlshortener.util.UrlMaker;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/urlpack/util")
@RequiredArgsConstructor
public class UrlPackJsonController {

	private final ApplicationContext applicationContext;
	private final UrlPackService urlPackService;
	
	@PostMapping("/shorturl")
	public Object create(@RequestParam String longUrl, HttpServletResponse response) {
	
		UrlPack urlPack = applicationContext.getBean(UrlPack.class);
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
