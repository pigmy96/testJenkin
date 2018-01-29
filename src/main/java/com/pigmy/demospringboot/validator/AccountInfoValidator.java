package com.pigmy.demospringboot.validator;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.model.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

 
//Khai báo là 1 @Component (Ngoại lệ 1 Bean).
@Component
public class AccountInfoValidator implements Validator {

  @Autowired
  private AccountDAO accountDAO;

 
  @Override
  public boolean supports(Class<?> clazz) {
      return clazz == AccountInfo.class;
  }

  @Override
  public void validate(Object target, Errors errors) {
      AccountInfo accountInfo = (AccountInfo) target;

 
      // Kiểm tra các trưởng của account.

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.loginForm.username");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.loginForm.password");
      String username = accountInfo.getUsername();
      if (username != null && username.length() > 0) {
          if(accountInfo.isNewAccount()) {
              Account account = accountDAO.findAccount(username);
              if (account != null) {
           	   if (accountInfo.getPass().equals(account.getPassword())==false)
           	   {
           		   errors.rejectValue("password", "Wrong.accountForm.password");
           	   }
              }
              else{
                  errors.rejectValue("username", "NotFound.loginForm.account");
              }
              }
          }


  }

}