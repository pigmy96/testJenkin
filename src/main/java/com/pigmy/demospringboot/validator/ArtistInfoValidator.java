package com.pigmy.demospringboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pigmy.demospringboot.model.ArtistInfo;

@Component
public class ArtistInfoValidator implements Validator{
	@Override
	  public boolean supports(Class<?> clazz) {
	      return clazz == ArtistInfo.class;
	  }

	  @Override
	  public void validate(Object target, Errors errors) {
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.artistForm.name");
	  }
}
