<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>投诉处理</title>
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
</head>
<script type="text/javascript">
	function Back() {
		if (confirm("您确定要返回吗？"))
			window.location.href = "ndealCompInfo.jsp";
	}
</script>
<body>
	<form action="dealComp" method="post" class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="10%" class="tableleft">投诉ID：</td>
				<td><input type="text" name="compid" readonly="readonly"
					value="${compinfo.complaintid}"></td>
			</tr>
			<tr>
				<td width="10%" class="tableleft">提交人：</td>
				<td><input type="text" readonly="readonly"
					value="${compinfo.submitusername}"></td>
			</tr>
			<tr>
				<td class="tableleft">联系电话:</td>
				<td><input type="text" readonly="readonly"
					value="${compinfo.userphone}"></td>
			</tr>
			<tr>
				<td class="tableleft">店铺名称:</td>
				<td><input type="text" readonly="readonly"
					value="${compinfo.shopname}"></td>
			</tr>
			<tr>
				<td class="tableleft">投诉内容:</td>
				<td><textarea rows=5 readonly="readonly" style="overflow: auto">${compinfo.complaintcontent}</textarea></td>
			</tr>
			<tr>
				<td class="tableleft">处理意见:</td>
				<td><textarea rows=5 style="overflow: auto" name="feedback"></textarea></td>
			</tr>
			<tr>
				<td class="tableleft"></td>
				<td>
					<button type="submit" id="savecomp" name="savecomp"
						class="btn btn-primary" type="button">保存</button>
					<button type="button" class="btn btn-success" onclick="Back()"
						name="backid" id="backid">返回列表</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>