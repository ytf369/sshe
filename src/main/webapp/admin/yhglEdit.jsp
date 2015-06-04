<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <form id="admin_yhglEdit_Form" method="post">
		<table>
			<tr>
				<th>编号</th>
				<td><input name="id" readonly="readonly" /></td>
				<th>登录名称</th>
				<td><input name="name" class="easyui-validatebox"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password" />如果不修改请留空
				</td>
				<th>创建时间</th>
				<td><input name="createdatetime" readonly="readonly" /></td>
			</tr>
			<tr>
				<th style="width: 55px;">所属角色</th>
				<td><input id="admin_yhglEdit" name="roleIds" style="width: 150px;" /></td>
				<th>最后修改时间</th>
				<td><input name="modifydatetime" readonly="readonly" /></td>
				<th></th>
				<td></td>
			</tr>
		</table>
	</form>