package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageLengthException;
import com.example.exception.UserDoesntExistException;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message newMessage(Message message){
        // user must exist
        Optional<Account> existingAccount = accountRepository.findById(message.getPostedBy());
        
        if(existingAccount.isEmpty()){
            throw new UserDoesntExistException();
        }
        
        // messageText not blank and less than 255 chars
        if(message.getMessageText().length() == 0 || message.getMessageText().length() > 255 ) {
            throw new InvalidMessageLengthException();
        }
        
        return messageRepository.save(message);
    }

    public Message getMessageById(int messageId){
        Optional<Message> foundMessage = messageRepository.findById(messageId);
        return foundMessage.orElse(null);
    }

    public int deleteMessageById(int messageId){
        Optional<Message> foundMessage = messageRepository.findById(messageId);

        if(foundMessage.isEmpty()){
            return 0;
        }

        messageRepository.deleteById(messageId);
        return 1;
    }
}
