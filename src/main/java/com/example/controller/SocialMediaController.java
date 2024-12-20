package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MessageDTO;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidMessageLengthException;
import com.example.exception.InvalidUsernameException;
import com.example.exception.MessageDoesntExistException;
import com.example.exception.UserDoesntExistException;
import com.example.exception.InvalidPasswordException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account acct){

        try {
            Account newAccount = accountService.registerAccount(acct);
            return ResponseEntity.status(200).body(newAccount);
            
        } catch (AccountAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
            
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acct) {
        try {
            Account user = accountService.login(acct.getUsername(), acct.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(user);

        } catch (InvalidUsernameException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message){
        try {
            Message newMessage = messageService.newMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);
        } catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (InvalidMessageLengthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageById(messageId));

    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){

        //TODO: put a try catch block for exception

        Integer deleted = messageService.deleteMessageById(messageId);
        if( deleted == 1){
            return ResponseEntity.status(HttpStatus.OK).body(deleted);
        }
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageTextById(@PathVariable int messageId, @RequestBody MessageDTO messageRequest){
        try {
            String messageText = messageRequest.getMessageText();
            Integer updatedMessage = messageService.updateMessageTextById(messageId, messageText);
            if( updatedMessage == 1){
                return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
            }

        } catch (MessageDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (InvalidMessageLengthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesByAccountId(accountId));
    }
}

