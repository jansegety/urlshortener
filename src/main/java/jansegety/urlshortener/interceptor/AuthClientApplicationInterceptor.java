package jansegety.urlshortener.interceptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jansegety.urlshortener.entity.ClientApplication;
import jansegety.urlshortener.entity.User;
import jansegety.urlshortener.error.exception.InvalidClientException;
import jansegety.urlshortener.error.exception.InvalidSecretException;
import jansegety.urlshortener.service.ClientApplicationService;
import lombok.RequiredArgsConstructor;

/*
 * rest 통신을 하는 client application을 검증합니다.
 */
@RequiredArgsConstructor
public class AuthClientApplicationInterceptor implements HandlerInterceptor {
	
	@Value("${path.authClient.essential}")
	public List<String> authClientEssential;

	private final ClientApplicationService clientApplicationService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		
		//application 검증 로직
	    String clientId = request.getHeader("urlshortener-client-id");
	    String clientSecret = request.getHeader("urlshortener-client-secret");
	
		//urlshortener-client-id 혹은 urlshortener-client-secret가 없다면
		if(!StringUtils.hasText(clientId)||!StringUtils.hasText(clientSecret)) {
			throw new IllegalArgumentException("잘못된 헤더");
		}
		
		UUID clientIdUUID = UUID.fromString(clientId);
		UUID clientSecretUUID = UUID.fromString(clientSecret);
		
		Optional<ClientApplication> clientApplicationOp = 
			clientApplicationService.findById(clientIdUUID);
		
		//일치하는 client가 없다면
		if(clientApplicationOp.isEmpty()) {
			throw new InvalidClientException("잘못된 client");
		}
		
		//secret이 일치하지 않는다면
		ClientApplication clientApplication = clientApplicationOp.get();
		if(!clientApplication.equalsClientSecret(clientSecretUUID)) {
			throw new InvalidSecretException("잘못된 secret");
		}
			
		//user 검증 로직
		User clientUser = clientApplication.getUser();
		if(clientUser == null) {
			throw new IllegalStateException("클라이언트에 유저 정보 없음");
		}
		
		//client를 등록한 유저 세션에 등록
		HttpSession session = request.getSession();
		session.setAttribute("clientUser", clientUser);		
		
		return true;
	}
	
}
