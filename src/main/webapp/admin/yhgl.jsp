<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var dg = $('#admin_yhgl_datagrid');
	$(function() {
		dg.datagrid({
					title : '用户数据',
					border : false,
					fitColum : true,
					fit : true,
					nowarp : true,
					sortName : 'name',
					sortOrder : 'desc',
					pageSize : 10,
					checkOnSelect : false,
					selectOnCheck : false,
					pageList : [ 10, 20, 30, 40 ],
					iconCls : 'icon-save',
					url : '${pageContext.request.contextPath}/userAction!datagrid.action',
					idField : 'id',
					pagePosition : 'bottom',
					pagination : true,
					loadMsg : '正在加载数据当中....',
					frozenColumns : [ [ {
						field : 'id',
						title : '编号',
						width : 100,
						checkbox : true
					}, {
						field : 'name',
						title : '姓名',
						width : 100,
						sortable : true
					} ] ],
					columns : [ [ {
						field : 'pwd',
						title : '密码',
						width : 100,
						formatter : function(value, rowData, rowIndex) {
							return '******';
						}
					}, {
						field : 'createdatetime',
						title : '创建时间',
						width : 100
					}, {
						field : 'modifydatetime',
						title : '修改时间',
						width : 100
					},
					{
						title : '所属角色ID',
						field : 'roleIds',
						width : 150,
						hidden : true
					}, {
						title : '所属角色',
						field : 'roleNames',
						width : 150
					}] ],
					toolbar : [ {
						text : '添加',
						iconCls : 'icon-add',
						handler : function() {
							append();
						}
					}, '-', {
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							editFun();
						}
					}, '-', {
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							remove();
						}
					}, '-', {
						text : '取消选中',
						iconCls : 'icon-redo',
						handler : function() {
							dg.datagrid('rejectChanges');
							dg.datagrid('unselectAll');
						}
					} ]
				});
	});
	function _search() {
		var searchForm = $('#admin_yhgl_searchForm').form();
		dg.datagrid('load', serializeObject(searchForm));
	}
	function cleanSearch() {
		dg.datagrid('load', {});
		$('#admin_yhgl_searchForm input').val('');
	}
	function append() {
		$('#admin_yhgl_addForm').form('clear');
		$('#admin_yhgl_addDialog').dialog('open');
	}
	function remove() {
		var rows = dg.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager
					.confirm(
							'请确认',
							'您要删除当前所选项目？',
							function(r) {
								if (r) {
									for ( var i = 0; i < rows.length; i++) {
										ids.push(rows[i].id);
									}
									$.ajax({
												url : '${pageContext.request.contextPath}/userAction!delete.action',
												data : {
													ids : ids.join(',')
												},
												dataType : 'json',
												success : function(d) {
													dg.datagrid('load');
													dg.datagrid('unselectAll');
													$.messager.show({
														title : '提示',
														msg : d.msg
													});
												}
											});
								}
							});
		} else {
			$.messager.alert('提示', '请勾选要删除的数据！');
		}
	}
	function editFun() {
		var rows = dg.datagrid('getChecked');
		if (rows.length == 1) {
			var d=$('<div/>').dialog({
				title : '编辑用户',
				href : '${pageContext.request.contextPath}/admin/yhglEdit.jsp',
				width : 600,
				height : 200,
				modal : true,
				buttons : [ {
					text : '编辑',
					handler : function() {
						$('#admin_yhglEdit_Form').form('submit',{
							 url:'${pageContext.request.contextPath}/userAction!edit.action',
						            success:function(r){
						            	obj=$.parseJSON(r);
						                 if(obj.success){
						                 d.dialog('close');
						                 dg.datagrid('updateRow',{
						                	index:dg.datagrid('getRowIndex',rows[0].id),
						                	row:obj.obj
						                }); 
						            	 $.messager.show({
						            		 title:'提示',
						            		 msg:obj.msg
						            	 });
						                 }else{
						                	 $.messager.alert('提示', obj.msg);
						                 }
								    }
						});
					}
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
					$('#admin_yhglEdit').combobox({
					url:'${pageContext.request.contextPath}/roleAction!doNotNeedSession_combobox.action',
					  valueField:'id',  
	                  textField:'name',  
	                   multiple:true,  
	                  panelHeight:'auto'
					});
                $('#admin_yhglEdit_Form').form('load',{
                	id:rows[0].id,
                	name:rows[0].name,
                	roleIds:getList(rows[0].roleIds),
                	createdatetime:rows[0].createdatetime,
                	modifydatetime:rows[0].modifydatetime
                });
				}
			});
		} else {
			$.messager.alert('提示', '请勾选一条要编辑的数据');
		}
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false,title:'过滤条件'"
		style="height: 110px;overflow: hidden;" align="left">
		<div id="admin_yhgl_toolbar">
			<form id="admin_yhgl_searchForm">
				<table class="tableForm datagrid-toolbar"
					style="width: 100%;height: 100%;">
					<tr>
						<th>用户名</th>
						<td><input name="name" style="width:317px;" />
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td><input name="createdatetimeStart"
							class="easyui-datetimebox" data-options="editable:false"
							style="width: 155px;" />至<input name="createdatetimeEnd"
							class="easyui-datetimebox" data-options="editable:false"
							style="width: 155px;" />
						</td>
					</tr>
					<tr>
						<th>最后修改时间</th>
						<td><input name="modifydatetimeStart"
							class="easyui-datetimebox" data-options="editable:false"
							style="width: 155px;" />至<input name="modifydatetimeEnd"
							class="easyui-datetimebox" data-options="editable:false"
							style="width: 155px;" /> <a href="javascript:void(0);"
							class="easyui-linkbutton" onclick="_search();">过滤</a> <a
							href="javascript:void(0);" class="easyui-linkbutton"
							onclick="cleanSearch();">取消</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center',border:false"
		style="overflow: hidden;">
		<table id="admin_yhgl_datagrid"></table>
	</div>
</div>
<div id="admin_yhgl_addDialog" class="easyui-dialog"
	data-options="closed:true,modal:true,title:'添加用户',buttons:[{
						text : '增加',
						iconCls : 'icon-add',
						handler : function() {
						$('#admin_yhgl_addForm').form('submit',{
						    url : '${pageContext.request.contextPath}/userAction!add.action',
						     success:function(r){
						       obj=$.parseJSON(r);
						       if(obj.success){
						         dg.datagrid('insertRow',{
						      index:0,
						      row:obj.obj
						      });
						      $('#admin_yhgl_addDialog').dialog('close');
						      }
						        $.messager.show({
						       title:'提示',
						       msg:obj.msg
						    });
						    }
						});
					   }
					}]"
	style="width:500px;height:200px;" align="center">
	<form id="admin_yhgl_addForm" method="post">
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
				<td><input name="pwd" type="password"
					class="easyui-validatebox" data-options="required:true" /></td>
				<th>创建时间</th>
				<td><input  name="createdatetime" readonly="readonly" /></td>
			</tr>
			<tr>
			<th style="width: 55px;">所属角色</th>
				<td><input id="yhgl_combobox" class="easyui-combobox" name="roleIds" data-options="
				url:'${pageContext.request.contextPath}/roleAction!doNotNeedSession_combobox.action',
				  valueField:'id',  
                  textField:'name',  
                   multiple:true,  
                  panelHeight:'auto'  
				" style="width: 145px;"/>
			</td>
				<th>最后修改时间</th>
				<td><input name="modifydatetime" readonly="readonly" /></td>
				<th></th>
				<td></td>
			</tr>
		</table>
	</form>
</div>
