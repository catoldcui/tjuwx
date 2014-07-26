<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<script src="/Admin/js/jquery-2.0.3.min.js"></script>
    <script src="/Admin/js/bootstrap.min.js"></script>
    <link href="/Admin/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/Admin/css/bootstrap-theme.min.css" rel="stylesheet" media="screen">
    
    <link href="/Admin/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <a href="getletter.action?pageNum=1" class="button">所有信件</a>
    <a href="add_letter.jsp" class="button">添加信件</a>
    <form action="searchletter" class="form-inline">
        <div class="input-group">
            <div class="input-group-addon"><span class="glyphicon glyphicon-search"></span></div>
            <input type="text" class="form-control" name="key"  placeholder="搜索"/>
            <input type="hidden" name="pageNum" value="1" />
        </div>
        <button type="submit" class="btn">搜索</button>
     </form>
</body>
</html>