package com.pigmy.demospringboot.validator;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.model.AccountInfo;

@Component
public class RegisterValidator implements Validator {

	  @Autowired
	  private AccountDAO accountDAO;

	  private EmailValidator emailValidator = EmailValidator.getInstance();
	  @Override
	  public boolean supports(Class<?> clazz) {
	      return clazz == AccountInfo.class;
	  }

	  @Override
	  public void validate(Object target, Errors errors) {
	      AccountInfo accountInfo = (AccountInfo) target;

	 
	      // Kiểm tra các trưởng của account.

	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.registerForm.username");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registerForm.password");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.registerForm.email");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.registerForm.confirmPassword");
	      if (accountInfo.getEmail().length() > 0 &&!emailValidator.isValid(accountInfo.getEmail())) {
	           errors.rejectValue("email", "Pattern.registerForm.email");
	       }
	      
	      if(accountInfo.getPassword().length() > 0){
	    	  if(accountInfo.getPassword().length() >= 4){
	    		  for (int i = 0; i < accountInfo.getPassword().length(); i++){
	    			  if ((accountInfo.getPassword().charAt(i) >= '0' && accountInfo.getPassword().charAt(i) <= '9')
	    				 ||(accountInfo.getPassword().charAt(i) >= 'a' && accountInfo.getPassword().charAt(i) <= 'z')
	    				 ||(accountInfo.getPassword().charAt(i) >= 'A' && accountInfo.getPassword().charAt(i) <= 'B')){
	    				  
	    			  }
	    			  else{
	    				  errors.rejectValue("password", "NotValid.registerForm.password");
	    				  break;
	    			  }
	    		  }
	    	  }
	    	  else{
	    		  errors.rejectValue("password", "AtLeast.registerForm.password");
	    	  }
	      }
	      
	      String username = accountInfo.getUsername();
	      if (username != null && username.length() > 0) {
	              Account account = accountDAO.findAccount(username);
	              if (account != null) {
	           		   errors.rejectValue("username", "Exist.registerForm.username");
	              }
	              else{
	            	  System.out.println("confirmPassword" + accountInfo.getConfirmPassword());
	            	  System.out.println("Password" + accountInfo.getPassword());
	            	  if(accountInfo.getConfirmPassword().equals(accountInfo.getPassword()) == false)
	                  errors.rejectValue("confirmPassword", "NotMatch.registerForm.confirmPassword");
	              }
	      }



	  }

	}