<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form method="post" id="admin_roleEdit_form">
		<input name="id" type="hidden" />
		<table class="tableForm">
			<tr>
				<th style="width: 55px;">角色名称</th>
				<td><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单名称'" style="width:323px;" />
				</td>
			</tr>
			<tr>
				<th>拥有权限</th>
				<td><input name="authIds" style="width:327px;" />
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td><textarea name="cdesc" style="height: 100px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>