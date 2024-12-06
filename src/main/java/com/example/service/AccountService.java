package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidUsernameException;
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
    
    public Account login(String username, String password){
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        
        // if it doesnt exist
        if (existingAccount.isEmpty()){
            throw new InvalidUsernameException();
        }
        
        Account acct = existingAccount.get();
        
        // if exists but passwords dont match
        if (!acct.getPassword().equals(password)){
            throw new InvalidPasswordException();
        }


        // it exists and passwords match
        return acct;
    }

}
