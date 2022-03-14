package jansegety.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jansegety.urlshortener.entity.ConfigForUrlPackTest;

@SpringBootTest
@ActiveProfiles({"production"})
class UrlshortenerApplicationTests {

	@Test
	void contextLoads() {
	}

}
