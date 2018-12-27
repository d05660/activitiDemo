<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>  
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%--项目路径 --%>
<c:set var="path" value="${ctxPath}" />
<%--静态文件目录 --%>
<c:set var="staticPath" value="${ctxPath}" />
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
  </head>
  <body>
     authenticated标签: <br/>
     <shiro:authenticated>  
    	用户[<shiro:principal/>]已身份验证通过  
	  </shiro:authenticated><br />
	  hasRole标签: <br/>
	 <shiro:hasRole name="GM">  
    	用户[<shiro:principal/>]拥有角色Employee 
	 </shiro:hasRole><br />
	 hasPermission标签: <br/>
	<shiro:hasPermission name="L1">  
    用户[<shiro:principal/>]拥有权限user:create
    </shiro:hasPermission>   <br/>  
     hello ${name}
     <br/>
     <a href="javascript:void(0)" class="btn btn-default btn-flat" onclick="logout()">Sign out</a>
  </body>
<script src="<%=basePath%>assets/vender/jquery/jquery.min.js"></script>
<script src="<%=basePath%>assets/vender/jquery/jquery.serialize-object.min.js"></script>
<script src="<%=basePath%>assets/vender/parsley/parsley.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=basePath%>assets/vender/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>assets/vender/bootstrap-fileinput/js/fileinput.min.js"></script>
<!-- nprogress -->
<script src="<%=basePath%>assets/vender/nprogress/js/nprogress.min.js"></script>
<script src="<%=basePath%>assets/vender/toastr/js/toastr.min.js"></script>
<script src="<%=basePath%>assets/vender/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="<%=basePath%>assets/vender/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="<%=basePath%>assets/vender/bootbox/bootbox.min.js"></script>
<script src="<%=basePath%>assets/vender/spin/spin.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=basePath%>assets/vender/AdminLTE/js/AdminLTE.min.js"></script>
  <script src="<%=basePath%>assets/js/default.js"></script>
</html>