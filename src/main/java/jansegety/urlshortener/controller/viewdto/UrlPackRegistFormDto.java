package jansegety.urlshortener.controller.viewdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UrlPackRegistFormDto {
	
	private String longUrl;
	private String shortUrl;

}
