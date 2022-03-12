package jansegety.urlshortener.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jansegety.urlshortener.entity.UrlPack;

@Service
public interface UrlPackService {
	
	public void registCreatingShortUrl(UrlPack urlPack);
	
	public List<UrlPack> findUrlPackList();
}
