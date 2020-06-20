package org.example.controller;

import org.example.model.AccountModel;
import org.example.model.TransferInfo;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Основной контроллер платежной системы
 */
@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountRest {

	@Autowired
	private AccountService accountService;

	@GetMapping
	public ResponseEntity<List<AccountModel>> getAccounts() {
		return new ResponseEntity<>(accountService.getList(), HttpStatus.OK);
	}

	@GetMapping("/{number}")
	public ResponseEntity<AccountModel> getAccountBy(@PathVariable(name = "number") Long accountNumber) {
		return new ResponseEntity<>(accountService.getBy(accountNumber), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountModel> create(@Valid @RequestBody AccountModel accountModel) {
		return new ResponseEntity<>(accountService.save(accountModel), HttpStatus.OK);
	}

	@DeleteMapping
	public void delete(Long id) {
		accountService.deleteBy(id);
	}

	@PostMapping("/putmoney")
	public ResponseEntity<AccountModel> putMoney(@Valid @RequestBody AccountModel accountModel) {
		return new ResponseEntity<>(
				accountService.putMoney(accountModel.getAmount(), accountModel.getAccountNumber()),
				HttpStatus.OK
		);
	}

	@PostMapping("/withdrawmoney")
	public ResponseEntity<AccountModel> withdrawMoney(@Valid @RequestBody AccountModel accountModel) {
		return new ResponseEntity<>(
				accountService.withdrawMoney(accountModel.getAmount(), accountModel.getAccountNumber()),
				HttpStatus.OK
		);
	}

	@PostMapping("/transfer")
	public ResponseEntity<String> transferMoney(@Valid @RequestBody TransferInfo transferInfo) {
		accountService.transfer(transferInfo);
		return new ResponseEntity<>("Перевод средств успешно осуществлен!", HttpStatus.OK);
	}

}
