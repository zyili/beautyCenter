<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>角色添加</title>
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
			window.location.href = "ServLabel.jsp";
	}
</script>
<body>
	<form action="addProdType"  method="post" class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="10%" class="tableleft">所属类别:</td>
				<td align="center"> <select id="mySelect" name="prdid"
					style='width: 110px;' onchange="changeOption();">
						<option id="option3" value="1">美甲服务</option>
						<option id="option3" value="2">美胸服务</option>
						<option id="option4" value="3">形体管理服务</option>
						<option id="option3" value="4">养生服务</option>
						<option id="option4" value="5">科技美容服务</option>
						<option id="option3" value="6">面部美容服务</option>
						<option id="option4" value="7">泡澡服务</option>
				</select>
				</td>
			</tr>
			<tr>
				<td class="tableleft">标签名称</td>
				<td><input type="text" name="typename" /></td>
			</tr>
			<tr>
				<td class="tableleft">描述标签</td>
				<td><textarea  rows=3  style="overflow:auto" name="typedec" ></textarea></td>
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
	</form>
</body>
</html>