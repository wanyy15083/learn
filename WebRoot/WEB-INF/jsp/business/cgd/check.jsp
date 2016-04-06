<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>采购单审核</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">
$(function (){
	//***********按钮**************
	$('#submitbtn').linkbutton({   
		iconCls: 'icon-ok'  
	});  
	$('#closebtn').linkbutton({   
		iconCls: 'icon-cancel'  
	});
});
//采购单审核提交
function checksubmit(){
	jquerySubByFId('yycgdcheckForm', yycgdcheck_callback, null);
}
/**
 * 采购单审核提交
 */
function yycgdcheck_callback(data) {
	_alert(data.resultInfo);
	
}

</script>
</HEAD>
<BODY>

<form id="yycgdcheckForm" name="yycgdcheckForm" action="${baseurl}/cgd/checksubmit.action" method="post">
<input type="hidden" name="yycgdCustom.id" value="${yycgdCustom.id}"/>
<input type="hidden" name="taskId" value="${taskId }"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed align=center>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;药品采购单</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							
							<TR>
							    
								<TD height=30 width="15%" align=right>采购单号：</TD>
								<TD class=category width="35%">
								${yycgd.id}
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								${yycgd.mc}
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >建单时间：</TD>
								<TD class=category width="35%">
									<fmt:formatDate value="${yycgd.cjtime}" pattern="yyyy-MM-dd"/>
								</TD>
								<TD height=30 width="15%" align=right >提交时间：</TD>
								<TD class=category width="35%">
								<fmt:formatDate value="${yycgd.tjtime}" pattern="yyyy-MM-dd"/>
								</TD>
								
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>联系人：</TD>
								<TD class=category width="35%">
								${yycgd.lxr}
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								${yycgd.lxdh}
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>采购单状态：</TD>
								<TD class=category width="35%">
								${yycgd.dictinfoByZt.info}
								</TD>
								<TD height=30 width="15%" align=right>备注：</TD>
								<TD class=category width="35%">
									${yycgd.bz}
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right>审核结果：</TD>
								<TD class=category width="35%">
								     <input type="radio" name="yycgdCustom.checkResult" value="3"/>审核通过
								     <input type="radio" name="yycgdCustom.checkResult" value="4"/>审核不通过
								</TD>
								<TD height=30 width="15%" align=right >审核意见：</TD>
								<TD class=category width="35%">
								   <textarea rows="2" cols="30" name="yycgdCustom.shyj"></textarea>
								</TD>
							</TR>
							
						
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="checksubmit()" class="easyui-linkbutton" iconCls='icon-save'>提交</a>
								<a id="closebtn" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

</BODY>
</HTML>

