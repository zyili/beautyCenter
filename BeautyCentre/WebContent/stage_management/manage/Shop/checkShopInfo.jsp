<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>店铺详细信息</title>
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
			window.location.href = "checkshop.jsp";
	}
</script>
<body>
	<form class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="20%" class="tableleft">店铺ID：</td>
				<td>${shopinfo.shopid}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺名称：</td>
				<td>${shopinfo.shopname}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺状态：</td>

				<td><c:if test="${shopinfo.state=='1'}">
						初次提交
					</c:if> <c:if test="${shopinfo.state=='2'}">
						修改提交
					</c:if> <c:if test="${shopinfo.state=='3'}">
						审核通过
					</c:if> <c:if test="${shopinfo.state=='4'}">
						审核拒绝
					</c:if> <c:if test="${shopinfo.state=='5'}">
						店铺关闭
					</c:if></td>
			</tr>
			<tr>
				<td class="tableleft">店铺创建时间：</td>
				<td>${shopinfo.createtime}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺电话：</td>
				<td>${shopinfo.shopphone}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺地址：</td>
				<td>${shopinfo.shopaddress}</td>
			</tr>
			<tr>
				<td class="tableleft">营业时间：</td>
				<td>${shopinfo.businesstime}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺等级：</td>
				<td>${shopinfo.shoplevel}</td>
			</tr>
			<tr>
				<td class="tableleft">预估人均消费：</td>
				<td>${shopinfo.perconsum}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺所有者姓名：</td>
				<td>${shopinfo.ownusername}</td>
			</tr>
			<tr>
				<td class="tableleft">修改时间：</td>
				<td>${shopinfo.modifytime}</td>
			</tr>
			<tr>
				<td class="tableleft">是否是热门店铺：</td>
				<td><c:if test="${shopinfo.ishotshop=='1'}">
						是
					</c:if> <c:if test="${shopinfo.ishotshop=='0'}">
						不是
					</c:if></td>
			</tr>
			<tr>
				<td class="tableleft">经度：</td>
				<td>${shopinfo.lng}</td>
			</tr>
			<tr>
				<td class="tableleft">纬度：</td>
				<td>${shopinfo.lat}</td>
			</tr>
			<tr>
				<td class="tableleft">描述：</td>
				<td>${shopinfo.shopdec}</td>
			</tr>

			<tr>
				<td>店铺相关图片：</td>
				<c:forEach var="img" items="${sessionScope.imgs}" begin="0">
					<td><img
						onload="javascript:if (this.width>450) this.width=450;if(this.height>300) this.height=300"
						src="http://121.41.35.130/BeautyCentre/shop_upload/${img.imgname}">
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