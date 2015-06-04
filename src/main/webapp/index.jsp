<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>SSHE示例项目</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript"
	src="jslib/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="jslib/jquery-easyui-1.3.1/jquery.cookie.js"></script>
<script type="text/javascript"
	src="jslib/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="jslib/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<%
	String easyuiThemeName = "default";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
	if (cookie.getName().equals("easyuiThemeName")) {
		easyuiThemeName = cookie.getValue();
		break;
	}
		}
	}
%>
<link rel="stylesheet"
	href="jslib/jquery-easyui-1.3.1/themes/<%=easyuiThemeName%>/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/icon.css"
	type="text/css"></link>
<script type="text/javascript" src="jslib/syUtil.js"></script>
<script type="text/javascript">
	function changeTheme(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
		$.cookie('easyuiThemeName', themeName, {
			expires : 7
		});
	}
	$(function() {
		if ('${sessionInfo.userId}'!="") {
			$('#user_login_loginDialog').dialog('close');
		}
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:60px;">
		<jsp:include page="layout/north.jsp"></jsp:include>
	</div>
	<div data-options="region:'south'" style="height:20px;"></div>
	<div data-options="region:'west'" style="width:200px;">
		<jsp:include page="layout/west.jsp"></jsp:include>
	</div>
	<div
		data-options="region:'east',split:'true',fit:'true',title:'center east'"
		style="width:200px;">
		<jsp:include page="/layout/east.jsp"></jsp:include>
		</div>
	<div data-options="region:'center',title:'欢迎使用SSHE示例系统'">
		<jsp:include page="layout/center.jsp"></jsp:include>
	</div>
	<jsp:include page="user/login.jsp"></jsp:include>
	<jsp:include page="user/reg.jsp"></jsp:include>
</body>
</html>
