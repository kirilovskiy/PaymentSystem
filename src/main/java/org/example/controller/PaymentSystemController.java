package org.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Основной контроллер платежной системы
 */
@RestController
@RequestMapping("/payments")
public class PaymentSystemController {

	@GetMapping
	public ResponseEntity<String> getStatus() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
