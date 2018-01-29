<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="content-wrapper">
	<a href="${pageContext.request.contextPath}/album/manage/add" class="btn btn-primary pull-left btn-add" role="button" name="Add">Add new album</a>
	<table id="example2" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Action</th>
				<th>Title</th>
				<th>Artist</th>
				<th>Genre</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ albumList }" var="list">
				<tr>
					<td>
						<a href="${pageContext.request.contextPath}/album/manage/edit?id=${ list.albumID }">
			            	<i class="fa fa-edit"></i>
			          	</a>
			          	<a id="${ list.albumID }" onClick="deleteAlbum(id)" >
			            	<i class="fa fa-trash"></i>
			          	</a>
					</td>
					<td>
					<span>${list.title }</span>
					</td>
					<td>
					<span>${list.genreName }</span>
					</td>
					<td>
					<span>${list.artistName }</span>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
 <script src="${pageContext.request.contextPath}/javascripts/deleteAlbum.js"></script>