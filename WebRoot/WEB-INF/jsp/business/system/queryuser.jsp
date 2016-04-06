<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>系统用户信息查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">
	var sysuserdel = function(id) {
		_confirm('您确定要执行删除操作吗?', null, function() {
			$("#sysuserdelid").val(id);
			jquerySubByFId('sysuserdelForm', sysuserdel_callback, null, "json");
		});
	};
	function sysuserdel_callback(data) {
		var result = getCallbackData(data);
		_alert(result);
		sysuserquery();
	}
	var sysuseredit = function(id) {
		//alert(id);
		var sendUrl = "${baseurl}/sys/user/edit.action?id=" + id;
		createmodalwindow("修改用户信息", 800, 250, sendUrl);
	
	};
	
	function sysuserview(id) {
		var sendUrl = "${baseurl}/sys/user/view.action?editid=" + id;
		createmodalwindow("查看用户信息", 800, 250, sendUrl);
	}
	var sysuseradd = function() {
		//alert(id);
		var sendUrl = "${baseurl}/sys/user/adduser.action";
		createmodalwindow("添加用户信息", 800, 250, sendUrl);
	};
	
	//工具栏
	var toolbar = [
	<shiro:hasPermission name="user:add">
	{
		id : 'sysuseradd',
		text : '添加',
		iconCls : 'icon-add',
		handler : sysuseradd
	} 
	</shiro:hasPermission>
	];
	
	var frozenColumns;
	
	var columns = [ [ /* {
		field : 'id',
		title : '',
		checkbox : true
	}, */ {
		field : 'usercode',
		title : '账号',
		width : 180
	}, {
		field : 'username',
		title : '名称',
		width : 130
	}, {
		field : 'groupid',
		title : '用户类型',
		width : 130,
		formatter : function(value, row, index) {
			return row.dictinfoByGroupid.info;
		}
	}, {
		field : 'sysmc',
		title : '所属单位',
		width : 130,
		formatter : function(value, row, index) {
			var dictcode =  row.dictinfoByGroupid.dictcode;
			if (dictcode == '1' || dictcode == '2') {
				return row.userjd.mc;
			} else if(dictcode == '3') {
				return row.useryy.mc;
			} else if(dictcode == '4') {
				return row.usergys.mc;
			}
		}
	}, {
		field : 'userstate',
		title : '用户状态',
		width : 130,
		formatter : function(value, row, index) {
			return row.dictinfoByUserstate.info;
		}
	}, {
		field : 'opt1',
		title : '修改',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:sysuseredit(\'' + row.id + '\')>修改</a>';
		}
	}, 

	{
		field : 'opt2',
		title : '删除',
		width : 60, 
		formatter : function(value, row, index) {
			return '<a href=javascript:sysuserdel(\'' + row.id + '\')>删除</a>';
		}
	} 

	] ];
	
	function initGrid() {
		$('#sysuserlist').datagrid({
			title : '系统用户列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/sys/user/queryuser_result.action',
			//sortName : 'code',
			//sortOrder : 'desc',
			//remoteSort : false,
			idField : 'id',
			//frozenColumns : frozenColumns,
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#sysuserlist').datagrid('unselectRow', index);
			}
		});
	
	}
	$(function() {
		initGrid();
		
	});
	
	function sysuserquery() {
	
		var formdata = $("#sysuserqueryForm").serializeJson();
		//alert(formdata);
		$('#sysuserlist').datagrid('unselectAll');
		$('#sysuserlist').datagrid('load', formdata);
	}

	
</script>

<title>query user</title>
</head>
<body>
	<!-- 删除时提交表单，ajaxForm提交 -->
	<form id="sysuserdelForm" name="sysuserdelForm" method="post" action="${baseurl}/sys/user/deleteuser.action">
		<input type="hidden" id="sysuserdelid" name="sysUserCustom.id" value="${ sysUserCustom.id }">
	</form>

	<form id="sysuserqueryForm" name="sysuserqueryForm" method="post" action="">
			<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">用户账号：${sysUserCustom.usercode }</td>
						<td><INPUT type="text" name="sysUserCustom.usercode" /></TD>
						<TD class="left">用户名称：</TD>
						<td><INPUT type="text" name="sysUserCustom.username" /></TD>

						<TD class="left">用户类型：</TD>
						<td>
							<select name="sysUserCustom.groupid">
								<option value="">请选择</option>
								<c:forEach items="${groupList}" var="dictinfo">
								  <option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
								
							</select>
						</TD>
						<td >
						<a id="btn" href="#" onclick="sysuserquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a>
							</td>
					</TR>


				</TBODY>
			</TABLE>
		</form>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="sysuserlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
</body>
</html>