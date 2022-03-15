package jansegety.urlshortener.entity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jansegety.urlshortener.service.encoding.Base62Encoder;
import jansegety.urlshortener.service.encoding.Encoder;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConfigForUrlPackTest.class})
@ActiveProfiles({"test"})
class UrlPackTest {
	
	@Autowired
	private UrlPack urlPack;
	@Autowired
	private UrlPack urlPack1;
	@Autowired
	private UrlPack urlPack2;

	@Test
	@DisplayName("prototype Scope bean은 DI시 항상 다른 객체여야 한다.")
	void when_prototypeScopeBeanDI_then_TheMustDifferentObject() {
		
		assertNotNull(urlPack1);
		assertNotNull(urlPack2);
		assertThat(urlPack1, is(not(urlPack2)));
		
	}
	
	
	@Test
	@DisplayName("id가 할당된 상태에서 다시 할당하면 IllegalStateException 발생")
	void when_idReAssigned_then_throwIllegalStateException() {
		
		urlPack.setIdCreatingShortUrl(1L);
		assertThrows(IllegalStateException.class, ()->{
			urlPack.setIdCreatingShortUrl(2L);
		});
		
	}
	
	@Test
	@DisplayName("id가 할당되면 자동으로 shortUrl이 생성되어야 한다.")
	void when_idAssigned_then_shortUrlMustbeCreated() {
		
		Encoder<Long, String> encoder = new Base62Encoder();
		
		urlPack.setIdCreatingShortUrl(12345L);
		String encodedValue = encoder.encoding(12345L);
		
		assertThat(urlPack.getValueEncoded(), is(equalTo(encodedValue)));
		
	}
	
	@Test
	@DisplayName("일치하지 않는 longUrl로 shortUrl을 요청하면 IllegalArgumentException 발생")
	void when_requestShortUrlWithMisMatchedLongUrl_then_throwIllegalArgumentException() {
		
		UrlPack urlPack = new UrlPack(new Base62Encoder());
		urlPack.setLongUrl("AAA.long.url");
		
		assertThrows(IllegalArgumentException.class, ()->{
			urlPack.requestShortUrlWithLongUrl("BBB.long.url");
		});
	}
	

}
