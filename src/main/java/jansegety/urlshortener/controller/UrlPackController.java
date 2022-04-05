package jansegety.urlshortener.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jansegety.urlshortener.controller.viewdto.UrlInfoForUrlPackListDto;
import jansegety.urlshortener.controller.viewdto.UrlPackListDto;
import jansegety.urlshortener.controller.viewdto.UrlPackRegistConfirmationDto;
import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.service.UrlPackService;
import jansegety.urlshortener.service.encoding.Encoder;
import jansegety.urlshortener.util.UrlMaker;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/urlpack")
@RequiredArgsConstructor
public class UrlPackController {
	
	
	private final UrlPackService urlPackService;
	private final Encoder<Long, String> encoder;


	@RequestMapping(value = "/registform", method = RequestMethod.GET)
	public String createForm() {
		return "/urlpack/registform";
	}
	
	@RequestMapping(value = "/registform", method = RequestMethod.POST)
	public String create(@RequestParam String originalUrl, 
			@RequestAttribute(required=true) User loginUser, 
			Model model) {
		
		UrlPack urlPack = new UrlPack();
		urlPack.setOriginalUrl(originalUrl);
		
		//urlPack에 user 정보도 등록
		urlPack.setUser(loginUser);
		urlPackService.regist(urlPack);// save and get id
		urlPack.createValueEncoded(encoder);
		
		UrlPackRegistConfirmationDto registFormDto = makeRgistFormDto(urlPack);
		model.addAttribute("registFormDto", registFormDto);
		
		return "/urlpack/registconfirmation";
	}


	private UrlPackRegistConfirmationDto makeRgistFormDto(UrlPack urlPack) {
		
		UrlPackRegistConfirmationDto registFormDto = new UrlPackRegistConfirmationDto();
		registFormDto.setOriginalUrl(urlPack.getOriginalUrl());
		registFormDto.setShortUrl(UrlMaker.makeUrlWithDomain(urlPack.getValueEncoded()));
		
		return registFormDto;
		
	}
	
	
	//로그인 유저의 urlpack list를 넣은 뷰를 반환한다
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String show(Model model, @RequestAttribute(required=true) User loginUser) {
	
		List<UrlPack> urlPackList = urlPackService.findByUser(loginUser);
		
		UrlPackListDto urlPackListDto = makeUrlPackListDto(urlPackList);
		model.addAttribute("urlPackListDto", urlPackListDto);
		
		return "/urlpack/list";
	}


	private UrlPackListDto makeUrlPackListDto(List<UrlPack> urlPackList) {
		List<UrlInfoForUrlPackListDto> urlInfoList = new ArrayList<>();
		for(UrlPack urlPack : urlPackList)
		{
			UrlInfoForUrlPackListDto urlInfo = new UrlInfoForUrlPackListDto();
			urlInfo.setOriginalUrl(urlPack.getOriginalUrl());
			urlInfo.setShortUrl(UrlMaker.makeUrlWithDomain(urlPack.getValueEncoded()));
			urlInfo.setRequstNum(urlPack.getRequestNum());
			urlInfoList.add(urlInfo);
		}
		UrlPackListDto urlPackListDto = new UrlPackListDto();
		urlPackListDto.setUrlInfoList(urlInfoList);
		return urlPackListDto;
	}
}
