package jansegety.urlshortener.controller.viewdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UrlPackRegistConfirmationDto {
	
	private String originalUrl;
	private String shortUrl;

}
