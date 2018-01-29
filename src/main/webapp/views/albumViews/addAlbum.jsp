<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="content-wrapper">
<div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Album Information - ADD ALBUM</h3>
            </div>
            
             <c:if test="${not empty errorMessage }">
             <div class="alert alert-danger">
        		 ${errorMessage}
    		 </div>
  			 </c:if>
            <!-- form start -->
            <form:form modelAttribute="albumForm" method="POST" enctype="multipart/form-data">
              <div class="box-body">
                 <div class="form-group">
                  <label>Title</label>
                  <form:input path="title" type="text" class="form-control" placeholder="Enter title..."/>

              	 <form:errors path="title" class="error-message" style="color: red"/>

                </div>
				
				<div class="form-group">
                  <label>Genre</label>
                  <br/>
                  <form:select  path="genreID">
				    <form:options items="${genreList}" itemValue="genreID" itemLabel="name"/>
				  </form:select>
              	 <form:errors path="genreID" class="error-message" style="color: red"/>   
        
              	 </div>
				<div class="form-group">
                  <label>Artist</label>
                  <br/>
                  <form:select  path="artistID">
				    <form:options items="${artistList}" itemValue="artistID" itemLabel="name"/>
				  </form:select>
              	 <form:errors path="artistID" class="error-message" style="color: red"/>   
        
              	 </div>
               <div class="form-group">
                  <label>Price</label>
                  <form:input path="priceString" type="text" class="form-control" placeholder="Enter price..."/>

              	 <form:errors path="priceString" class="error-message" style="color: red"/> 

                </div>
               <div class="form-group">
               <div class="thumbnail">
               	<img id="previewImage" src="#" class="img-responsive upload"/>
               </div>
               	
                  <label>Upload Image</label>
                 <form:input path="fileData" type="file" class="form-control" id="uploadImage"/>

              	 <form:errors path="fileData" class="error-message" style="color: red"/> 

                </div>
                
              </div>
              <!-- /.box-body -->

              <div class="box-footer">
			  	 &nbsp;
               <input type="submit" class="btn btn-primary" value="Save" /> 
              </div>
            </form:form>
    </div>


 </div>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
 
 <script src="${pageContext.request.contextPath}/javascripts/upload.js"></script>