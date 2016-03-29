<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>热门商铺管理</title>
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
</body>
<script type="text/javascript">
	function delcfm() {
		if (!confirm("确认要修改？")) {
			return false;
		}
	}
	function cdelcfm() {
		if (!confirm("确认要删除？")) {
			return false;
		}
	}
	
	$(function() {
		$('#addnew').click(function() {
			window.location.href = "addHotShop.jsp";
		});
	});
	
    function jsSelectItemByValue(objSelect,objItemText) {  
        for(var i=0;i<objSelect.options.length;i++) {  
            if(objSelect.options[i].value == objItemText) {  
                objSelect.options[i].selected = true;  
                break;  
            }  
        }  
    }  
	function changeOption() {
		var obj = document.getElementById("mySelect"); //定位idvar 
		index = obj.selectedIndex; // 选中索引
		var value = obj.options[index].value;
		var url = "editHotShops?cityid=" + value;
		window.location.href = url;
	}
	$(document).ready(function() {
		var id = ${cityid};
		jsSelectItemByValue(document.getElementById("mySelect"), id);
	});
</script>

<form class="form-inline definewidth m20" action="query" method="post">
	<table id="tb_data"
		class="table table-bordered table-hover definewidth m10">
		<tr>
			<td align="center">城市： <select id="mySelect" name="prdid"
				style='width: 110px;' onchange="changeOption();">
					<c:forEach var="city" items="${sessionScope.citys}" begin="0">
						<option id="option3" value="${city.districtid}">${city.districtname}</option>
					</c:forEach>
			</select>
			</td>
			<c:if test="${hotshopCount< '10'}">
						<td align="right">
				<button type="button" class="btn btn-success" id="addnew">新增标签</button>
			</td>
			</c:if>
			
		</tr>
	</table>
</form>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>
			<th>店铺ID</th>
			<th>店铺名称</th>
			<th>联系电话</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="shop" items="${sessionScope.hotshops}"
			varStatus="status" begin="0">
			<tr>
				<td>${shop.shopid}</td>
				<td>${shop.shopname}</td>
				<td>${shop.shopphone}</td>
				<td><a href="changehotShop?shopid=${shop.shopid}"
					onclick="return delcfm();">修改</a>
					<a href="deleteHotShop?shopid=${shop.shopid}"
								onclick="return cdelcfm();">&nbsp;&nbsp;&nbsp;删除</a>
					
					</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
		</tr>
	</tfoot>
</table>
</body>
</html>