<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

		<script type="text/javascript">
		
	$(function (){
		$('#submitbtn').linkbutton({   
    		iconCls: 'icon-ok'  
		});  
		$('#closebtn').linkbutton({   
    		iconCls: 'icon-cancel'  
		});
		
	});
	function cgdadd(){
		jquerySubByFId('cgdform',cgdadd_callback,null,"json");
		
	}
	function cgdadd_callback(data){
		var resultInfo = data.resultInfo;
		_alert(resultInfo);
		/* if(resultInfo.type == TYPE_RESULT_SUCCESS){
			window.location = '${baseurl}/cgd/edit.action?yycgdCustom.id='+resultInfo.sysdata.yycgdid;
		} */
	}
	
	</script>
 </HEAD>
<BODY>
<form id="cgdform" name="cgdform" action="${baseurl}/cgd/addresult.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background="images/r_0.gif" width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;创建采购单</TD>
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
								<TD height=30 width="15%" align=right >采购单号：</TD>
								<TD class=category width="35%">
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="yycgd_mc" name="yycgdCustom.mc" value=""   />
								</div>
								<div id="yycgd_mcTip"></div>
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right >建单时间：</TD>
								<TD class=category width="35%"></TD>
								<TD height=30 width="15%" align=right >提交时间：</TD>
								<TD class=category width="35%"></TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >联系人：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" name="yycgdCustom.lxr" value=""   />
								</div>
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" name="yycgdCustom.lxdh" value=""   />
								</div>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >采购单状态：</TD>
								<TD class=category width="35%">
								</TD>
								<TD height=30 width="15%" align=right >备注：</TD>
								<TD class=category width="35%">
								<textarea name="yycgdCustom.bz"></textarea>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >审核时间：</TD>
								<TD class=category width="35%"></TD>
								<TD height=30 width="15%" align=right >审核意见：</TD>
								<TD class=category width="35%"></TD>
							</TR>
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="cgdadd()">保存</a>
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

