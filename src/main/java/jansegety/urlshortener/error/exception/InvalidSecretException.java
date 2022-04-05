package jansegety.urlshortener.error.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidSecretException extends RuntimeException{
	public InvalidSecretException(String msg) {super(msg);}
}
