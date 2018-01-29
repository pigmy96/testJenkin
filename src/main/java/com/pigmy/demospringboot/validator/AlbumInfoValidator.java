package com.pigmy.demospringboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pigmy.demospringboot.model.AlbumInfo;
@Component
public class AlbumInfoValidator implements Validator {
	 
	  @Override
	  public boolean supports(Class<?> clazz) {
	      return clazz == AlbumInfo.class;
	  }

	  @Override
	  public void validate(Object target, Errors errors) {
	      AlbumInfo albumInfo = (AlbumInfo) target;

	 

	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.albumForm.title");
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priceString", "NotEmpty.albumForm.price");
	      String priceString = albumInfo.getPriceString();
	      
	      boolean checkNumber = true;
	      int countDot = 0;
	      if (priceString.length() > 0){
			  for (int i = 0; i < priceString.length(); i++){
				  if (priceString.charAt(i) < '0' || priceString.charAt(i) > '9'){
					  if (priceString.charAt(i) == '.'){
						  countDot++;
						  if(countDot > 1){
							  checkNumber = false;
							  break;
						  }
					  }
					  else{
						  checkNumber = false;
						  break;
					  }
				  }
			  }
			  if (checkNumber == false){
				  errors.rejectValue("priceString", "NotPositive.albumForm.price");
			  }
			  else{
				  double price = Double.parseDouble(priceString);
				  System.out.println("price: " + price);
				  if(price <= 0){
					  errors.rejectValue("priceString", "NotPositive.albumForm.price");
				  }
			  }
	      }

	      System.out.println("FILE: " + albumInfo.getFileData());
	      System.out.println("FILE SIZE: " + albumInfo.getFileData().getSize());
	      if (albumInfo.getFileData().getSize() > 0) {
	    	  String imageType = albumInfo.getFileData().getContentType();
	    	  System.out.println("Imgae type: " + imageType);
	    	  if(imageType.indexOf("image") >= 0){
	    		  if(albumInfo.getFileData().getSize() > (2*1024*1024)){
	    			  errors.rejectValue("fileData", "MaximumSize.albumForm.fileData");
	    		  }
	    	  }
	    	  else{
	    		  errors.rejectValue("fileData", "NotImage.albumForm.fileData");
	    	  }
	      }

	  }

}
