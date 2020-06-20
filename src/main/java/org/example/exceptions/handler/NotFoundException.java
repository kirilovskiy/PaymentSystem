package org.example.exceptions.handler;

public class NotFoundException extends RuntimeException {

	private static final String TITLE = "Запрашиваемая сущность не найдена";

	public NotFoundException(String message) {
		super(message);
	}

	public String getTitle() {
		return TITLE;
	}

}
