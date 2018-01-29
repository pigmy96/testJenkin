package com.pigmy.demospringboot.service.impl;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.model.AccountInfo;
import com.pigmy.demospringboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountDAO accountDAO;
    @Override
    public boolean isLogin(){
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public AccountInfo getInfoUserLoggedIn(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountDAO.findAccount(userDetails.getUsername());
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUsername(account.getUserName());
        accountInfo.setPass(account.getPassword());
        accountInfo.setActive(account.isActive());
        accountInfo.setRole(account.getUserRole().getName());
        accountInfo.setEmail(account.getEmail());
        return accountInfo;
    }

    @Override
    public AccountInfo register(AccountInfo accountInfo){
        String password = accountInfo.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        accountInfo.setPassword(password);
        try {
            accountDAO.save(accountInfo);
        } catch (Exception e) {
           return null;

        }
        return accountInfo;
    }

    @Override
    public Account getUserLoggedIn(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountDAO.findAccount(userDetails.getUsername());
        return account;
    }
}
