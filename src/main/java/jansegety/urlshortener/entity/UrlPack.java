package jansegety.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jansegety.urlshortener.service.encoding.Encoder;


/*
 * id가 할당될 때 shortUrl이 초기화 된다.
 */
@Table(name = "url_pack")
@Entity
public class UrlPack{
	
	@Id @GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "original_url")
	private String origianlUrl;
	@Column(name = "value_encoded")
	private String valueEncoded;
	
	@Column(name = "request_num")
	private Integer requestNum=0;
	
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		
		if(this.id != null)
			throw new IllegalStateException("id가 이미 할당되었습니다.");
		
		this.id = id;
	}
	public String getOriginalUrl() {
		return origianlUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.origianlUrl = originalUrl;
	}
	public String getValueEncoded() {
		return valueEncoded;
	}
	
	public Integer getRequestNum() {
		return requestNum;
	}
	
	public User getUser() {
		return user;
	}

	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String requestShortUrlWithoriginalUrl(String originalUrl) {
		if(!this.origianlUrl.equals(originalUrl))
			throw new IllegalArgumentException("originalUrl이 일치하지 않습니다.");
		requestNum++;
		return valueEncoded;
	}
	
	
	public void createValueEncoded(Encoder<Long, String> encoder) {
		
		if(id==null)
			throw new IllegalStateException("encoding할 id가 아직 할당되지 않았습니다.");
		
		this.valueEncoded = encoder.encoding(this.id);
	}
	
	@Override
	public String toString() {
		return "UrlPack [id=" + id + ", originalUrl=" + origianlUrl 
				+ ", valueEncoded=" + valueEncoded + "]";
	}

}
