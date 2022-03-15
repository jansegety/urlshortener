package jansegety.urlshortener.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jansegety.urlshortener.entity.UrlPack;
import jansegety.urlshortener.repository.UrlPackRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplUrlPackService implements UrlPackService {

	private final UrlPackRepository urlPackRepository;
	
	@Override
	public void registAndEncoding(UrlPack urlPack) {
		
		//urlPcak은 id가 저장되면서 할당된다.
		//urlPack은 id가 할당 될 때 자동으로 valueEncoded을 생성한다.
		urlPackRepository.save(urlPack);
		
	}

	@Override
	public List<UrlPack> findUrlPackList() {
		
		return urlPackRepository.findAll();
	}

	@Override
	public Optional<UrlPack> findByValueEncoded(String valueEncoded) {
		
		UrlPack urlPackOrNull = findUrlPackList().stream().filter(urlPack -> urlPack.getValueEncoded().equals(valueEncoded)).findAny().orElse(null);
		return Optional.ofNullable(urlPackOrNull);
		
	}

}
