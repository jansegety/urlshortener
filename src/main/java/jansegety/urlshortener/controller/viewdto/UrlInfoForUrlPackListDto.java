package jansegety.urlshortener.controller.viewdto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UrlInfoForUrlPackListDto {

	private String originalUrl;
	private String shortUrl;
	private int requstNum;
	
}
