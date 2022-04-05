package jansegety.urlshortener.controller.jsondto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateShortUrlDto {
	
	private Result result;
	
	@Getter
	@Setter
	@ToString
	public class Result{
		
		private String valueEncoded;
		private String url;
		private String orgUrl;
		
	}
	
}
