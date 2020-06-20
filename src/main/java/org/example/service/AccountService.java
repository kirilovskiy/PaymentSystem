package org.example.service;

import org.example.model.AccountModel;
import org.example.model.TransferInfo;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

	List<AccountModel> getList();

	AccountModel getBy(Long accountNumber);

	AccountModel save(AccountModel account);

	void deleteBy(Long id);

	AccountModel putMoney(BigDecimal amount, Long accountNumber);

	AccountModel withdrawMoney(BigDecimal amount, Long accountNumber);

	void transfer(TransferInfo transferInfo);
}
