<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="content-wrapper">
	<table id="example2" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th></th>
				<th>Action</th>
				<th>Order Date</th>
				<th>Total</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ orderList }" var="list" varStatus="status">
				<tr>
					<td>
					<span>${status.index +1 }</span>
					</td>
					<td>
			          	<a href="${pageContext.request.contextPath}/order/orderDetail?id=${ list.orderID }">
			            	<i class="fa fa-info-circle"></i>
			          	</a>
					</td>
					<td>
					<span>${list.orderDate }</span>
					</td>
					<td>
					<span>${list.total }</span>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>