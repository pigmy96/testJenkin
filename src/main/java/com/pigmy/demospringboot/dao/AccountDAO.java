package com.pigmy.demospringboot.dao;

import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.model.AccountInfo;

public interface AccountDAO {

   
   public Account findAccount(String userName);
   public void save(AccountInfo accountInfo);
}