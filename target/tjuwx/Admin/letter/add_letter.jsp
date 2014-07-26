<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>添加信件</title>
    <script src="/Admin/js/jquery-2.0.3.min.js"></script>
    <script src="/Admin/js/bootstrap.min.js"></script>
    <link href="/Admin/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/Admin/css/bootstrap-theme.min.css" rel="stylesheet" media="screen">

    <link href="/Admin/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <nav class="navbar navbar-inverse  navbar-static-top" role="navigation">
        <div class="container">
            <ul class="nav navbar-nav">
               <li role="presentation" class="dropdown">
                 <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                   信件 <span class="caret"></span>
                 </a>
                 <ul class="dropdown-menu" role="menu">
                     <li><a href="/Admin/letter/add_letter.jsp">添加信件</a></li>
                     <li><a href="/Admin/letter/getletter.action?pageNum=1">所有信件</a></li>
                     <li class="divider"></li>
                     <li><a href="/Admin/letter/searchletter.action?pageNum=1&key=">查询信件</a></li>
                 </ul>
               </li>
             </ul>
        </div>
    </nav>

    <div class="container">
        <h1>
            添加信件
        </h1>
        <div class="block">
            <!--<form class="form-g" id="addform" action="addletter" method="post" onsubmit="return check();">
                <input type="text" class="form-control" id="receiver" label="收件人" name="letter.receiver" maxlength="20"/>
                <input type="text"class="form-control" id="sender" label="寄信者" name="letter.sender"  maxlength="20"/>
                <input type="text" class="form-control" id="senderaddr" label="寄信地址" name="letter.senderaddr" maxlength="50"/>
                <button type="submit" class="btn btn-primary btn-lg active"/>添加</button>
            <form>-->
            <form id="addform" action="addletter.action?preAction=add" method="post" onsubmit="return check();" role="form">
              <div class="form-group">
                <label>收件人</label>
                <div class="input-group">
                  <div class="input-group-addon">*</div>
                  <input type="text" class="form-control" id="receiver" name="letter.receiver"/>
                </div>
              </div>
              <div class="form-group">
                <label>寄信者</label>
                <input type="text"class="form-control" id="sender" name="letter.sender"/>
              </div>
              <div class="form-group">
                <label>寄信地址</label>
                <input type="text" class="form-control" id="senderaddr" name="letter.senderaddr"/>
              </div>
                <input type="submit" class="btn btn-primary btn-lg active form-control" value="添加"/>
            </form>
            <!--<s:property value="#session.message"/>
            <s:set name="message" value="%{''}" scope="session"/>-->
            <div class="message" id="actionmessage">
                <font color="#000000"><s:actionmessage/></font>
                <font color="#ff0000"><s:actionerror/></font>
            </div>
            <div class="message">
                <span id="js_message"></span>
            </div>
       </div>
    </div>
</body>
<script type="text/javascript">
    $("#actionmessage").show();
    $("#receiver").focus();
	function check(){
		console.log("receiver: " + $("#receiver").val());
		if($("#receiver").val() == ""){
			console.log("receiver空");
			$("#actionmessage").hide();
			$("#js_message").text("收件人不能为空");
			$("#receiver").focus();
			return false;
		}
		return true;
	}
</script>
</html>