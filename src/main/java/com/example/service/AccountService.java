package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidPasswordException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account){
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

        if(existingAccount.isPresent()) {
            throw new AccountAlreadyExistsException();
        }

        if(account.getPassword().length() < 4){
            throw new InvalidPasswordException();
        }

        return accountRepository.save(account);
    }

}
