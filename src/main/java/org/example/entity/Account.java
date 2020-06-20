package org.example.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Account {
	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	long id;
	@NonNull
	BigDecimal amount;
	@NonNull
	@Column(name = "account_number", unique = true)
	Long accountNumber;

}
