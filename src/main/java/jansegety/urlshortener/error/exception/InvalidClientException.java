package jansegety.urlshortener.error.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidClientException extends RuntimeException{
	public InvalidClientException(String mas) {super(mas);}
}
