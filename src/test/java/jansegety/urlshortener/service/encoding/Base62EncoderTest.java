package jansegety.urlshortener.service.encoding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class Base62EncoderTest {

	
	Base62Encoder encoder = new Base62Encoder();
	
	@Test
	@DisplayName("인코딩 한 값을 다시 디코딩하면 소스와 같아야 한다.")
	void when_encodeSource_then_theReturnValueDecodedIsSameAsTheSource() {
		
		String encodedValue = encoder.encoding(1L);
		assertThat(encoder.decoding(encodedValue), equalTo(1L));
		
		
		
		String encodedValue2 = encoder.encoding(10034524342L);
		assertThat(encoder.decoding(encodedValue2), equalTo(10034524342L));
		
		
	}
	

}
