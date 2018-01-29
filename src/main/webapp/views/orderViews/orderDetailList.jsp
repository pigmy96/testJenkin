<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<div class="content-wrapper">

   <div class="thumbnail">
     <div class="caption detail">
     	<h3>Order Detail</h3> 
     	<div class="order-detail-box">
     	<div class="order-detail">
	     	<p class="description">Order Date: ${ order.orderDate }</p>
			<p class="description">First Name: ${ order.firstName }</p> 
			<p class="description">Address: ${ order.address }</p> 
			<p class="description">State: ${ order.state }</p> 
			<p class="description">Country: ${ order.country } </p> 
			<p class="description">Email: ${ order.email }</p> 
			<div class="price">Total: ${order.total}</div>
     	</div>
		<div class="order-detail">
			<p class="description">User Name: ${ order.userName }</p>
			<p class="description">Last Name: ${ order.lastName }</p> 
			<p class="description">City: ${ order.city }</p> 
			<p class="description">Postal Code: ${ order.postalCode }</p> 
			<p class="description">Phone: ${ order.phone }</p> 
		</div>
		</div>
     </div>
   </div>
   <table id="example2" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th></th>
				<th>Album</th>
				<th>Price (each)</th>
				<th>Quantity</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ orderDetail }" var="list" varStatus="status">
				<tr>
					<td>
					<span>${status.index + 1 }</span>
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
	
</div>
