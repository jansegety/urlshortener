package jansegety.urlshortener.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jansegety.urlshortener.controller.viewdto.UrlInfoForUrlPackListDto;
import jansegety.urlshortener.repository.UrlPackRepository;
import jansegety.urlshortener.service.encoding.Encoder;
import jansegety.urlshortener.util.UrlMaker;

@SpringBootTest
class UrlControllerTest {

	@Autowired
	private UrlPackController urlController;
	@Autowired
	private UrlPackRepository urlPackRepository;
	
	@Autowired
	private Encoder<Long, String> encoder;
	
	private MockMvc mock;
	
	
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(urlController).build();
		urlPackRepository.deleteAll();
	}
	
	@Test
	@DisplayName("createForm 함수는 특별한 예외가 없다면 문자열 urlpack/registform 반환")
	void when_requestFormWithNoException_then_createFormFuncReturnStringUrlPackRegistForm() throws Exception {
		
		mock.perform(get("/urlpack/registform")
		.param("longUrl", "WWW.ABCDEFG.HIJKLMNOP"))
		.andExpect(status().isOk())
		.andExpect(view().name("urlpack/registform"));
		
		
	}
	
	
	
	@Test
	@DisplayName("create 함수는 특별한 예외가 없다면 문자열 urlpack/registconfirmation 반환")
	void when_requestCreateNewEntityWithNoException_then_createFuncReturnStringUrlPackRegistConfrimation() throws Exception {
		
		mock.perform(post("/urlpack/registform")
		.param("longUrl", "WWW.ABCDEFG.HIJKLMNOP"))
		.andExpect(status().isOk())
		.andExpect(view().name("urlpack/registconfirmation"));
	
	}
	
	
	@Test
	@DisplayName("create 함수는 입력받은 longUrl과 단축된 shortUrl을 가지는 RegistFormDto를 모델에 포함")
	void when_requestCreateNewEntityWithNoException_then_modelHasRegistFormDtoObjectWithLongUrlAndShortUrl() throws Exception {
		
		    String longUrl = "WWW.ABCDEFG.HIJKLMNOP";
		
			mock.perform(post("/urlpack/registform")
				.param("longUrl", longUrl))
				.andExpect(model().attribute("registFormDto", hasProperty("longUrl", equalTo(longUrl))))
				.andExpect(model().attribute("registFormDto", hasProperty("shortUrl", equalTo(UrlMaker.makeUrlWithDomain(encoder.encoding(1L)))))); //B is Encoded Value by base62 with 1L
		
	}
	
	
	
	@Test
	@DisplayName("show 함수는 특별한 예외가 없다면 문자열 urlpack/list 반환")
	void when_requestShowListWithNoException_then_showFuncReturnStringUrlPackList() throws Exception {
		
		mock.perform(get("/urlpack/list"))
		.andExpect(status().isOk())
		.andExpect(view().name("urlpack/list"));
		
	}
	
	@Test
	@DisplayName("show 함수는 urlPack 저장소에 있던 list를 model에 dto로 넣어 반환")
	void when_requestShowListWithNoException_then_putTheListInTheUrlPackStorageAsDtoInTheModelAndReturnIt() throws Exception {
		
		mock.perform(post("/urlpack/registform").param("longUrl", "AAA.AAA.AAA"));
		mock.perform(post("/urlpack/registform").param("longUrl", "BBB.BBB.BBB"));
		
		UrlInfoForUrlPackListDto urlInfo1 = new UrlInfoForUrlPackListDto();
		urlInfo1.setLongUrl("AAA.AAA.AAA");
		urlInfo1.setShortUrl(UrlMaker.makeUrlWithDomain(encoder.encoding(1L)));
		urlInfo1.setRequstNum(0);
		
		UrlInfoForUrlPackListDto urlInfo2 = new UrlInfoForUrlPackListDto();
		urlInfo2.setLongUrl("BBB.BBB.BBB");
		urlInfo2.setShortUrl(UrlMaker.makeUrlWithDomain(encoder.encoding(2L)));
		urlInfo2.setRequstNum(0);
		
		mock.perform(get("/urlpack/list"))
		.andExpect(model().attribute("urlPackListDto", 
				hasProperty("urlInfoList", 
				hasItems(urlInfo1, urlInfo2))));
		
	}
	
	
	

	
	

}
