package jansegety.urlshortener.service.encoding;

import org.springframework.stereotype.Component;

@Component
public interface Encoder<S,T> {
	
	public T encoding(S source);
	public S decoding(T encoded);
	

}
