<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>角色管理</title>
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
			window.location.href = "userMessage.jsp";
	}
</script>
<body>
	<form   class="definewidth m20">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="10%" class="tableleft">登录名</td>
				<td>${userMessage.username}</td>
			</tr>
			<tr>
				<td class="tableleft">密码</td>
				<td>${userMessage.password}</td>
			</tr>
			<tr>
				<td class="tableleft">真实姓名</td>
				<td>${userMessage.realname}</td>
			</tr>
			<tr>
				<td class="tableleft">出生日期</td>
				<td>${userMessage.brithday}</td>
			</tr>
			<tr>
				<td class="tableleft">性别</td>
				<td>
					<c:if test="${userMessage.sex==1}">
						男
						</c:if>
						<c:if test="${userMessage.sex==2}">
						女
						</c:if>
				</td>
			</tr>
			<tr>
				<td class="tableleft">类型</td>
				<td><c:if test="${userMessage.type==1}">
						个人
						</c:if> <c:if test="${userMessage.type==0}">
						企业
						</c:if> <c:if test="${userMessage.type==100}">
						管理员
						</c:if>
				</td>
			</tr>
			<tr>
				<td class="tableleft">注册时间</td>
				<td>${userMessage.createtime}</td>
			</tr>
			<tr>
				<td class="tableleft">职业</td>
				<td>${userMessage.profession}</td>
			</tr>
			<tr>
				<td class="tableleft">电话</td>
				<td>${userMessage.phone}</td>
			</tr>
			<tr>
				<td class="tableleft">地址</td>
				<td>${userMessage.adress}</td>
			</tr>
			<tr>
				<td class="tableleft">邮箱</td>
				<td>${userMessage.email}</td>
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