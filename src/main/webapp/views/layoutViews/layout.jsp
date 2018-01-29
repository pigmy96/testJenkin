<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="UTF-8">
 
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
	crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel='stylesheet' href='${pageContext.request.contextPath}/stylesheets/style.css' />
	
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href='${pageContext.request.contextPath}/bootstrap/css/AdminLTE.min.css'>
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/bootstrap/css/_all-skins.min.css'>
 
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="menu" />
	<tiles:insertAttribute name="body" />
</div>
	<tiles:insertAttribute name="footer" />
	<script
	src="https://code.jquery.com/jquery-1.12.4.min.js"
	integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
	crossorigin="anonymous"></script>
	

<script src="${pageContext.request.contextPath}/plugins/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<!-- FastClick -->
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/bootstrap/js/app.min.js"></script>
<!-- Sparkline -->
<!-- jvectormap -->
<!-- SlimScroll 1.3.0 -->
<script src="${pageContext.request.contextPath}/plugins/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jquery.dataTables.min.js"></script><!-- phân trang -->
<script src="${pageContext.request.contextPath}/plugins/dataTables.bootstrap.min.js"></script><!-- định dạng -->
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script>
  $(function () {
    $("#example1").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });
  });
</script>
</body>
</html>