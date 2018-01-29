<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="content-wrapper">
<div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Shipping Information</h3>
            </div>
            
             <c:if test="${not empty errorMessage }">
             <div class="alert alert-danger">
        		 ${errorMessage}
    		 </div>
  			 </c:if>
            <!-- form start -->
            <form:form modelAttribute="orderForm" method="POST">
              <div class="box-body">
                 <div class="form-group">
                  <label for="exampleInputFirstName">First Name</label>
                  <form:input path="firstName" type="text" class="form-control" placeholder="Enter first name..."/>

              	 <form:errors path="firstName" class="error-message" style="color: red"/>

                </div>
                
				<div class="form-group">
                  <label for="exampleInputLastName">Last Name</label>
                  <form:input path="lastName" type="text" class="form-control" placeholder="Enter last name..."/>

              	 <form:errors path="lastName" class="error-message" style="color: red"/>
                </div>
				
				<div class="form-group">
                  <label for="exampleInputAddress">Address</label>
                  <form:input path="address" type="text" class="form-control" placeholder="Enter address..."/>

              	 <form:errors path="address" class="error-message" style="color: red"/>
                </div>
               
               <div class="form-group">
                  <label for="exampleInputCity">City</label>
                  <form:input path="city" type="text" class="form-control" placeholder="Enter city..."/>

              	 <form:errors path="city" class="error-message" style="color: red"/>
                </div>
                
                <div class="form-group">
                  <label for="exampleInputState">State</label>
                  <form:input path="state" type="text" class="form-control" placeholder="Enter state..."/>

              	 <form:errors path="state" class="error-message" style="color: red"/>
                </div>
                
                <div class="form-group">
                  <label for="exampleInputPostalCode">Postal Code</label>
                  <form:input path="postalCode" type="text" class="form-control" placeholder="Enter postal code..."/>

              	 <form:errors path="postalCode" class="error-message" style="color: red"/>
                </div>
                
                <div class="form-group">
                  <label for="exampleInputCountry">Country</label>
                  <form:input path="country" type="text" class="form-control" placeholder="Enter country..."/>

              	 <form:errors path="country" class="error-message" style="color: red"/>
                </div>
                
               <div class="form-group">
                  <label for="exampleInputPhone">Phone</label>
                  <form:input path="phone" type="text" class="form-control" placeholder="Enter phone..."/>

              	 <form:errors path="phone" class="error-message" style="color: red"/>
                </div>
                
                <div class="form-group">
                  <label for="exampleInputEmail">Email Address</label>
                  <form:input path="email" type="text" class="form-control" placeholder="Enter email address..."/>

              	 <form:errors path="email" class="error-message" style="color: red"/>
                </div>
                
              </div>
              <!-- /.box-body -->

              <div class="box-footer">
			  	 &nbsp;
               <input type="submit" class="btn btn-primary" value="Submit Order" /> 
              </div>
            </form:form>
    </div>


 </div>