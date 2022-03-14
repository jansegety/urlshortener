package jansegety.urlshortener.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jansegety.urlshortener.service.encoding.Encoder;


/*
 * id가 할당될 때 shortUrl이 초기화 된다.
 */
@Component
@Scope("prototype")
public class UrlPack{
	
	private Long id;
	
	private String longUrl;
	private String shortUrl;
	private Integer requestNum=0;

	private Encoder<Long, String> encoder;
	
	
	@Autowired
	public UrlPack(Encoder<Long, String> encoder) {
		this.encoder = encoder;
	};
	
	
	public Long getId() {
		return id;
	}
	public void setIdCreatingShortUrl(Long id) {
		
		if(isIdAssigned())
			throw new IllegalStateException("id가 이미 할당되었습니다.");
		
		createShortUrl(id); //id할당시 자동으로 shortUrl 생성
		
		this.id = id;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	
	public Integer getRequestNum() {
		return requestNum;
	}


	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}


	public boolean isIdAssigned()
	{
		boolean isIdAssigned = true;
		if(id == null)
			isIdAssigned = false;
		
		return isIdAssigned;
	}
	
	
	public String requestShortUrlWithLongUrl(String longUrl)
	{
		if(!this.longUrl.equals(longUrl))
			throw new IllegalArgumentException("longUrl이 일치하지 않습니다.");
		
		requestNum++;
		
		return shortUrl;
	}
	
	
	private void createShortUrl(Long id) {
		this.shortUrl = encoder.encoding(id);
	}
	
	
	@Override
	public String toString() {
		return "UrlPack [id=" + id + ", longUrl=" + longUrl + ", shortUrl=" + shortUrl + "]";
	}
	
	
	

}
