package jansegety.urlshortener.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Table(name = "client_application")
@Entity
@Getter
public class ClientApplication {
	
	//객체에서 UUID로 할당된다.
	//DB에서 128bit 크기의 Binay타입으로 바뀐다
	@Id @GeneratedValue
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "client_secret")
	private UUID clientSecret;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	

	public void setId(UUID id){
		if(this.id != null)
			throw new IllegalStateException("id가 이미 할당되었습니다.");
		
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClientSecret(UUID clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean equalsClientSecret(UUID clientSecret) {
		return this.clientSecret.equals(clientSecret);
	}

	@Override
	public String toString() {
		return "ClientApplication [id=" + id + ", name=" + name 
				+ ", clientSecret=" + clientSecret + "]";
	}	

}
