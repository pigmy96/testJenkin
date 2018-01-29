<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header class="main-header">
    <!-- Logo -->
    <a href="${pageContext.request.contextPath}/" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>M</b>S</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Music</b>Store</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
        <li class="dropdown user user-menu">
        <a href="${pageContext.request.contextPath}/cart/cartList" class="dropdown-toggle">
		<span class="hidden-xs">Cart (${ totalQuantity })</span>
		</a>
		</li>
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              
			 <c:if test="${pageContext.request.userPrincipal.name != null}">
			 
              <span class="hidden-xs">${pageContext.request.userPrincipal.name}</span>
              </c:if>
			  <c:if test="${pageContext.request.userPrincipal.name == null}">
			   <span class="hidden-xs">Account Managenent</span>
			  </c:if>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
				<c:if test="${pageContext.request.userPrincipal.name != null}">
				
			  <p>
                  ${pageContext.request.userPrincipal.name}
                  <small>Member of Music Store</small>
                </p>
			  </c:if>
			  <c:if test="${pageContext.request.userPrincipal.name == null}">
				
			  <p>
                 Guest
                </p>
			  </c:if>
                
              </li>
            
			 <c:if test="${pageContext.request.userPrincipal.name != null}">
			  <li class="user-footer">
                <div class="pull-left">
                  <a href="${pageContext.request.contextPath}/accountInfo" class="btn btn-default btn-flat">Profile</a>
                </div>
                <div class="pull-right">
                  <a href="${pageContext.request.contextPath}/logout" class="btn btn-default btn-flat">Log Out</a>
                </div>
              </li>
			  </c:if>
			  <c:if test="${pageContext.request.userPrincipal.name == null}">
			  <li class="user-footer">
			  <div class="pull-left">
                  <a href="${pageContext.request.contextPath}/register" class="btn btn-default btn-flat">Register</a>
                </div>
                <div class="pull-right">
                  <a href="${pageContext.request.contextPath}/login" class="btn btn-default btn-flat">Log In</a>
                </div>
              </li>
			  </c:if>
              
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  