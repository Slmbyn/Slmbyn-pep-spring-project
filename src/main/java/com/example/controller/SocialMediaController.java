package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidPasswordException;
import com.example.service.AccountService;

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

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account acct){

        try {
            Account newAccount = accountService.registerAccount(acct);
            return ResponseEntity.status(200).body(newAccount);

        } catch (AccountAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}



/**
 *     @GetMapping("/string/{text}")
    public String getStringPathVariable(@PathVariable String text){
        return text;
    }
 * 
 *      
@PostMapping(value = "/requestbody") <- can omit 'value = '
public Sample postSample(@RequestBody Sample pojo){
    //you will need to change the method's parameters and return the extracted request body.
    return pojo;
}

@GetMapping("/exampleHeaders")
public ResponseEntity headers(@RequestBody Sample sample){
    return ResponseEntity.status(200).header("content-type", "application/zip").body(sample);
}
 */