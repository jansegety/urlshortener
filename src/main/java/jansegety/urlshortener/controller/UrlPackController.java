package jansegety.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/urlpack")
public class UrlPackController {

	@RequestMapping(value = "/registform", method = RequestMethod.GET)
	public String createForm() {
		return "urlpack/registform";
	}
	
	
	@RequestMapping(value = "/registform", method = RequestMethod.POST)
	public String create() {
		return "urlpack/registform";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String show() {
		return "urlpack/list";
	}
	
	
}
