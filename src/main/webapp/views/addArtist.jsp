<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="content-wrapper">
<div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Genre Information - ADD NEW GENRE</h3>
            </div>
            
             <c:if test="${not empty errorMessage }">
             <div class="alert alert-danger">
        		 ${errorMessage}
    		 </div>
  			 </c:if>
            <!-- form start -->
            <form:form modelAttribute="artistForm" method="POST">
              <div class="box-body">
                 <div class="form-group">
                  <label for="exampleInputName">Name</label>
                  <form:input path="name" type="text" class="form-control" placeholder="Enter name..."/>
              	 <form:errors path="name" class="error-message" style="color: red"/>
                </div>   
              </div>
              <!-- /.box-body -->

              <div class="box-footer">
			  	 &nbsp;
               <input type="submit" class="btn btn-primary" value="Save" /> 
               <input type="reset"  class="btn btn-success" value="Reset" />
              </div>
            </form:form>
    </div>


 </div> 
