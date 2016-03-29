<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>服务标签管理</title>
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

	function delcfm() {
		if (!confirm("确认要修改？")) {
			return false;
		}
	}
    function jsSelectItemByValue(objSelect,objItemText) {  
        for(var i=0;i<objSelect.options.length;i++) {  
            if(objSelect.options[i].value == objItemText) {  
                objSelect.options[i].selected = true;  
                break;  
            }  
        }  
    }  
	
	function changeOption()
	{
		var obj = document.getElementById("mySelect"); //定位idvar 
		index = obj.selectedIndex; // 选中索引
		var value = obj.options[index].value; 
		var url="findProdType?ptpage=1&prdid="+value;
		window.location.href=url;
	}
	$(document).ready(function() {
		showPage();
		var id=${prdid};
		jsSelectItemByValue(document.getElementById("mySelect"),id);  
	});
	  $(function () {
			$('#addnew').click(function(){
					window.location.href="addProdType.jsp";
			 });
	    });
	function showPage() {
		var totalPage = ${sessionScope.totalPage};
		var page = ${ptpage};
		var S1 = document.getElementById('s1'); //总页数
		var S2 = document.getElementById('s2'); //当前页数
		S1.innerHTML = '';
		S2.innerHTML = '';
		S1.appendChild(document.createTextNode(totalPage));
		S2.appendChild(document.createTextNode(page));
	}
	function First() {
		var page = 1;
		window.location.href = "findProdType?ptpage= " + page;
	}
	function Next() {
		var nextpage = ${ptpage}+1;
		var totalPage = ${sessionScope.totalPage};
		if (nextpage <= totalPage) {
			var url="findProdType?ptpage="+nextpage;
			window.location.href = url;
		
		} else {
			alert('错误,已到最后一页');
		}
	}
	function Prepage() {
		var prepage = ${ptpage}-1;
		if (prepage > 0) {
			window.location.href = 'findProdType?ptpage=' + prepage;
		} else {
			alert('错误,已到第一页');
		}
	}
	function Lpage() {
		var page = ${sessionScope.totalPage};
		window.location.href = 'findProdType?ptpage=' + page;
	}
</script>

<form class="form-inline definewidth m20" action="query"
	method="post">
	<table id="tb_data"
	class="table table-bordered table-hover definewidth m10"> 
	<tr>
	<td align="center">
	         服务类别：
		<select  id="mySelect" name="prdid" style='width:110px;'  onchange= "changeOption();">
		 		<c:forEach var="prd" items="${sessionScope.prds}" begin="0">
						<option id="option3" value="${prd.productid}">${prd.productname}</option>
				</c:forEach>
		</select>
	</td>
	<td>
		<button type="button" class="btn btn-success" id="addnew">新增标签</button>
	</td>
	</tr>
</table>
	
</form>
<table id="tb_data"
	class="table table-bordered table-hover definewidth m10">
	<thead>
		<tr>	
			<th>标签ID</th>
			<th>标签名称</th>
			<th>创建时间</th>
			<th>提交人</th>
			<th>操作</th>		
		</tr>
	</thead>
	<tbody>
		 <c:forEach var="prodt" items="${sessionScope.prdts}" begin="0">
		<tr>
			<td>${prodt.prodtypeid}</td>
			<td>${prodt.prodtypename}</td>
			<td>${prodt.createtime}</td>
			<td>${prodt.createusername}</td>
			<td class="tablecenter"><a href="deleteProdType?prodtypeid=${prodt.prodtypeid}" onclick="return delcfm();">删除</a></td>
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