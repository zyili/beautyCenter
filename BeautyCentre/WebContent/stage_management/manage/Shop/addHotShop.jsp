<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>热门店铺添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="../Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="../Css/style.css" />
<link rel="stylesheet" href="../../css/demo.css" type="text/css">
<link rel="stylesheet" href="../../css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../../js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="../../js/jquery.ztree.excheck-3.5.js"></script>
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
			window.location.href = "hotShop.jsp";
	}
	
	function check() {
		var id = form1.shopid.value;
		var shopId = $('#shopid').val();//店铺的id
		if (shopId == null ||isNaN(shopId)) 
		{
			alert("请输入正确的店铺ID!");
			return false;
		}
		if (0>=id.length) {
			alert("请输入店铺ID!");
			return false;
		} 
		return true;
	}
	
	function addShop() {
		var shopId = $('#shopid').val();//店铺的id
		if (shopId != null && !isNaN(shopId)) {
			//用异步的方式去查询后台
			$.post("webfindShopinfo", {
				'shop.shopid' : shopId
			//给后台传递的参数
			}, function(data) {
				//data就是后台返回的值，应该是个json
				if (data != null) {
					if (data['shopexist'] == "no") {
						alert("该店铺不存在");
					} else {
						//添加到下拉框
						document.getElementById("shopDesc").value =data['shopname'];
					}
				} else {
					alert("id不存在");
				}
			});
		} else {
			alert("输入的店铺ID不正确！");
			//清空文本框的值
			$('#shopId').val('');
		}
	}
</script>
<body>
	<s:form name="form1" action="addHotShop"  method="post"  onsubmit="return check();" class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="10%" class="tableleft">城市ID:</td>
				<td align="center"> 
					<input id="cityid" type="text" name="cityid" readonly="true" value="${cityid}" class="abc input-default" />
				</td>
			</tr>
			<tr>
				<td class="tableleft">店铺ID:</td>
				<td><input id="shopid" type="text" name="shopid" class="abc input-default" />
				<input type="button"
					value="查询店铺名称" class="btn btn-success" onclick="addShop();" />
				</td>
			</tr>
			<tr>
				<td class="tableleft">店铺名称：</td>
				<td><input name="shopkey" id="shopDesc" class="abc input-default"/>
				</td>
			</tr>
			<tr>
				<td class="tableleft"></td>
				<td>
					<button type="submit" id="savemenus" name="savemenus"
						class="btn btn-primary" type="button">保存</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-success" name="backid" onclick="Back()"
						id="backid">返回列表</button>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>