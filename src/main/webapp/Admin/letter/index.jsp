<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
    <s:form action="letter/add" method="post">
        <s:textfield label="收件人" name="receiver"/>
        <s:textfield label="寄信者" name="sender"/>
        <s:submit/>
    </s:form>
</body>
</html>