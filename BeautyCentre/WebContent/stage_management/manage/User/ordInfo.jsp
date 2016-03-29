<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单信息查询</title>
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
		if (!confirm("确认要修改？")) {
			return false;
		}
	}
	$(document).ready(function() {
		showPage();
	});
	function showPage() {
		var totalPage = ${sessionScope.totalPage};
		var page = ${page};
		var S1 = document.getElementById('s1'); //总页数
		var S2 = document.getElementById('s2'); //当前页数
		S1.innerHTML = '';
		S2.innerHTML = '';
		S1.appendChild(document.createTextNode(totalPage));
		S2.appendChild(document.createTextNode(page));
	}
	function First() {
		var page = 1;
		window.location.href = 'listOrdInfo?page=' + page;
	}
	function Next() {
		var nextpage = ${page}+1;
		var totalPage = ${sessionScope.totalPage};
		if (nextpage <= totalPage) {
			window.location.href = 'listOrdInfo?page=' + nextpage;
		} else {
			alert('错误,已到最后一页');
		}
	}
	function Prepage() {
		var prepage = ${page}-1;
		if (prepage > 0) {
			window.location.href = 'listOrdInfo?page=' + prepage;
		} else {
			alert('错误,已到第一页');
		}
	}
	function Lpage() {
		var page = ${sessionScope.totalPage};
		window.location.href = 'listOrdInfo?page=' + page;
	}
</script>
<form class="form-inline definewidth m20" action="queryOderInfo"
	method="post">
	预订人或订单号： <input type="text" name="nameororid" value="请输入内容" onfocus="javascript:if(this.value=='请输入内容')this.value='';" id="username"
		class="abc input-default">&nbsp;&nbsp;
	<button type="submit" class="btn btn-primary">查询</button>
</form>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>	
			<th>订单号</th>
			<th>预定人</th>
			<th>联系方式</th>
			<th>预定店铺</th>
			<th>订单状态</th>
			<th>订单金额</th>
			<th>创建时间</th>
			<th>操作</th>	
		</tr>
	</thead>
	<tbody>
	 <c:forEach var="ord" items="${sessionScope.ordinfo}" begin="0">
			<tr>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='orderid'}">
							<td>${map.value}</td>
						</c:if>   		 
			       </c:forEach>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='username'}">
							<td>${map.value}</td>
						</c:if>   		 
			       </c:forEach>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='ordphone'}">
							<td>${map.value}</td>
						</c:if>   		 
			       </c:forEach>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='shopname'}">
							<td>${map.value}</td>
						</c:if>   		 
			       </c:forEach>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='state'}">
			       			<td>
			       			<c:if test="${map.value=='2'}">
			       			预约中
			       			</c:if>
			       			<c:if test="${map.value=='1'}">
			       			订单完成
			       			</c:if>
			       			<c:if test="${map.value=='0'}">
			       			订单取消
			       			</c:if>
			       			<c:if test="${map.value=='3'}">
			       			已退订
			       			</c:if>
			       			</td>
						</c:if>   		 
			       </c:forEach>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='sumprice'}">
							<td>${map.value}</td>
						</c:if>   		 
			       </c:forEach>
			       <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='createtime'}">
							<td>${map.value}</td>
						</c:if>   		 
			       </c:forEach>
			          <c:forEach var="map" items="${ord}" begin="0">
			       		<c:if test="${map.key=='orderid'}">
							<td><a href="findOrdInfo?orderid=${map.value}">详细信息</a></td>
						</c:if>   		 
			       </c:forEach>
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