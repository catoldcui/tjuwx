<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>添加信件</title>
    <script src="../js/jquery-2.0.3.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../css/bootstrap-theme.min.css" rel="stylesheet" media="screen">

    <link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="container">
        <div class="block">
            <!--<form class="form-g" id="addform" action="addletter" method="post" onsubmit="return check();">
                <input type="text" class="form-control" id="receiver" label="* 收件人" name="letter.receiver"/>
                <input type="text"class="form-control" id="sender" label="寄信者" name="letter.sender"/>
                <input type="text" class="form-control" id="senderaddr" label="寄信地址" name="letter.senderaddr"/>
                <button type="submit" class="btn btn-primary btn-lg active"/>添加</button>
            <form>-->
            <form id="addform" action="addletter" method="post" onSubmit="return check();" role="form">
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
                <input type="button" class="btn btn-primary btn-lg active form-control" onClick="return check();" value="添加"/>
            </form>
            <!--<s:property value="#session.message"/>
            <s:set name="message" value="%{''}" scope="session"/>-->
            <div class="message" id="actionmessage">
                <font color="#ffffff"><s:actionmessage/></font>
                <font color="#ff0000"><s:actionerror/></font>
            </div>
            <div class="message">
                <span id="js_message"></span>
            </div>
       </div>
    </div>
</body>
<script type="text/javascript">
	function check(){
		console.log("receiver: " + $("#receiver").val());
		if($("#receiver").val() == ""){
			console.log("receiver空");
			$("#actionmessage").hide();
			$("#js_message").text("收件人不能为空");
			return false;
		}
		$("#addform").submit();
		return true;
	}
</script>
</html>