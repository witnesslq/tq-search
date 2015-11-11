<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SOA TEST</title>
</head>
<body>
<% 
	String soa_basic_java = "{\"timestamp\":0,\"app_version\":5001,\"channel\":\"taqu\",\"appcode\":0,\"alias\":\"\",\"gender\":1,\"os\":\"\",\"os_version\":\"\",\"mac\":\"\",\"token\":\"326df6aad434061a2468213d7c8d6349\",\"access\":3,\"platform_name\":\"1\",\"platform_id\":4,\"project_name\":\"taqu\",\"partner\":\"\",\"height\":0,\"width\":0,\"longitude\":0,\"latitude\":0,\"ip\":\"192.168.187.1\"}";
	if("soa".equals(request.getParameter("action"))) {
		String url = "http://127.0.0.1:8080/tq-search/api?service="+request.getParameter("service").trim();
			url += "&method="+request.getParameter("method").trim();
			url += "&form="+Base64.encodeBase64String(("["+request.getParameter("form")+"]").trim().getBytes());
			url += "&soa_basic_java="+Base64.encodeBase64String(soa_basic_java.getBytes());
			url += "&distinctRequestId=325afasdfwefwet864sadfewf8464fewas4esa";
		response.sendRedirect(url);
	} else {
%>
<form action="test.jsp?action=soa" method="post">
	service:<input type="text" name="service"><br/><br/>
	method:<input type="text" name="method"><br/><br/>
	form:[<input type="text" name="form" width="50%">]<br/><br/>
	<input type="submit" value="request"/>
</form>
<% } %>
</body>
</html>