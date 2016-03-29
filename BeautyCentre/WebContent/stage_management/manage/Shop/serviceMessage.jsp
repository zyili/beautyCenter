<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>服务详细信息</title>
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

@media
(
max-width
:
980px)
{ /* Enable use of floated navbar text */
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
			window.location.href = "serviceInfo.jsp";
	}
</script>
<body>
	<form class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="15%" class="tableleft">服务ID</td>
				<td>${service.serviceid}</td>
			</tr>
			<tr>
				<td class="tableleft">服务名称</td>
				<td>${service.servicename}</td>
			</tr>
			<tr>
				<td class="tableleft">服务类型</td>
				<td>${prod.productname}</td>
			</tr>
			<tr>
				<td class="tableleft">服务状态</td>
				<td><c:if test="${service.state=='0'}">
						下架
					</c:if> <c:if test="${service.state=='1'}">
						正常
					</c:if></td>
			</tr>
			<tr>
				<td class="tableleft">店铺ID</td>
				<td>${sop.shopid}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺名称</td>
				<td>${sop.shopname}</td>
			</tr>
			<tr>
				<td class="tableleft">服务创建时间</td>
				<td>${service.createtime}</td>
			</tr>
			<tr>
				<td class="tableleft">服务价格</td>
				<td>${service.price}</td>
			</tr>
			<tr>
				<td class="tableleft">服务描述</td>
				<td>${service.servicedec}</td>
			</tr>
			<tr>
				<td>服务图片：</td>
				<c:forEach var="img" items="${sessionScope.simgs}" begin="0">
					<td><img
						onload="javascript:if (this.width>450) this.width=450;if(this.height>300) this.height=300"
						src="http://121.41.35.130/BeautyCentre/service_upload/${img.imgname}">
					</td>
				</c:forEach>
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