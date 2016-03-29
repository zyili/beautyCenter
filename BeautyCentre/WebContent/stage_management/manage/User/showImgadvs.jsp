<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>广告位管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="../Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="../Css/style.css" />
<script type="text/javascript" src="../Js/jquery.js"></script>
<script type="text/javascript" src="../Js/jquery.sorted.js"></script>
<script type="text/javascript" src="../Js/bootstrap.js"></script>
<script type="text/javascript" src="../Js/ckform.js"></script>
<script type="text/javascript" src="../Js/common.js"></script>
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
<SCRIPT LANGUAGE="JavaScript">
function to_change(){
	if(document.getElementById('radio1').checked)
	{
		window.location.href="findCheckedShop" ;
	}
    }
</SCRIPT>
</head>
</body>
<script type="text/javascript">

	function delcfm() {
		if (!confirm("确认要删除？")) {
			return false;
		}
	}

	$(document).ready(function() {

	});
	  $(function () {
			$('#addnew').click(function(){
					window.location.href="addImgadv.jsp";
			 });
	    });
</script>

<form class="form-inline definewidth m20" action=""
	method="post">
	<table id="tb_data"
	class="table table-bordered table-hover definewidth m10"> 
	<tr>
	<td>
		<button type="button" class="btn btn-success" id="addnew">新增标签</button>
	</td>
	</tr>
</table>
	
</form>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>	
			<th>序号</th>
			<th>标题</th>
			<th>链接地址</th>
			<th>添加时间</th>
			<th>创建人</th>
			<th>到期时间</th>
			<th>操作</th>		
		</tr>
	</thead>
	<tbody>
		 <c:forEach var="imgadv" items="${sessionScope.imgadvers}" begin="0">
		<tr>
			<td>${imgadv.imgadverid}</td>
			<td>${imgadv.advername}</td>
			<td>${imgadv.tourl}</td>
			<td>${imgadv.createdate}</td>
			<td>${imgadv.createop}</td>
			<td>${imgadv.expiredate}</td>
			<td class="tablecenter"><a href="deleteImgadv?adverid=${imgadv.imgadverid}" onclick="return delcfm();">删除</a></td>
		</tr>
		</c:forEach>
	</tbody>
	<tfoot>
	</tfoot>
</table>
</body>
</html>