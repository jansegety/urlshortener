package jansegety.urlshortener.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jansegety.urlshortener.controller.viewdto.UrlInfoForUrlPackListDto;
import jansegety.urlshortener.controller.viewdto.UrlPackListDto;
import jansegety.urlshortener.controller.viewdto.UrlPackRegistFormDto;
import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.service.UrlPackService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/urlpack")
@RequiredArgsConstructor
public class UrlPackController {
	
	private final UrlPackService urlPackService;
	private final ApplicationContext applicationContext;

	@RequestMapping(value = "/registform", method = RequestMethod.GET)
	public String createForm() {
		
		return "urlpack/registform";
	}
	
	
	@RequestMapping(value = "/registform", method = RequestMethod.POST)
	public String create(@RequestParam(value="longUrl") String longUrl, Model model) {
		
		UrlPack urlPack = applicationContext.getBean(UrlPack.class);
		urlPack.setLongUrl(longUrl);
		urlPackService.registCreatingShortUrl(urlPack);
		
		UrlPackRegistFormDto registFormDto = new UrlPackRegistFormDto();
		registFormDto.setLongUrl(urlPack.getLongUrl());
		registFormDto.setShortUrl(urlPack.getShortUrl());
		model.addAttribute("registFormDto", registFormDto);
		
		return "urlpack/registform";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String show(Model model) {
		
		List<UrlPack> urlPackList = urlPackService.findUrlPackList();
		
		ArrayList<UrlInfoForUrlPackListDto> urlInfoList = new ArrayList<UrlInfoForUrlPackListDto>();
		for(UrlPack urlPack : urlPackList)
		{
			UrlInfoForUrlPackListDto urlInfo = new UrlInfoForUrlPackListDto();
			urlInfo.setLongUrl(urlPack.getLongUrl());
			urlInfo.setShortUrl(urlPack.getShortUrl());
			urlInfo.setRequstNum(urlPack.getRequestNum());
			urlInfoList.add(urlInfo);
		}
		UrlPackListDto urlPackListDto = new UrlPackListDto();
		urlPackListDto.setUrlInfoList(urlInfoList);
		model.addAttribute("urlPackListDto", urlPackListDto);
		
		return "urlpack/list";
	}
	
    
	
	
}
