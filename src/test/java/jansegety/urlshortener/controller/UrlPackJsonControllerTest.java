package jansegety.urlshortener.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jansegety.urlshortener.entity.ClientApplication;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.repository.ClientApplicationRepository;
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
	private ClientApplicationRepository clientApplicationRepository;
	
	@Autowired
	private Encoder<Long, String> encoder;
	
	private MockMvc mock;
	
	private MediaType jsonType = new MediaType(MediaType.APPLICATION_JSON.getType(),	
										MediaType.APPLICATION_JSON.getSubtype(),
										Charset.forName("utf8"));
	
	private final String LONG_URL = "http://www.long.www.long.www.long.wwwlong";
	
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(jsonController).build();
		urlPackRepository.deleteAll();
		clientApplicationRepository.deleteAll();
	}

	@Test
	@DisplayName("/urlpack/util/shorturl로 요청이 오면 client-id와 client-secret가 검증이 통과되야 "
			+ "원래url 단축url 정보 등이 포함된 createShortUrlDto객체 형태로 응답이 되어야 한다.")
	void when_requestShortUrlClientIdAndClientSecretMustPassVerification_then_responseInTheFormOfCreateShortUrlDto() throws Exception {
		
		//clientApplication 설정
		ClientApplication clientApplication = new ClientApplication();
		clientApplication.setName("테스트용 클라이언트");
		clientApplication.setClientSecret(UUID.randomUUID()); //id는 영속화 될 때 할당된다.
		
		
		//clientApplication에 유저 할당
		User user = new User();
		clientApplication.setUser(user);
		
		//clientApplication 영속화
		clientApplicationRepository.save(clientApplication); 
		
		
		mock.perform(post("/urlpack/util/shorturl").param("url",LONG_URL).accept(jsonType)
				.header("urlshortener-client-id", clientApplication.getId().toString())
				.header("urlshortener-client-secret", clientApplication.getClientSecret().toString()))
		.andExpect(status().isOk())
		.andExpect(header().string("Connection", "keep-alive"))
		.andExpect(jsonPath("$.message", is("ok")))
		.andExpect(jsonPath("$.code", is("200")))
		.andExpect(jsonPath("$.result.orgUrl", is(LONG_URL)))
		.andExpect(jsonPath("$.result.valueEncoded", is(encoder.encoding(1L))))
		.andExpect(jsonPath("$.result.url", is(UrlMaker.makeUrlWithDomain(encoder.encoding(1L)))));
		
		
		
	}

}
