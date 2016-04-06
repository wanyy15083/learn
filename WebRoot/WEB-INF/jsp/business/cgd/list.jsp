<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>采购单信息查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>


<script type="text/javascript">
	var cgdedit = function(id) {
		var sendUrl = "${baseurl}/cgd/edit.action?yycgdCustom.id=" + id;
		parent.opentabwindow(id+'采购单修改',sendUrl);//打开一个新标签
	};

	//工具栏
	var toolbar = [ ];


	var columns = [ [ 
{
	field : 'id',
	title : '采购单编号',
	width : 80
},{
	field : 'mc',
	title : '采购单名称',
	width : 150
},{
	field : 'cjtime',
	title : '建单时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			//通过js日期格式化
			var date = new Date(value);
			var y = date.getFullYear();//获取年
			var m = date.getMonth()+1;//获取月
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'xgtime',
	title : '修改时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			var date = new Date(value);
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'tjtime',
	title : '提交时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			var date = new Date(value);
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'shtime',
	title : '审核时间',
	width : 80,
	formatter: function(value,row,index){
		if(value){
			try{
			var date = new Date(value);
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+"-"+m+"-"+d;
			}catch(e){
				alert(e);
			}
		}
		
	}
},{
	field : 'cgdzt',
	title : '采购单状态', 
	width :100,
	formatter:function(value,row,index){
		return row.dictinfoByZt.info;
	}
},{
	field : 'opt3',
	title : '修改',
	width : 60,
	formatter:function(value, row, index){
		return '<a href=javascript:cgdedit("'+row.id+'")>修改</a>';
	}
}]];

	function initGrid() {
		$('#cgdlist').datagrid({
			title : '采购单列表',
			striped : true,
			url : '${baseurl}/cgd/listresult.action',
			idField : 'id',
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#cgdlist').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();
		
	});

	function cgdlist() {

		var formdata = $("#cgdlistForm").serializeJson();
		/* $('#cgdlist').datagrid('unselectAll'); */
		$('#cgdlist').datagrid('load', formdata);
	}

	
</script>
</HEAD>
<BODY>
	<div id="cgdlist_div">
		<form id="cgdlistForm" name="cgdlistForm" method="post" action="">
			<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">采购单编号：${yycgdCustom.yycgdbm }</td>
						<td><INPUT type="text" name="yycgdCustom.yycgdbm" /></TD>
						<TD class="left">采购单名称：</TD>
						<td><INPUT type="text" name="yycgdCustom.mc" /></TD>

						<TD class="left">采购单状态：</TD>
						<td>
							<select name="yycgdCustom.cgdzt">
								<option value="">全部</option>
								<c:forEach items="${cgdztList}" var="dictinfo">
								  <option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
								
							</select>
						</TD>
						<td >
						<a id="btn" href="#" onclick="cgdlist()"
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
						<table id="cgdlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</div>

</BODY>
</HTML>

