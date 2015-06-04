<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <script type="text/javascript">
    var loginDialog;
	$(function() {
		$('#user_login_loginForm').form({
			url : '${pageContext.request.contextPath}/userAction!login.action',
			success : function(r) {
				var obj = $.parseJSON(r);
				if (obj.success) {
					$('#user_login_loginDialog').dialog('close');
					$('#sessionInfoDiv').html(sy.fs('[<strong>{0}</strong>]，欢迎你！您使用[<strong>{1}</strong>]IP登录！', obj.obj.loginName, obj.obj.ip));
					$.messager.show({
						title : '提示',
						msg : obj.msg
					});
				} else {
					$.messager.alert('提示', obj.msg);
				}
			}
		});
		$('#user_login_loginForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#user_login_loginForm').submit();
			}
		});
		window.setTimeout(function(){
			$('#user_login_loginForm input[name=name]').focus();
		},0);
	});
</script>
<div id="user_login_loginDialog" class="easyui-dialog" style="width:240px"
		data-options="title:'登录',modal:true,closable:false,buttons:[{
        text:'注册',
        iconCls:'icon-edit',
        handler:function(){
        $('#index_regDialog').dialog('open');
        }
        },{
           text:'登录',
           iconCls:'icon-help',
        handler:function(){
        $('#user_login_loginForm').submit();
        }
        }]">
        <form id="user_login_loginForm" method="post">
		<table>
			<tr>
				<th>登录名</th>
				<td><input name="name" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input type="password" name="pwd" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
