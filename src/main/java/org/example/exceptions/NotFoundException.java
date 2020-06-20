package org.example.exceptions;

public class NotFoundException extends RuntimeException {

	private static final String TITLE = "Запрашиваемая сущность не найдена";

	public NotFoundException(String message) {
		super(message);
	}

	public String getTitle() {
		return TITLE;
	}

}
