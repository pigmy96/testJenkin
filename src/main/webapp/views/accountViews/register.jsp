<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="content-wrapper">
<div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Account Information - REGISTER</h3>
            </div>
            <div class="col-md-4 col-md-offset-4">
             <c:if test="${not empty errorMessage }">
             <div class="alert alert-danger">
        		 ${errorMessage}
    		 </div>
  			 </c:if>
            <!-- form start -->
            <form:form modelAttribute="accountForm" method="POST">
              <div class="box-body">
                 <div class="form-group">
                  	<label for="exampleInputUsername">Username</label>
                  	<form:input path="username" type="text" class="form-control" placeholder="Enter user name..."/>
              	 <form:errors path="username" class="error-message" style="color: red"/>
                </div>
				
				<div class="form-group">
                  	<label for="exampleInputEmail">Email</label>
                 	 <form:input path="email" type="text" class="form-control" placeholder="Enter email..."/>
              	 <form:errors path="email" class="error-message" style="color: red"/>
                </div>
                
                <div class="form-group">
					<label for="password">Password</label>
					<form:input path="password" type="password" id="password" name="password" class="form-control" placeholder="Enter password..."/>
              	 	<form:errors path="password" class="error-message" style="color: red"/>
				</div>
				
				<div class="form-group">
					<label for="confirmpassword">Confirm Password</label>
					<form:input path="confirmPassword" type="password" class="form-control" placeholder="Confirm password..."/>
              	 	<form:errors path="confirmPassword" class="error-message" style="color: red"/>
				</div>
               
              </div>
              <!-- /.box-body -->

              
               <input type="submit" class="btn btn-primary" value="Register" /> 
            </form:form>
            </div>
    </div>


 </div>