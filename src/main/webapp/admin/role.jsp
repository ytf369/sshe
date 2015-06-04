<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	var datagrid = $('#admin_role_datagrid');
	$(function() {
		datagrid.datagrid({
			url : '${pageContext.request.contextPath}/roleAction!datagrid.action',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortName : 'name',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : true,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				sortable : true,
				checkbox : true
			}, {
				title : '角色名称',
				field : 'name',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				title : '备注',
				field : 'cdesc',
				width : 150
			}, {
				title : '拥有权限ID',
				field : 'authIds',
				width : 300,
				hidden : true
			}, {
				title : '拥有权限',
				field : 'authNames',
				width : 300
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			} ]
		/*onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}*/
		});
	});
	function append() {
		var ad = $('<div/>').dialog({
			title : '添加用户角色',
			href : '${pageContext.request.contextPath}/roleAction!roleAdd.action',
			width : 420,
			height : 260,
			modal : true,
			buttons : [ {
				text : '添加',
				handler : function() {
					$('#admin_roleAdd_form').form('submit', {
						url : '${pageContext.request.contextPath}/roleAction!add.action',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								datagrid.datagrid('load');
								ad.dialog('close');
							}
							$.messager.show({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			} ],
			onLoad : function() {
				$('#admin_roleAdd_form input[name=authIds]').combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/authAction!doNotNeedSession_treeRecursive.action',
					checkbox : true,
					multiple : true
				});
			}
		});
	}
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var ae = $('<div/>').dialog({
				title : '编辑角色',
				href : '${pageContext.request.contextPath}/roleAction!roleEdit.action',
				width : 420,
				height : 260,
				buttons : [ {
					text : '编辑',
					handler : function() {
						$('#admin_roleEdit_form').form('submit', {
							url : '${pageContext.request.contextPath}/roleAction!edit.action',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									ae.dialog('close');
								}
								$.messager.show({
									title : '提示',
									msg : json.msg
								});
							}
						});
					}
				} ],
				onLoad : function() {
					$('#admin_roleEdit_form input[name=authIds]').combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/authAction!doNotNeedSession_treeRecursive.action',
						checkbox : true,
						multiple : true
					});
					$('#admin_roleEdit_form').form('load', {
						id : rows[0].id,
						name : rows[0].name,
						cdesc : rows[0].cdesc,
						authIds : getList(rows[0].authIds)
					});
				}
			});
		} else if (rows.length > 1) {
			$.messager.alert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			$.messager.alert('提示', '请勾选要删除的记录', 'error');
		}
	}
	function remove() {
		var rows = datagrid.datagrid("getChecked");
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('请确认', '你确定要删除当前所选的数据吗？', function(b) {
				if (b) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/roleAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							if (d.success) {
								datagrid.datagrid('load');
								datagrid.datagrid('unselectAll');
								$.messager.show({
									title : '提示',
									msg : d.msg
								});
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请勾选数据进行删除!');
		}
	}
</script>
<table id="admin_role_datagrid"></table>