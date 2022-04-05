package jansegety.urlshortener.service.encoding;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements Encoder<Long, String>{
	
	final int RADIX = 62;
	final String CODEC = 
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	@Override
	public String encoding(Long source) {
		
		StringBuffer sb = new StringBuffer();
		while(source > 0) {
			sb.append(CODEC.charAt((int) (source % RADIX)));
			source /= RADIX;
		}
		return sb.toString();
	}

	@Override
	public Long decoding(String encoded) {
		long sum = 0;
		long power = 1;
		for (int i = 0; i < encoded.length(); i++) {
			sum += CODEC.indexOf(encoded.charAt(i)) * power;
			power *= RADIX;
		}
		return sum;
	}

}
