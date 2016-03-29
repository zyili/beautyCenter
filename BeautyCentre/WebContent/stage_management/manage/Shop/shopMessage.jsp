<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

@media ( max-width : 980px) { /* Enable use of floated navbar text */
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
			window.location.href = "shopInfo.jsp";
	}
	function check() {
		var shopname = document.getElementById("shopname").value;
		if (0 >= shopname.length) {
			alert("请输入店铺名称");
			return false;
		}
		var shopphone = document.getElementById("shopphone").value;
		if (10 >= shopphone.length) {
			alert("请输入手机号码");
			return false;
		}
		if (shopphone.length>=15) {
			alert("请输入正确的手机号码");
			return false;
		}
		var shopaddress = document.getElementById("shopaddress").value;
		if (0 >= shopaddress.length) {
			alert("请输入店铺地址");
			return false;
		}
		var businesstime = document.getElementById("businesstime").value;
		if (0 >= businesstime.length) {
			alert("请输入店铺营业时间");
			return false;
		}
		var shoplevel = document.getElementById("shoplevel").value;
		if (0 >= shoplevel.length) {
			alert("请输入店铺等级");
			return false;
		}
		if (shoplevel >= 6) {
			alert("请输入正确的店铺等级");
			return false;
		}
		return true;
	}
</script>
<body>
	<s:form name="form1" action="saveWebShopInfo"
		enctype="multipart/form-data" onsubmit="return check();" method="post"
		namespace="/">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="15%" class="tableleft">店铺ID</td>
				<td><input id="shopid" type="text" name="webshop.shopid"
					readonly="true" value="${shopinfo.shopid}"
					class="abc input-default" /></td>
			</tr>
			<tr>
				<td class="tableleft">店铺名称</td>
				<td><input type="text" name="webshop.shopname" id="shopname"
					value="${shopinfo.shopname}" class="abc input-default" /><font
					color="#FF0000">不能超过50个字符*</font></td>
			</tr>
			<tr>
				<td class="tableleft">店铺状态</td>
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
				<td class="tableleft">店铺创建时间</td>
				<td>${shopinfo.createtime}</td>
			</tr>
			<tr>
				<td class="tableleft">店铺电话</td>
				<td><input type="text" name="webshop.shopphone" id="shopphone"
					value="${shopinfo.shopphone}" class="abc input-default" /><font
					color="#FF0000">*</font></td>
			</tr>
			<tr>
				<td class="tableleft">店铺地址</td>
				<td><input type="text" name="webshop.shopaddress"
					id="shopaddress" value="${shopinfo.shopaddress}"
					class="abc input-default" /><font color="#FF0000">*</font></td>
			</tr>
			<tr>
				<td class="tableleft">营业时间</td>
				<td><input type="text" name="webshop.businesstime"
					id="businesstime" value="${shopinfo.businesstime}"
					class="abc input-default" /><font color="#FF0000">*</font></td>
			</tr>
			<tr>
				<td class="tableleft">店铺等级</td>
				<td><input type="text" name="webshop.shoplevel" id="shoplevel"
					value="${shopinfo.shoplevel}" class="abc input-default" /><font
					color="#FF0000">1~5*</font></td>
			</tr>

			<tr>
				<td class="tableleft">描述</td>
				<td><textarea rows=3 style="overflow: auto" id="shopdec"
						name="webshop.shopdec">${shopinfo.shopdec}</textarea></td>
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
					<button type="submit" id="save" name="save" class="btn btn-primary"
						type="button">保存</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-success" onclick="Back()"
						name="backid" id="backid">返回列表</button>
				</td>
			</tr>
		</table>
	</s:form>
	</form>
</body>
</html>