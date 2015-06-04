<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function() {
		$('#index_regForm').form({
			url : '${pageContext.request.contextPath}/userAction!reg.action',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(r) {
				var obj = $.parseJSON(r);
				if (obj.success) {
					$('#index_regDialog').dialog('close');
					$.messager.show({
						title : '提示',
						msg : obj.msg
					});
				} else {
					$.messager.alert('提示', obj.msg);
				}
			}
		});
		$('#index_regForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#index_regForm').submit();
			}
		});
	});
</script>
<div id="index_regDialog" style="width:250px" class="easyui-dialog"
	data-options="title:'注册',modal:true,closed:true,buttons:[{
        text:'注册',
        iconCls:'icon-edit',
        handler:function(){
        	$('#index_regForm').submit();
        }
        }]">
	<form id="index_regForm" method="post">
		<table>
			<tr>
				<th>登录名</th>
				<td><input name="name" class="easyui-validatebox"
					data-options="required:true,missingMessage:'登录名称必填'" /></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password"
					class="easyui-validatebox"
					data-options="required:true,missingMessage:'密码名称必填'" /></td>
			</tr>
			<tr>
				<th>重复密码</th>
				<td><input name="rePwd" type="password"
					class="easyui-validatebox"
					data-options="required:true,validType:'eqPwd[\'#index_regForm input[name=pwd]\']'" />
				</td>
			</tr>
		</table>
	</form>
</div>