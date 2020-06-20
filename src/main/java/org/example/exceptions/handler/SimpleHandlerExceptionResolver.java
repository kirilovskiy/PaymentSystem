package org.example.exceptions.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.NegativeBalanceException;
import org.example.exceptions.NotFoundException;
import org.example.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.example.model.ApiError.SERVER_ERROR_CODE;

@ControllerAdvice
@Slf4j
public class SimpleHandlerExceptionResolver {

	private static final String LOG_FORMAT = "Resolve exception [%s]";

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public void resolveNotFoundException(HttpServletResponse response, NotFoundException ex) {
		log.debug(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);
		generateResponse(response, new ApiError(ApiError.RESOURCE_NOT_FOUND, ex.getTitle(), ex.getMessage()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NegativeBalanceException.class)
	public void resolveNegativeBalanceException(HttpServletResponse response, NegativeBalanceException ex) {
		log.debug(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);
		generateResponse(response, new ApiError(ApiError.ILLEGAL_STATE, ex.getTitle(), ex.getMessage()));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public void resolveException(HttpServletResponse response, Throwable ex) {
		log.debug(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);
		generateResponse(response, new ApiError(SERVER_ERROR_CODE, ex));
	}

	private void generateResponse(HttpServletResponse response, ApiError apiError) {
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		try {
			String resp = new ObjectMapper().writeValueAsString(apiError);
			response.getWriter().print(resp);
		} catch (IOException e) {
			log.error("Failure get response writer: ", e);
		}
	}

}
