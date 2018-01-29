<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="content-wrapper">

 <div class="col-md-4 col-md-offset-4">
	<h1>Sign In</h1>
	
	<c:if test="${param.error == 'true'}">
		<div class="alert alert-danger">
			Login Failed!!!<br />
               Invalid username and password!
               <br/>
               
		</div>
	</c:if>
	<form method="POST"
           action="${pageContext.request.contextPath}/j_spring_security_check">
		<div class="form-group">
			<label for="email">Username</label>
			<input type="text" id="username" name="userName" class="form-control">
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" id="password" name="password" class="form-control">
		</div>
		<input type="hidden" name="_csrf" value="{{ csrfToken }}">
		&nbsp;
		<input type="submit" value="Login" class="btn btn-primary">
		<input type="reset" value="Reset" class="btn btn-success" /></td>
	</form>
	<span class="error-message">${error }</span>
 </div>

</div>
 