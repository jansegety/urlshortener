package jansegety.urlshortener.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.repository.UrlPackRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplUrlPackService implements UrlPackService {

	private final UrlPackRepository urlPackRepository;
	
	@Override
	public void registAndEncoding(UrlPack urlPack) {
		
		//urlPcak은 id가 할당된다.
		urlPackRepository.save(urlPack);
		//할당된 id로 valueEncoded 생성
		urlPack.createValueEncoded();
		
		
	}

	@Override
	public List<UrlPack> findAll() {
		
		return urlPackRepository.findAll();
	}

	@Override
	public Optional<UrlPack> findByValueEncoded(String valueEncoded) {
		
		UrlPack urlPackOrNull = findAll().stream().filter(urlPack -> urlPack.getValueEncoded().equals(valueEncoded)).findAny().orElse(null);
		return Optional.ofNullable(urlPackOrNull);
		
	}

	@Override
	public List<UrlPack> findByUser(User user) {
		
		return urlPackRepository.findByUser(user);
	}

}
