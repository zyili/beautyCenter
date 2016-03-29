<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>评论信息管理</title>
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
			
		});
	});
	function delcfm() {
		if (!confirm("确认要删除评论吗？")) {
			return false;
		}
	}
	$(document).ready(function() {
		showPage();
	});
	function showPage() {
		var totalPage = ${sessionScope.totalPage};
		var page = ${reviewpage};
		var S1 = document.getElementById('s1'); //总页数
		var S2 = document.getElementById('s2'); //当前页数
		S1.innerHTML = '';
		S2.innerHTML = '';
		S1.appendChild(document.createTextNode(totalPage));
		S2.appendChild(document.createTextNode(page));
	}
	function First() {
		var page = 1;
		window.location.href = 'listReviews?reviewpage=' + page;
	}
	function Next() {
		var nextpage = ${reviewpage}+1;
		var totalPage = ${sessionScope.totalPage};
		if (nextpage <= totalPage) {
			window.location.href = 'listReviews?reviewpage=' + nextpage;
		} else {
			alert('错误,已到最后一页');
		}
	}
	function Prepage() {
		var prepage = ${reviewpage}-1;
		if (prepage > 0) {
			window.location.href = 'listReviews?reviewpage=' + prepage;
		} else {
			alert('错误,已到第一页');
		}
	}
	function Lpage() {
		var page = ${sessionScope.totalPage};
		window.location.href = 'listReviews?reviewpage=' + page;
	}
</script>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>	
			<th>评论ID</th>
			<th>提交时间</th>
			<th>评论内容</th>
			<th>提交人</th>
			<th>评分</th>
			<th>店铺ID</th>
			<th>操作</th>	
		</tr>
	</thead>
	<tbody>
		 <c:forEach var="review" items="${sessionScope.reviews}" begin="0">
		<tr>
			<td>${review.reviewid}</td>
			<td>${review.createdatetime}</td>
			<td>${review.content}</td>
			<td>${review.username}</td>
			<td>${review.score}</td>
			<td>${review.shopid}</td>
			<td>
			<a href="deleteReviewByAdmin?reviewid=${review.reviewid}" onclick="return delcfm();">删除评论</a></td>
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