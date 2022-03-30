package jansegety.urlshortener.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Table(name = "user")
@Entity
@Getter
public class User {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@OneToMany(mappedBy ="user")
	List<UrlPack> urlPackList = new ArrayList<>();
	
	@OneToMany(mappedBy ="user")
	List<ClientApplication> clientApplicationList = new ArrayList<>();
	
	
	public void setId(Long id) {
		
		if(this.id != null)
			throw new IllegalStateException("id가 이미 할당되었습니다.");
		
		this.id = id;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public int hashCode() {
		
		int result = (int) (id ^ (id >>> 32));
	    result = 31 * result + id.hashCode();
	    
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		
		User user = (User)obj;
		
		return this.id == user.id;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
	
	
	
	
}
