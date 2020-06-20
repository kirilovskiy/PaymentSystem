package org.example.repo;

import org.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> getByAccountNumber(Long accountNumber);
}
