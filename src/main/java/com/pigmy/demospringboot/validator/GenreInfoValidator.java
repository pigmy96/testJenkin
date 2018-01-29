package com.pigmy.demospringboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pigmy.demospringboot.model.GenreInfo;

@Component
public class GenreInfoValidator implements Validator{
	@Override
	  public boolean supports(Class<?> clazz) {
	      return clazz == GenreInfo.class;
	  }

	  @Override
	  public void validate(Object target, Errors errors) {
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.genreForm.name");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.genreForm.description");
	  }
}
