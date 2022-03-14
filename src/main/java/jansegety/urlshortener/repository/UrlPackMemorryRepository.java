package jansegety.urlshortener.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jansegety.urlshortener.entity.UrlPack;

@Repository
public class UrlPackMemorryRepository implements UrlPackRepository{
	
    private List<UrlPack> urlPackList = new ArrayList<>();

	@Override
	synchronized public void save(UrlPack urlPack) {
		
		if(urlPack.isIdAssigned())
			throw new IllegalArgumentException("이미 id가 할당된 entity입니다.");
			
		//Id 할당
		int lastIndex = urlPackList.size();
		System.out.println("마지막 index + 1 =" + Long.valueOf(lastIndex+1));
		urlPack.setIdCreatingShortUrl(Long.valueOf(lastIndex+1));
		
		
		urlPackList.add(urlPack);
		
	}
	

	@Override
	public List<UrlPack> findAll() {
		
		
		return urlPackList ;
	}


	@Override
	public void deleteAll() {
		
		this.urlPackList = new ArrayList<>();
	}

	
	

}
