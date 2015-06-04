<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	function addTabs(opts) {
		var t = $('#layout_center_centerTabs');
		if(t.tabs('exists',opts.title)){
			t.tabs('select',opts.title);
		}else{
			t.tabs('add', opts);
		}
	}
</script>
<div id="layout_center_centerTabs" class="easyui-tabs" data-options="border:false,fit:true">
	
	<div title="首页"></div>
	
	</div>