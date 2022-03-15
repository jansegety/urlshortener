package jansegety.urlshortener.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jansegety.urlshortener.repository.UrlPackRepository;
import jansegety.urlshortener.service.encoding.Encoder;
import jansegety.urlshortener.util.UrlMaker;

@SpringBootTest
class UrlPackJsonControllerTest {
	
	
	@Autowired
	private UrlPackJsonController jsonController;
	@Autowired
	private UrlPackRepository urlPackRepository;
	@Autowired
	private Encoder<Long, String> encoder;
	
	private MockMvc mock;
	
	private MediaType jsonType = new MediaType(MediaType.APPLICATION_JSON.getType(),	
										MediaType.APPLICATION_JSON.getSubtype(),
										Charset.forName("utf8"));
	
	
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(jsonController).build();
		urlPackRepository.deleteAll();
	}

	@Test
	@DisplayName("/urlpack/util/shorturl로 요청이 오면 원래url 단축url 정보 등이 포함된 createShortUrlDto 객체 형태로 응답이 되어야 한다.")
	void when_requestShortUrl_then_responseInTheFormOfCreateShortUrlDto() throws Exception {
		
		String longUrl = "http://www.long.www.long.www.long.wwwlong";
		
		mock.perform(post("/urlpack/util/shorturl").param("longUrl",longUrl).contentType(jsonType))
		.andExpect(status().isOk())
		.andExpect(header().string("Connection", "keep-alive"))
		.andExpect(jsonPath("$.message", is("ok")))
		.andExpect(jsonPath("$.code", is("200")))
		.andExpect(jsonPath("$.result.orgUrl", is(longUrl)))
		.andExpect(jsonPath("$.result.valueEncoded", is(encoder.encoding(1L))))
		.andExpect(jsonPath("$.result.url", is(UrlMaker.makeUrlWithDomain(encoder.encoding(1L)))))
		.andDo(print());
		
		
		
	}

}
