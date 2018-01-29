<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<div class="content-wrapper">
<h1>Account Profile</h1>
    <div class="thumbnail">
      <table  align="center">
         <ul>
           <li>User Name: ${pageContext.request.userPrincipal.name}</li>
           <li>Email: ${accountInfo.email}</li>
             <li>Role: ${accountInfo.role}</li>

       </ul>
     </table>
    </div>
  
</div>