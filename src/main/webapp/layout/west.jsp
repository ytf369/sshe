<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		<div class="easyui-panel"
			data-options="title:'功能导航',fit:true,border:false">
			<div  class="easyui-accordion" data-options="fit:true,border:false">
				<div id="layout_west_tree" title="系统菜单" iconCls="icon-save">
					<ul class="easyui-tree"
						data-options="
						url:'${pageContext.request.contextPath}/menuAction!getAllTreeNode.action',
						parentField:'pid',
						lines:true,
						onClick:function(node){
						if(node.attributes.url){
						var url='${pageContext.request.contextPath}'+node.attributes.url;
						addTabs({title:node.text,href:url,closable:true});
						}
						}"></ul>
				</div>
				<div title="Title2" iconCls="icon-reload"></div>
			</div>
		</div>
