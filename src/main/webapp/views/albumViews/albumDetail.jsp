<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<div class="content-wrapper">

   <div class="thumbnail">
   		<img src="${pageContext.request.contextPath}/album/albumImage?id=${albumDetail.albumID}" alt="..." class="img-responsive">
  
     <div class="caption detail">
       <h3>${albumDetail.title}</h3>     
		<div class="price">${albumDetail.price}</div>
		<p class="description">Genre: ${ genreAlbum }</p>
		<p class="description">Artist: ${ artistAlbum }</p> 
		<div class="clearfix">
		<form name="EditDelete" method="POST">
		<a href="${pageContext.request.contextPath}/cart/addCart?id=${albumDetail.albumID}" class="btn btn-success pull-left" role="button" name="AddCart">Add to Cart</a>
		
		</form>
		</div>
     </div>
   </div>
	
</div>
