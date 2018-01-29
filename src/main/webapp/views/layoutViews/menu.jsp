<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

 <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-filter"></i> <span>Genres</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
          	<c:forEach items="${ genreList }" var="genre">
          		<li><a href="${pageContext.request.contextPath}/album/genre?id=${genre.genreID}"><i class="fa fa-circle-o"></i>${genre.name}</a></li>
        	</c:forEach>
          </ul>
        </li>
        
         <c:if test="${pageContext.request.userPrincipal.name != null}">
  
		     <security:authorize  access="hasRole('ROLE_ADMIN')">
		     	<li class="treeview">
		   			<a href="#">
		            <i class="fa fa-shopping-basket"></i> <span>Manage</span>
		            <span class="pull-right-container">
		              <i class="fa fa-angle-left pull-right"></i>
		            </span>
		          </a>
		          <ul class="treeview-menu">
		          	<li><a href="${pageContext.request.contextPath}/album/manage"><i class="fa fa-circle-o"></i>Albums</a></li>
		          	<li><a href="${pageContext.request.contextPath}/genre/add"><i class="fa fa-circle-o"></i>Add new genre</a></li>
		          	<li><a href="${pageContext.request.contextPath}/artist/add"><i class="fa fa-circle-o"></i>Add new artist</a></li>
		          </ul>
		        </li>
		     
		     </security:authorize>
		     <li class="treeview">
		          <a href="${pageContext.request.contextPath}/order/orderHistory">
		            <i class="fa fa-history"></i> <span>Order History</span>
		            <span class="pull-right-container">
		              <i class="fa fa-angle-left pull-right"></i>
		            </span>
		          </a>
		   
		        </li>
      </c:if>

      </ul>
    </section>
    <!-- /.sidebar -->
 </aside>