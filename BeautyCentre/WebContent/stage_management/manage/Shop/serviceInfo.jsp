<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>商铺信息审核</title>
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
	$(function() {
		$('#addnew').click(function() {
			window.location.href = "addUser";
		});
	}); 
	function delcfm() {
		if (!confirm("确认要恢复服务？")) {
			return false;
		}
	}
	function cdelcfm() {
		if (!confirm("确认要下架服务？")) {
			return false;
		}
	}
	$(document).ready(function() {
		showPage();
	});
	function showPage() {
		var totalPage = ${sessionScope.totalPage};
		var page = ${svpage};
		var S1 = document.getElementById('s1'); //总页数
		var S2 = document.getElementById('s2'); //当前页数
		S1.innerHTML = '';
		S2.innerHTML = '';
		S1.appendChild(document.createTextNode(totalPage));
		S2.appendChild(document.createTextNode(page));
	}
	function First() {
		var page = 1;
		var status=${svnamorid};
		window.location.href = 'serviceInfo?svpage=' + page+'&svnamorid='+status;
	}
	function Next() {
		var nextpage = ${svpage}+1;
		var totalPage = ${sessionScope.totalPage};
		var status=${svnamorid};
		if (nextpage <= totalPage) {
			window.location.href = 'serviceInfo?svpage=' + nextpage+'&svnamorid='+status;
		} else {
			alert('错误,已到最后一页');
		}
	}
	function Prepage() {
		var prepage = ${svpage}-1;
		var status=${svnamorid};
		if (prepage > 0) {
			window.location.href = 'serviceInfo?svpage=' + prepage+'&svnamorid='+status;
		} else {
			alert('错误,已到第一页');
		}
	}
	function Lpage() {
		var page = ${sessionScope.totalPage};
		var status=${svnamorid};
		window.location.href = 'serviceInfo?svpage=' + page+'&svnamorid='+status;
	}
</script>
<form class="form-inline definewidth m20" action="serviceInfo"
	method="post">
	 请输入商家用户名或者ID:<input type="text" name="svnamorid" value="${querykey}"
		onfocus="javascript:if(this.value=='请输入商家用户名或者ID')this.value='';"
		id="username" class="abc input-default">&nbsp;&nbsp;
	<button type="submit" class="btn btn-primary">查询</button>
</form>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>	
			<th>服务ID</th>
			<th>服务名称</th>
			<th>服务价格</th>
			<th>发布时间</th>
			<th>店铺名称</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
				<c:forEach var="serv" items="${sessionScope.servs}" begin="0">
			<tr>
				<td>${serv.serviceid}</td>
				<td>${serv.servicename}</td>
				<td>${serv.price}</td>
				<td>${serv.createtime}</td>
				<td>${serv.shopname}</td>
				<td><a
					href="findServInfo?servid=${serv.serviceid}&backjsp=listShopInfo">详细信息&nbsp;&nbsp;&nbsp;</a>
					<c:choose>
						<c:when test="${serv.state=='0'}">
							<a href="recoveryService?servid=${serv.serviceid}&svnamorid=${svnamorid}"
								onclick="return delcfm();">&nbsp;&nbsp;&nbsp;恢复服务</a>
						</c:when>
						<c:otherwise>
							<a href="closeService?servid=${serv.serviceid}&svnamorid=${svnamorid}"
								onclick="return cdelcfm();">&nbsp;&nbsp;&nbsp;下架服务</a>
						</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="5">总共<span id="s1"></span>页
				&nbsp;&nbsp;&nbsp;&nbsp;当前第<span id="s2"></span>页
				<div id="div-button">
					<input type="button" onClick="First();" value="首页" id="F-page">
					&nbsp;&nbsp; <input type="button" onClick="Next();" value="下一页"
						id="Nex-page"> &nbsp;&nbsp; <input type="button"
						onClick="Prepage();" value="上一页" id="Pre-page">
					&nbsp;&nbsp; <input type="button" onClick="Lpage();" value="尾页"
						id="L-page">
				</div>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>