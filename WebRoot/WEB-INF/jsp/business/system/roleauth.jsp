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
		
	
	function role_authorize_submit(){
			
			jquerySubByFId('role_authorize_form',role_authorize_submit_callback,null,"json");

	}
	function role_authorize_submit_callback(data){
		//alert(data);
		var result = getCallbackData(data);
		//var type = result.type;
		_alert(result);
		/* if (TYPE_RESULT_SUCCESS == type) {
			parent.sysPermissionquery();
			parent.closemodalwindow();
		} 	 */
	}
	
	
	var setting = {
			view : {
				selectedMulti : false,
				showLine: true
			},
			check : {
				enable : true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "",
					"N" : ""
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",//结点id
					pIdKey : "parentid",//结点父id
					rootPId : 0
				},
				key: {
					url: "xUrl"
				}
			},
			callback : {
				onClick : null
			}
		};
		//树对象 
		var tree;
		//结点
		var zNodes;
		function showSysPermissionTree() {
			tree.showMenu();
		}

		$(function() {
			
			$('#submitbtn').linkbutton({   
	    		iconCls: 'icon-ok'  
			});  
			$('#closebtn').linkbutton({   
	    		iconCls: 'icon-cancel'  
			});
			//通过ajax获取树的结点
			//var sendUrl = "${baseurl}/sys/permission/listByjson.action";
			//var ajaxOption = new AjaxOption();
			//	ajaxOption._initPostRequest(false,sendUrl,"pame","json");
			//	_ajaxPostRequest(ajaxOption, '', permissionload_callback);	
			//通过ajax取出所有的结点
			jquerySubByFId('listByjson',permissionload_callback,null,"json",false);
			//new createSyncTree使用zNodes，等到jquerySubByFId执行完成，这里jquerySubByFId使用同步
			tree = new createSyncTree("permissionTreeContent", "permissionTree",
					"sysPermissionName", "sysPermissionIds", setting, zNodes, null,
					"onCheck", '${sysPermissionsByRoleId}');
			//展开所有结点
			tree.expandAll();
			//bug修复：只要选中父节点就选中全部子节点,默认选中时可以关联
			var zTree = $.fn.zTree.getZTreeObj("permissionTree");
			zTree.setting.check.chkboxType = { "Y":"ps" , "N":"ps"};
			
		});
		function permissionload_callback(data){
			try{
			//zNodes = redata.sysPermissionList;
				zNodes = data;
				//是json格式的数据组成的数组
			}catch(e){
				//alert(e);
			}
			return ;
		}

	</script>
 </HEAD>
<BODY>
<form id="listByjson" action="${baseurl}/sys/auth/permissionall.action" method="post"></form>
<form id="role_authorize_form" name="role_authorize_form" action="${baseurl}/sys/auth/roleauthsubmit.action" method="post">
<input type="text" name="roleId" value="${roleId }"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;分配权限信息</TD>
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
								<TD width="100%" align=right rolspan=2>
								<!-- 选中树的结点id，以逗号分隔
								以框中默认填充了角色下的权限，当进入此页面用户什么都不操作，直接点提交
								 -->
								<input type="text" id="sysPermissionIds" name="sysPermissionsByRoleId" value="${sysPermissionsByRoleId }"/>
								<div id="permissionTreeContent" >
									<ul id="permissionTree" class="ztree"></ul>
								</div>
								</TD>
								
							</TR>
							
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="role_authorize_submit()">提交</a>
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

