<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <script src="../js/jquery-2.0.3.min.js">
        </script>
        <script src="../js/bootstrap.min.js">
        </script>
        <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="../css/bootstrap-theme.min.css" rel="stylesheet" media="screen">
        <link href="../css/main.css" rel="stylesheet" media="screen">
        <title>
            信件列表
        </title>
        <script type="text/javascript">
            function validate() {
                var page = $("input[name='pageNum']").val();
                if (page < 0) {
                    window.document.location.href = "getletter.action?pageNum=1";
                } else if (page > <s:property value="#session.totalpage"/>) {
                    alert("你输入的页数大于最大页数，页面将跳转到首页！");
                    window.document.location.href = "getletter.action?pageNum=1";
                    return false;
                }
                return true;
            }

            function searchValidate(){
                var key = $("input[name='key']").val();
                if(key.length <= 0){
                    return false;
                } else {

                }
            }

            function search(){

            }
        </script>
    </head>

    <body>
        <div class="container">
            <ul class="nav nav-pills" role="tablist">
              <li role="presentation" class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                  信件 <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="../letter/add_letter.jsp">添加信件</a></li>
                    <li><a href="../letter/getletter.action?pageNum=1">所有信件</a></li>
                    <li class="divider"></li>
                    <li><a href="../letter/searchletter.action?pageNum=1&key=">查询信件</a></li>
                </ul>
              </li>
            </ul>
            <s:if test="#session.pageNum==0">
                <p>
                    参数错误！
                </p>
                <p>
                    <a href="index.jsp">
                        返回主页
                    </a>
                </p>
            </s:if>
            <s:else>
                <h1 style="float:left">
                    <font color="blue" size="10">
                        所有信件
                    </font>
                </h1>
                <div style="float:right" id="searchblock">
                    <form action="searchletter" class="form-inline">
                        <div class="input-group">
                            <div class="input-group-addon"><span class="glyphicon glyphicon-search"></span></div>
                            <input type="text" id="keyinput" class="form-control" name="key"  placeholder="搜索" value="<s:property value='#session.key'/>" onClick="this.select();"/>
                            <input type="hidden" name="pageNum" value="1" />
                        </div>
                        <button type="submit" class="btn">搜索</button>
                     </form>
                </div>
                <hr>
                <table border="0" align="center" width="50%" class="table table-hover">
                    <tr>
                        <div class="row">
                            <th class="col-sm-1">
                                信件编号
                            </th>
                            <th class="col-sm-2">
                                收件人
                            </th>
                            <th class="col-sm-2">
                                寄件人
                            </th>
                            <th class="col-sm-2">
                                寄件地址
                            </th>
                            <th class="col-sm-2">
                                添加时间
                            </th>
                            <th class="col-sm-1">
                                状态
                            </th>
                            <th class="col-sm-2">
                                操作
                            </th>
                        </div>
                    </tr>
                    <s:iterator value="#session.letterlist" id="letter" status="st">
                        <tr <s:if test="#st.odd==false">
                            bgcolor="#eeeeee"
                            </s:if>
                            >
                            <td>
                                <s:property value="%{#st.count + ( #session.pageNum - 1 ) * @org.tju.wx.action.LetterAction@ELENUM}"
                                />
                            </td>
                            <td>
                                <s:property value="#letter.receiver" />
                            </td>
                            <td>
                                <s:if test="#letter.sender!=''">
                                    <s:property value="#letter.sender" />
                                </s:if>
                                <s:else>
                                    无
                                </s:else>
                            </td>
                            <td>
                                <s:if test="#letter.senderaddr!=''">
                                    <s:property value="#letter.senderaddr" />
                                </s:if>
                                <s:else>
                                    无
                                </s:else>
                            </td>
                            <td>
                                <s:property value="#letter.time" />
                            </td>
                            <td>
                                <s:if test="#letter.status==1">
                                    在团委
                                </s:if>
                                <s:else>
                                    已被取走
                                </s:else>
                            </td>
                            <td>
                                <s:if test="#letter.status==1">
                                    <a class="btn btn-primary btn-lg active" href="sendletter.action?preAction=add&letter.lid=<s:property value='#letter.lid'/>">
                                        取走
                                    </a>
                                </s:if>
                                <s:else>
                                    <a class="btn btn-default btn-lg active" href="backletter.action?preAction=add&letter.lid=<s:property value='#letter.lid'/>">
                                        放回
                                    </a>
                                </s:else>
                                <a class="btn btn-default btn-lg active" href="deleteletter.action?preAction=add&letter.lid=<s:property value='#letter.lid'/>">
                                    删除
                                </a>
                            </td>
                        </tr>
                    </s:iterator>
                </table>
                <div class="row">
                    <div align="left" class="col-sm-6">
                        <div class="message" id="actionmessage">
                            <font color="#000000">
                                <s:actionmessage/>
                            </font>
                            <font color="#ff0000">
                                <s:actionerror/>
                            </font>
                        </div>
                    </div>
                    <div align="right" class="col-sm-6">
                        <s:if test="#session.pageNum == 1">
                            <a class="btn btn-primary btn-lg active" disabled="disabled">
                                首页
                            </a>
                            &nbsp;&nbsp;&nbsp;
                            <a class="btn btn-primary btn-lg active" disabled="disabled">
                                上一页
                            </a>
                        </s:if>
                        <s:else>
                            <a class="btn btn-primary btn-lg active" href="getletter.action?pageNum=1">
                                首页
                            </a>
                            &nbsp;&nbsp;&nbsp;
                            <a class="btn btn-primary btn-lg active" href="getletter.action?pageNum=<s:property value='#session.pageNum - 1'/>">
                                上一页
                            </a>
                        </s:else>
                        <s:if test="#session.pageNum != #session.totalpage">
                            <a class="btn btn-primary btn-lg active" href="getletter.action?pageNum=<s:property value='#session.pageNum + 1'/>">
                                下一页
                            </a>
                            &nbsp;&nbsp;&nbsp;
                            <a class="btn btn-primary btn-lg active" href="getletter.action?pageNum=<s:property value='#session.totalpage'/>">
                                尾页
                            </a>
                        </s:if>
                        <s:else>
                            <a class="btn btn-primary btn-lg active" disabled="disabled">
                                下一页
                            </a>
                            &nbsp;&nbsp;&nbsp;
                            <a class="btn btn-primary btn-lg active" disabled="disabled">
                                尾页
                            </a>
                        </s:else>
                        <br/>
                        <font size="3px">
                            共
                            <font color="red">
                                <s:property value="#session.totalpage" />
                            </font>
                            页
                        </font>
                        <font size="3px">
                            当前第
                            <font color="red">
                                <s:property value="#session.pageNum" />
                            </font>
                            页
                        </font>
                        <font size="3px">
                            共
                            <font color="red">
                                <s:property value="#session.count" />
                            </font>
                            条记录
                        </font>
                        <br/>
                        <form action="getletter" onSubmit="return validate();">
                            <font size="4">
                                跳转至
                                <input type="text" name="pageNum" size="2"  value="1"/>
                                页
                                <button class="btn btn-primary btn-lg active" type="submit">
                                    跳转
                                </button>
                            </font>
                        </form>
                    </div>
                    <br/>
                </div>
            </s:else>
        </div>
    </body>

</html>