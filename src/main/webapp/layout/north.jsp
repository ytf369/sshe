<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	function logout(b) {
		$('#sessionInfoDiv').html('');
		$.post('${pageContext.request.contextPath}/userAction!doNotNeedSession_logout.action', function() {
			if (b) {
				if (sy.isLessThanIe8()) {
					$('#user_login_loginDialog').dialog('open');
				} else {
					location.replace('${pageContext.request.contextPath}/');
				}
			} else {
				$('#user_login_loginDialog').dialog('open');
			}
		});
	}
	function showUserInfo() {
		var p = parent.sy.dialog({
			title : '用户信息',
			href : '${pageContext.request.contextPath}/userAction!doNotNeedSession_userInfo.action',
			width : 360,
			height : 285,
			buttons : [ {
				text : '修改密码',
				handler : function() {
					$('#layout_north_form').form('submit', {
						url : '${pageContext.request.contextPath}/userAction!doNotNeedSession_editUserInfo.action',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								p.dialog('close');
							}
							$.messager.show({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			} ]
		});
	}
</script>
<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
	<c:if test="${sessionInfo.userId != null}">[<strong>${sessionInfo.loginName}</strong>]，欢迎你！您使用[<strong>${sessionInfo.ip}</strong>]IP登录！</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px; ">
	<a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
	<a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeTheme('default');">default</div>
	<div onclick="changeTheme('gray');">gray</div>
	<div onclick="changeTheme('metro');">metro</div>
	<div onclick="changeTheme('cupertino');">cupertino</div>
	<div onclick="changeTheme('dark-hive');">dark-hive</div>
	<div onclick="changeTheme('pepper-grinder');">pepper-grinder</div>
	<div onclick="changeTheme('sunny');">sunny</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="showUserInfo();">个人信息</div>
	<div class="menu-sep"></div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div onclick="logout();">注销</div>
</div>
