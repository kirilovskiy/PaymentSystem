package org.example.service.impl;

import org.example.converter.AccountConverter;
import org.example.entity.Account;
import org.example.exceptions.NegativeBalanceException;
import org.example.exceptions.NotFoundException;
import org.example.model.AccountModel;
import org.example.model.TransferInfo;
import org.example.repo.AccountRepository;
import org.example.service.AccountService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

	private static final AccountConverter ACCOUNT_CONVERTER = Mappers.getMapper(AccountConverter.class);
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<AccountModel> getList() {
		return ACCOUNT_CONVERTER.entityToModelList(accountRepository.findAll());
	}

	@Override
	public AccountModel save(AccountModel accountModel) {
		Account account = ACCOUNT_CONVERTER.modelToEntity(accountModel);
		return ACCOUNT_CONVERTER.entityToModel(accountRepository.save(account));
	}

	@Override
	public List<AccountModel> saveAll(List<AccountModel> accountModels) {
		List<Account> account = ACCOUNT_CONVERTER.modelToEntityList(accountModels);
		return ACCOUNT_CONVERTER.entityToModelList(accountRepository.saveAll(account));
	}

	@Override
	public AccountModel getBy(Long accountNumber) {
		return ACCOUNT_CONVERTER.entityToModel(
				accountRepository.getByAccountNumber(accountNumber)
						.orElseThrow(() -> new NotFoundException(String.format("Счет: {%s} не найден!", accountNumber)))
		);
	}

	@Override
	public void deleteBy(Long id) {
		accountRepository.deleteById(id);
	}

	@Override
	@Transactional
	public synchronized AccountModel putMoney(BigDecimal amount, Long accountNumber) {
		AccountModel currentAccount = getBy(accountNumber);
		currentAccount.setAmount(currentAccount.getAmount().add(amount));
		return save(currentAccount);
	}

	@Override
	@Transactional
	public synchronized AccountModel withdrawMoney(BigDecimal amount, Long accountNumber) {
		AccountModel currentAccount = getBy(accountNumber);
		if (currentAccount.getAmount().compareTo(amount) < 0) {
			throw new NegativeBalanceException("Баланс по счету не может быть отрицательным!");
		}
		currentAccount.setAmount(currentAccount.getAmount().subtract(amount));
		return save(currentAccount);
	}

	@Override
	@Transactional
	public void transfer(TransferInfo transferInfo) {
		AccountModel fromAccount = getBy(transferInfo.getAccountNumberFrom());
		AccountModel toAccount = getBy(transferInfo.getAccountNumberTo());
		if (transferInfo.getAccountNumberFrom() < transferInfo.getAccountNumberTo()) {
			synchronized (fromAccount) {
				synchronized (toAccount) {
					doTransfer(fromAccount, toAccount, transferInfo.getAmount());
				}
			}
		} else  {
			synchronized (toAccount) {
				synchronized (fromAccount) {
					doTransfer(fromAccount, toAccount, transferInfo.getAmount());
				}
			}
		}
	}

	private void doTransfer(final AccountModel fromAccount, final AccountModel toAccount, final BigDecimal amount) {
		/*withdrawMoney(amount, fromAccount.getAccountNumber());
		putMoney(amount, toAccount.getAccountNumber());*/
		if (fromAccount.getAmount().compareTo(amount) < 0) {
			throw new NegativeBalanceException("Баланс по счету не может быть отрицательным!");
		}
		fromAccount.setAmount(fromAccount.getAmount().subtract(amount));
		toAccount.setAmount(toAccount.getAmount().add(amount));

		this.saveAll(Arrays.asList(fromAccount, toAccount));
	}

}
