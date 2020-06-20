package org.example.exceptions.handler;

public class NegativeBalanceException extends RuntimeException {

	private static final String TITLE = "Отрицательный баланс";

	public NegativeBalanceException(String message) {
		super(message);
	}

	public String getTitle() {
		return TITLE;
	}
}
