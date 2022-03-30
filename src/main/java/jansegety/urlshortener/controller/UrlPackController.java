package jansegety.urlshortener.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jansegety.urlshortener.controller.viewdto.UrlInfoForUrlPackListDto;
import jansegety.urlshortener.controller.viewdto.UrlPackListDto;
import jansegety.urlshortener.controller.viewdto.UrlPackRegistConfirmationDto;
import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.service.UrlPackService;
import jansegety.urlshortener.service.UserService;
import jansegety.urlshortener.util.UrlMaker;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/urlpack")
@RequiredArgsConstructor
public class UrlPackController {
	
	private final UrlPackService urlPackService;
	private final UserService userService;
	private final ApplicationContext applicationContext;


	@RequestMapping(value = "/registform", method = RequestMethod.GET)
	public String createForm() {
		
		return "/urlpack/registform";
	}
	
	
	@RequestMapping(value = "/registform", method = RequestMethod.POST)
	public String create(@RequestParam String longUrl, @RequestAttribute(required=true) User loginUser, Model model) {
		
		UrlPack urlPack = applicationContext.getBean(UrlPack.class);
		urlPack.setLongUrl(longUrl);
		
		//urlPack에 user 정보도 등록
		urlPack.setUser(loginUser); //set user to urlpack
		urlPackService.registAndEncoding(urlPack);// save
		
		UrlPackRegistConfirmationDto registFormDto = new UrlPackRegistConfirmationDto();
		registFormDto.setLongUrl(urlPack.getLongUrl());
		registFormDto.setShortUrl(UrlMaker.makeUrlWithDomain(urlPack.getValueEncoded()));
		model.addAttribute("registFormDto", registFormDto);
		
		return "/urlpack/registconfirmation";
	}
	
	
	//로그인 유저의 urlpack list를 넣은 뷰를 반환한다
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String show(Model model, @RequestAttribute(required=true) User loginUser) {
	
		
		List<UrlPack> urlPackList = urlPackService.findByUser(loginUser);
		
		ArrayList<UrlInfoForUrlPackListDto> urlInfoList = new ArrayList<UrlInfoForUrlPackListDto>();
		for(UrlPack urlPack : urlPackList)
		{
			UrlInfoForUrlPackListDto urlInfo = new UrlInfoForUrlPackListDto();
			urlInfo.setLongUrl(urlPack.getLongUrl());
			urlInfo.setShortUrl(UrlMaker.makeUrlWithDomain(urlPack.getValueEncoded()));
			urlInfo.setRequstNum(urlPack.getRequestNum());
			urlInfoList.add(urlInfo);
		}
		UrlPackListDto urlPackListDto = new UrlPackListDto();
		urlPackListDto.setUrlInfoList(urlInfoList);
		model.addAttribute("urlPackListDto", urlPackListDto);
		
		return "/urlpack/list";
	}
	
    
	
	
}
