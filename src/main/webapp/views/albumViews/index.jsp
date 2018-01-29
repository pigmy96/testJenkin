<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="content-wrapper">
	<img src="${pageContext.request.contextPath}/images/home-showcase.png" id="imgHome" alt="..." class="img-responsive center-block" >
	<c:if test="${genreName != null}">
		<h3>${ genreName } Albums</h3>
	</c:if>
	<c:if test="${genreName == null}">
		<h3>All Albums</h3>
	</c:if>
	<c:forEach items="${ albumList }" var="list1" varStatus="status">
		<div class="row">
			<c:forEach items="${ list1 }" var="list2">
  				<div class="col-sm-6 col-md-4">
    				<div class="thumbnail">
					   	<img src="${pageContext.request.contextPath}/album/albumImage?id=${list2.albumID}" alt="..." class="img-responsive">
      					<div class="caption">
        					<h3>${list2.title}</h3>
        					<div class="clearfix">
								<div class="price pull-left">$${list2.price}</div>
								<a href="${pageContext.request.contextPath}/album/detail?id=${list2.albumID}" class="btn btn-info pull-right" role="button" name="Detail">View Detail</a>
							</div>
      					</div>
    				</div>
				</div>
			</c:forEach>
  		</div>

 	</c:forEach>

</div>
