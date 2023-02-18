package me.jamespurvis.blogsite.service;

import me.jamespurvis.blogsite.models.Account;
import me.jamespurvis.blogsite.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
