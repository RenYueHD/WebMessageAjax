<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("user") ==null){
		response.sendRedirect("login.html");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>聊天室</title>
<link rel="stylesheet" href="css/talk.css" type="text/css"></link>
<script type="text/javascript" src="js/talk.js" charset="utf-8"></script>
<%
	if(request.getParameter("type")==null || !request.getParameter("type").equals("long")){
		out.print("<script type=\"text/javascript\" src=\"js/short.js\"></script>");
	}else{
		out.print("<script type=\"text/javascript\" src=\"js/long.js\"></script>");
	}
%>
</head>
<body>
	<div id="container">
		<div id="left">
			<ul id="msg_container">
					
			</ul>
			<div id="control_container">
				当前用户:<span id="user">${user }</span>
				对:<span id="reader" class="reader">所有人</span>说:<input type="text" id="msg" onkeydown="return sendKey(event);"/><input type="button" value="提交" onclick="sendMessage()" />
				<input type="button" value="性能测试" onclick="test()" />
			</div>
		</div>
		<ul id="right"></ul>
	</div>
</body>
</html>