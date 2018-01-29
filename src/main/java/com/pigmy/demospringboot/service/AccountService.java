package com.pigmy.demospringboot.service;

import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.model.AccountInfo;
import org.springframework.stereotype.Service;

public interface AccountService {
    public boolean isLogin();
    public AccountInfo getInfoUserLoggedIn();
    public AccountInfo register(AccountInfo accountInfo);
    public Account getUserLoggedIn();
}
