<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详细信息</title>
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
			window.location.href = "ordInfo.jsp";
	}
</script>
<body>
	<form   class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="10%" class="tableleft">订单ID</td>
				<td>${order.orderid}</td>
			</tr>
			<tr>
				<td class="tableleft">预订人</td>
				<td>${order.submitusername}</td>
			</tr>
			<tr>
				<td class="tableleft">订单状态</td>
				<td>${order.state}</td>
			</tr>
			<tr>
				<td class="tableleft">订单创建时间</td>
				<td>${order.createtime}</td>
			</tr>
			<tr>
				<td class="tableleft">订单修改时间</td>
				<td>${order.modifytime}</td>
			</tr>
			<tr>
				<td class="tableleft">订单电话</td>
				<td>${order.ordphone}</td>
			</tr>
			<tr>
				<td class="tableleft">订单总价</td>
				<td>${order.sumprice}</td>
			</tr>
			<tr>
				<td class="tableleft">订单真实价格</td>
				<td>${order.realprice}</td>
			</tr>
			<tr>
				<td class="tableleft">订购服务数量</td>
				<td>${order.number}</td>
			</tr>
			<tr>
				<td class="tableleft"></td>
				<td>
					<button type="button" class="btn btn-success" onclick="Back()"
						name="backid" id="backid">返回列表</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>