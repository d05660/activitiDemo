<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>后台管理</title>
<%@ include file="common/basecss.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <%@ include file="common/header.jsp"%>
  <!-- Left side column. contains the logo and sidebar -->
  <%@ include file="common/sidebar.jsp"%>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1> Dashboard <small>Control panel</small> </h1>
      <ol class="breadcrumb">
        <li> <a href="#"> <i class="fa fa-dashboard"></i> Home </a> </li>
        <li class="active">部门管理</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box box-info">
            <div class="box-header">
              <h3 class="box-title"> 部门管理 </h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body" id="emp-container">
              建设中。。。。
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <%@ include file="common/footer.jsp"%>
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<%@ include file="common/basejs.jsp"%>
</body>
</html>