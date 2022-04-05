package jansegety.urlshortener.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.repository.UrlPackRepository;
import jansegety.urlshortener.service.UrlPackService;
import jansegety.urlshortener.service.encoding.Encoder;


@SpringBootTest
class RedirectControllerTest {
	
	@Autowired
	private RedirectController redirectController;
	@Autowired
	private UrlPackController urlController;
	@Autowired
	private UrlPackRepository urlPackRepository;
	@Autowired
	private UrlPackService urlPackService;
	
	@Autowired
	private Encoder<Long, String> encoder;
	
	private MockMvc mock;
	
	
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(urlController, redirectController).build();
		urlPackRepository.deleteAll();
		UrlPack testUrlPack = new UrlPack();
		testUrlPack.setOriginalUrl("WWW.ABCDEFG.HIJKLMNOP");
		urlPackService.regist(testUrlPack);
		testUrlPack.createValueEncoded(encoder);
	}

	@Test
	@DisplayName("등록된 shortUrl로 요청시 redirect 응답")
	void when_requestShortUrlRegistered_then_responseRedirectTooriginalUrl() throws Exception {
		
		String shortUrl = encoder.encoding(1L);
		mock.perform(get("/"+shortUrl))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("WWW.ABCDEFG.HIJKLMNOP"));

	}
	
	@Test
	@DisplayName("등록된 shortUrl로 요청시 요청횟수 1 증가")
	void when_requestShortUrlRegistered_then_requestNumPlusOne() throws Exception {
		
		String valueEncoded = encoder.encoding(1L);
		UrlPack urlPack = urlPackService.findByValueEncoded(valueEncoded).get();
		
		mock.perform(get("/"+valueEncoded)); //request 1
		assertThat(urlPack.getRequestNum(), equalTo(1));
		
		mock.perform(get("/"+valueEncoded)); //request 2
		assertThat(urlPack.getRequestNum(), equalTo(2));
		
	}
	
	@Test
	@DisplayName("등록되지 않은 shortUrl로 요청시 400 bad request 응답")
	void when_requestShortUrlNotRegistered_then_responseBadRequest400() throws Exception {
		String shortUrl = encoder.encoding(10L);
		mock.perform(get("/"+shortUrl)).andExpect(status().is4xxClientError());
		
	}

}
