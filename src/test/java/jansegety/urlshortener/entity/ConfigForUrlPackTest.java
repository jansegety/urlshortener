package jansegety.urlshortener.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import jansegety.urlshortener.service.encoding.Base62Encoder;
import jansegety.urlshortener.service.encoding.Encoder;

@Configuration
public class ConfigForUrlPackTest {
	
	@Bean
	@Scope("prototype") //class에 prototype 선언이 되어 있어도 그것은 componant scan시에 작동한다.
	public UrlPack urlPack() {
		
		return new UrlPack(encoder());
	}
	
	
	@Bean
	public Encoder encoder() {
		return new Base62Encoder();
	}

}
