package jansegety.urlshortener.repository;

import java.util.ArrayList;
import java.util.List;

import jansegety.urlshortener.entity.UrlPack;

public class UrlPackMemorryRepository implements UrlPackRepository{
	
    private final List<UrlPack> urlPackList = new ArrayList<>();

	@Override
	synchronized public void save(UrlPack urlPack) {
		
		if(urlPack.isIdAssigned())
			throw new IllegalArgumentException("이미 id가 할당된 entity입니다.");
			
		//Id 할당
		int lastIndex = urlPackList.size();
		urlPack.setIdCreatingShortUrl(Long.valueOf(lastIndex+1));
		
		
		urlPackList.add(urlPack);
		
	}
	

	@Override
	public List<UrlPack> findList() {
		
		List<UrlPack> copyOfList = new ArrayList<UrlPack>();
		for( UrlPack urlPack : urlPackList )
		{
				copyOfList.add(urlPack.clone());
		}
		
		return copyOfList ;
	}
	
	
	

}
