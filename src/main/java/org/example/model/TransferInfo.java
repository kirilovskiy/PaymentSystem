package org.example.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferInfo {
	@NotNull(message = "Номер счета не может быть null")
	Long accountNumberFrom;
	@NotNull(message = "Номер счета не может быть null")
	Long accountNumberTo;
	@NotNull(message = "Сумма для перервода  не может быть null")
	@Min(value = 0, message = "Сумма для перервода должна быть положительной")
	BigDecimal amount;
}
