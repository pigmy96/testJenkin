package com.pigmy.demospringboot.validator;

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pigmy.demospringboot.model.OrderInfo;

@Component
public class OrderInfoValidator implements Validator {
	 private EmailValidator emailValidator = EmailValidator.getInstance();
	  @Override
	  public boolean supports(Class<?> clazz) {
	      return clazz == OrderInfo.class;
	  }

	  @Override
	  public void validate(Object target, Errors errors) {
		  OrderInfo orderInfo = (OrderInfo) target;
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.orderForm.firstName");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.orderForm.lastName");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.orderForm.address");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty.orderForm.city");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "NotEmpty.orderForm.state");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "NotEmpty.orderForm.postalCode");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.orderForm.country");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.orderForm.phone");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.orderForm.email");
	      if (orderInfo.getEmail().length() > 0 && !emailValidator.isValid(orderInfo.getEmail())) {
	           errors.rejectValue("email", "Pattern.orderForm.email");
	       }
	      System.out.println("length phone:" + orderInfo.getPhone().length());
	      if(orderInfo.getPhone().length() >= 10 && orderInfo.getPhone().length() <= 11){
	    	  for(int i = 0; i < orderInfo.getPhone().length(); i++){
	    		  if (orderInfo.getPhone().charAt(i) < '0' || orderInfo.getPhone().charAt(i) > '9')
	    		  {
	    			  errors.rejectValue("phone", "NotValid.orderForm.phone");
	    			  break;
	    		  }
	    	  }
	      }
	      else if (orderInfo.getPhone().length() > 0){
	    	  errors.rejectValue("phone", "Length.orderForm.phone");
	      }
	  }

}