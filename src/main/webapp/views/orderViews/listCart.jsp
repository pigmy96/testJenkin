<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="content-wrapper">
	<a href="${pageContext.request.contextPath}/cart/confirmCheckout" class="btn btn-primary pull-left btn-add" role="button" name="Add">Check Out >></a>
	<table id="example2" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th></th>
				<th>Action</th>
				<th>Album</th>
				<th>Price (each)</th>
				<th>Quantity</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ cartList }" var="list" varStatus="status">
				<tr>
					<td>
						<span>${ status.index + 1 }</span>
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/cart/remove?id=${ list.cartID }">
			            	<i class="fa fa-trash"></i>
			          	</a>
					</td>
					<td>
					<span>${list.albumTitle }</span>
					</td>
					<td>
					<span>${list.unitPrice }</span>
					</td>
					<td>
					<span>${list.quantity }</span>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="thumbnail">
     <div class="caption detail">
       <h3>Total: ${ total }</h3>
		</div>
     </div>
   </div>
</div>