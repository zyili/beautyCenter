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
<SCRIPT LANGUAGE="JavaScript">
function to_change(){
	if(document.getElementById('radio1').checked)
	{
		window.location.href="findCheckedShop" ;
	}
  }
</SCRIPT>
</head>
</body>
<script type="text/javascript">
	$(function() {
		$('#addnew').click(function() {
			window.location.href = "addUser";
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
	function delcfm() {
		if (!confirm("确认通过？")) {
			return false;
		}
	}
	function delcfmR() {
		if (!confirm("确认拒绝？")) {
			return false;
		}
	}
	
	$(document).ready(function() {
		showPage();
		var status=${status};
		jsSelectItemByValue(document.getElementById("mySelect"),status);
	});
	function showPage() {
		var totalPage = ${sessionScope.totalPage};
		var page = ${snckpage};
		var S1 = document.getElementById('s1'); //总页数
		var S2 = document.getElementById('s2'); //当前页数
		S1.innerHTML = '';
		S2.innerHTML = '';
		S1.appendChild(document.createTextNode(totalPage));
		S2.appendChild(document.createTextNode(page));
	}
	function First() {
		var page = 1;
		var status=${status};
		window.location.href = 'findNoCheckShop?snckpage=' + page+'&status='+status;
	}
	function Next() {
		var nextpage = ${snckpage}+1;
		var totalPage = ${sessionScope.totalPage};
		var status=${status};
		if (nextpage <= totalPage) {
			window.location.href = 'findNoCheckShop?snckpage=' + nextpage+'&status='+status;
		} else {
			alert('错误,已到最后一页');
		}
	}
	function Prepage() {
		var prepage = ${snckpage}-1;
		var status=${status};
		if (prepage > 0) {
			window.location.href = 'findNoCheckShop?snckpage=' + prepage+'&status='+status;
		} else {
			alert('错误,已到第一页');
		}
	}
	function Lpage() {
		var page = ${sessionScope.totalPage};
		var status=${status};
		window.location.href = 'findNoCheckShop?snckpage=' + page+'&status='+status;
	}
</script>

<form class="form-inline definewidth m20" action="findNoCheckShop"
	method="post">
	<table id="tb_data"
	class="table table-bordered table-hover definewidth m10"> 
	<tr>
	<td align="center">
	 	<input type="radio" name="checkstatus" value="1" id="radio1"  onclick="to_change()"> 审核
	 </td>
	 <td align="center">
   		<input type="radio" name="checkstatus" value="0" id="radio2" checked="checked" onclick="to_change()"/> 未审核
	</td>
	<td align="right">
		<select  id="mySelect" name="status" style='width:110px;'  onchange= "changeOption();">
			<option id="option6" value="6"  selected = "selected" >全部</option>
			<option id="option3" value="1">初次提交</option>
			<option id="option4" value="2">修改提交</option>
		</select>
	</td>
	<td>
		<button type="submit" class="btn btn-primary">查询</button>
	</td>
	</tr>
</table>
	
</form>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>	
			<th>店铺ID</th>
			<th>店铺名称</th>
			<th>提交时间</th>
			<th>店铺等级</th>
			<th>提交类型</th>
			<th>审核操作</th>	
		</tr>
	</thead>
	<tbody>
		 <c:forEach var="shop" items="${sessionScope.checkshops}" begin="0">
		<tr>
			<td>${shop.shopid}</td>
			<td><a href="checkShopInfo?shopid=${shop.shopid}">${shop.shopname}</a></td>
			<td>${shop.createtime}</td>
			<td>${shop.shoplevel}</td>
			<td>
			<c:if test="${shop.state=='1'}">
						初次提交
			</c:if>
			<c:if test="${shop.state=='2'}">
						修改提交
			</c:if>
			</td>
			<td>
				<a href="checkShop?shopid=${shop.shopid}&checkfalge=1" onclick="return delcfm();">通过&nbsp;&nbsp;&nbsp;</a>
				<a href="checkShop?shopid=${shop.shopid}&checkfalge=2"
					onclick="return delcfmR();">&nbsp;&nbsp;&nbsp;拒绝</a></td>
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