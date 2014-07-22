<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>添加信件</title>
    <script src="/Admin/js/jquery-2.0.3.min.js"></script>
	<script src="/Admin/js/bootstrap.min.js"></script>
</head>
<body>
    <s:form id="addform" action="addletter" method="post" onsubmit="return check();">
        <s:textfield id="receiver" label="* 收件人" name="letter.receiver"/>
        <s:textfield id="sender" label="寄信者" name="letter.sender"/>
        <s:textfield id="senderaddr" label="寄信地址" name="letter.senderaddr"/>
        <s:submit value="添加"/>
    </s:form>
    <!--<s:property value="#session.message"/>
    <s:set name="message" value="%{''}" scope="session"/>-->
    <s:actionmessage/>
    <s:actionerror/>
    <span id="js_messgae"></span>
</body>
<script type="text/javascript">
	function check(){
		console.log("receiver: " + $("#receiver").val());
		if($("#receiver").val() == ""){
			console.log("receiver空");
			$("#js_messgae").text("收件人不能为空");
			return false;
		}
		return true;
	}
</script>
</html>