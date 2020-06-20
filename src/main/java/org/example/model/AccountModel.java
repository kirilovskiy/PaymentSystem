package org.example.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccountModel {
	Long id;
	@Min(value = 0, message = "Сумма должна быть положительной")
	@NotNull
	BigDecimal amount;
	@NotNull
	Long accountNumber;
}
