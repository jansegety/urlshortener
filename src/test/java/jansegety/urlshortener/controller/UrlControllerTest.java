package jansegety.urlshortener.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlControllerTest {

	UrlPackController urlController = new UrlPackController();
	
	@Test
	@DisplayName("createForm 함수는 특별한 예외가 없다면 문자열 urlpack/registform 반환")
	void when_requestFormWithNoException_then_createFormFuncReturnStringUrlPackRegistForm() {
		
		assertThat(urlController.createForm(),is("urlpack/registform"));
		
	}
	
	@Test
	@DisplayName("create 함수는 특별한 예외가 없다면 문자열 urlpack/registform 반환")
	void when_requestCreateNewEntityWithNoException_then_createFuncReturnStringUrlPackRegistForm() {
		assertThat(urlController.create(),is("urlpack/registform"));
	}
	
	
	@Test
	@DisplayName("show 함수는 특별한 예외가 없다면 문자열 urlpack/list 반환")
	void when_requestShowListWithNoException_then_showFuncReturnStringUrlPackList() {
		assertThat(urlController.show(),is("urlpack/list"));
	}
	
	

}
