package jansegety.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jansegety.urlshortener.service.encoding.Encoder;


/*
 * id가 할당될 때 shortUrl이 초기화 된다.
 */
@Table(name = "url_pack")
@Entity
@Component
@Scope("prototype")
public class UrlPack{
	
	@Id @GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "long_url")
	private String longUrl;
	@Column(name = "value_encoded")
	private String valueEncoded;
	
	@Column(name = "request_num")
	private Integer requestNum=0;
	
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;

	private Encoder<Long, String> encoder;
	
	
	@Autowired
	public UrlPack(Encoder<Long, String> encoder) {
		this.encoder = encoder;
	};
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		
		if(this.id != null)
			throw new IllegalStateException("id가 이미 할당되었습니다.");
		
		this.id = id;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
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

	
	public String requestShortUrlWithLongUrl(String longUrl)
	{
		if(!this.longUrl.equals(longUrl))
			throw new IllegalArgumentException("longUrl이 일치하지 않습니다.");
		
		requestNum++;
		
		return valueEncoded;
	}
	
	
	public void createValueEncoded() {
		
		if(id==null)
			throw new IllegalStateException("encoding할 id가 아직 할당되지 않았습니다.");
		
		this.valueEncoded = encoder.encoding(this.id);
	}
	
	
	@Override
	public String toString() {
		return "UrlPack [id=" + id + ", longUrl=" + longUrl + ", valueEncoded=" + valueEncoded + "]";
	}
	
	
	

}
