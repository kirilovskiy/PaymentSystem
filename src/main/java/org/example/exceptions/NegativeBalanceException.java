package org.example.exceptions;

public class NegativeBalanceException extends RuntimeException {

	private static final String TITLE = "Отрицательный баланс";

	public NegativeBalanceException(String message) {
		super(message);
	}

	public String getTitle() {
		return TITLE;
	}
}
