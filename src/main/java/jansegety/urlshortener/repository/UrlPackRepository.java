package jansegety.urlshortener.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jansegety.urlshortener.entity.UrlPack;

@Repository
public interface UrlPackRepository {

	
	public void save(UrlPack urlPack);
	
	public List<UrlPack> findAll();
	
	public void deleteAll();
	
	
}
