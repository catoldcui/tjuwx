<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
  <head>
    <script src="/Admin/js/jquery-2.0.3.min.js"></script>
    <script src="/Admin/js/bootstrap.min.js"></script>
    <title>信件列表</title>
    <script type="text/javascript">
    	function validate()
        {
            var page = $("input[name='pageNum']").val();
			if(page < 0){
				window.document.location.href = "getletter.action?pageNum=1";
			} else if(page > <s:property value="#session.totalpage"/>)
            {
                alert("你输入的页数大于最大页数，页面将跳转到首页！");
                window.document.location.href = "getletter.action?pageNum=1";
                return false;
            }
            
            return true;
        }
    </script>
  </head>
  
      <body>
    <s:if test="#session.pageNum==0">
    	<p>参数错误！</p>
        <p><a href="index.php">返回主页</a></p>
    </s:if>
    <s:else>
    <h1><font color="blue">分页查询</font></h1><hr>
    <table border="1" align="center" bordercolor="yellow" width="50%">
        <tr>
            <th>信件编号</th>
            <th>收件人</th>
            <th>寄件人</th>
            <th>寄件地址</th>
            <th>添加时间</th>
        </tr>
        
    <s:iterator value="#session.letterlist" id="letter" status="st">
        <tr>
        	<td><s:property value="#st.count"/></td>
            <td><s:property value="#letter.receiver"/></td>
            <td>
            	<s:if test="#letter.sender==''">
            		<s:property value="#letter.sender"/>
                </s:if>
                <s:else>
                	无
                </s:else>
            </td>
            <td>
            	<s:if test="#letter.senderaddr==''">
            		<s:property value="#letter.senderaddr"/>
                </s:if>
                <s:else>
                	无
                </s:else>
            </td>
            <td><s:property value="#letter.time"/></td>
        </tr>
    </s:iterator>
    
    </table>
    
    <center>
    
        <font size="5">共<font color="red"><s:property value="#session.totalpage"/></font>页 </font>&nbsp;&nbsp;
        <font size="5">共<font color="red"><s:property value="#session.count"/></font>条记录</font><br><br>
        <font size="5">当前第<font color="red"><s:property value="#session.pageNum"/></font>页</font><br><br>
        
        <s:if test="#session.pageNum == 1">
            首页&nbsp;&nbsp;&nbsp;上一页
        </s:if>
        
        <s:else>
            <a href="getletter.action?pageNum=1">首页</a>
            &nbsp;&nbsp;&nbsp;
            <a href="getletter.action?pageNum=<s:property value='#session.pageNum - 1'/>">上一页</a>
        </s:else>
        
        <s:if test="#session.pageNum != #session.totalpage">
            <a href="getletter.action?pageNum=<s:property value='#session.pageNum + 1'/>">下一页</a>
            &nbsp;&nbsp;&nbsp;
            <a href="getletter.action?pageNum=<s:property value='#session.totalpage'/>">尾页</a>
        </s:if>
        
        <s:else>
            下一页&nbsp;&nbsp;&nbsp;尾页
        </s:else>
    
    </center><br>
    
    <center>
        
        <form action="getletter" onSubmit="return validate();">
            <font size="4">跳转至</font>
            <input type="text" size="2" name="pageNum">页
            <input type="submit" value="跳转">
        </form>
        
    </center>
  </s:else>
    
  </body>
</html>