package org.example.converter;

import org.example.entity.Account;
import org.example.model.AccountModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AccountConverter {

	Account modelToEntity(AccountModel model);

	AccountModel entityToModel(Account entity);

	List<AccountModel> entityToModelList(List<Account> entities);

}
