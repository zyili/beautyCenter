<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>广告添加</title>
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
			window.location.href = "getImgadvsInfo";
	}
	function imgcheck() {
		var strFileName = form1.upload.value;
		var strtype = strFileName.substring(strFileName.length - 3,
				strFileName.length);
		var strtype1 = strFileName.substring(strFileName.length - 4,
				strFileName.length);
		if (strFileName.length === 0) {
			alert("请选择要上传的文件");
			return false;
		} else {
			strtype = strtype.toLowerCase();
			if (strtype == "gif" || strtype == "jpg" || strtype == "png"
					|| strtype == "jpeg") {
				return true;
			} else {
				alert("这种文件类型不允许上传！\r\n只允许上传图片文件\r\n请重新选择文件上传。");
				return false;
			}
			return false;
		}

	}
	function daycheck() {
		var day = form1.expday.value;
		if (day > 0) {
			if (day > 60) {
				document.getElementById("expday").value = "";
				alert("有效期不能超过60天");

			}
		} else {
			alert("有效期必须大于0");
			document.getElementById('expday').value = "";
		}
	}
	function check() {
		var flage = false;
		var strFileName = form1.upload.value;
		var strtype = strFileName.substring(strFileName.length - 3,
				strFileName.length);
		var strtype1 = strFileName.substring(strFileName.length - 4,
				strFileName.length);
		if (strFileName.length === 0) {
			alert("请选择要上传的文件");
			return false;
		} else {
			strtype = strtype.toLowerCase();
			if (strtype == "gif" || strtype == "jpg" || strtype == "png"
					|| strtype == "jpeg") {
				var day = form1.expday.value;
				if (day.length > 0) {
					flage = true;
				} else {
					alert("请填写有效期限");
				}
			} else {
				alert("这种文件类型不允许上传！\r\n只允许上传图片文件\r\n请重新选择文件上传。");
			}
			return flage;
		}

	}
</script>
<body>
	<s:form name="form1" action="saveImgadve" enctype="multipart/form-data"
		onsubmit="return check();" method="post" namespace="/">
		<table class="table table-bordered table-hover definewidth m10">
			<tr>
				<td width="20%" class="tableleft">广告标题：</td>
				<td><input type="text" name="imgadv.advername" /></td>
			</tr>
			<tr>
				<td class="tableleft">链接地址</td>
				<td><input type="text" name="imgadv.tourl" /></td>
			</tr>
			<tr>
				<s:file name="upload" id="upload" onchange="imgcheck();"
					label="选择广告图片" />
			</tr>
			<tr>
				<td class="tableleft">有效时长：</td>
				<td><input type="text" id="expday" name="expday"
					onchange="daycheck();" />(有效值0-60天)</td>
			</tr>
			<tr>
				<td class="tableleft">指定城市：</td>
				<td><select id="mySelect" name="city" style='width: 110px;'
					onchange="changeOption();">
						<c:forEach var="city" items="${sessionScope.citys}" begin="0">
							<option value="${city.districtid}">${city.districtname}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="tableleft">广告描述</td>
				<td><textarea rows=4 cols=4 style="overflow: auto"
						name="imgadv.adverdec"></textarea></td>
			</tr>
			<tr>
				<td class="tableleft"></td>
				<td>
					<button type="submit" id="save" name="save" class="btn btn-primary"
						type="button">保存</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-success" name="backid"
						onclick="Back()" id="backid">返回列表</button>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>