<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-mongodb服务器配置</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
				
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<form action="${baseUrl}/mongoServerConfig/create">
		<#if mongodbConfig??>
			<input type="hidden" name="mongodbConfig.id" value="${mongodbConfig.id}">
		</#if>
		<table>
			<tr>
				<td>域名/IP</td>
				<td>
					<input type="text" name="mongodbConfig.host" value="<#if mongodbConfig??>${mongodbConfig.host}</#if>">
				</td>
			</tr>
			<tr>
				<td>端口</td>
				<td>
					<input type="text" name="mongodbConfig.port" value="<#if mongodbConfig??>${mongodbConfig.port}</#if>">
				</td>
			</tr>
			<tr>
				<td>数据库</td>
				<td>
					<input type="text" name="mongodbConfig.db" value="<#if mongodbConfig??>${mongodbConfig.db}</#if>">
				</td>
			</tr>
			<tr>
				<td>数据表</td>
				<td>
					<input type="text" name="mongodbConfig.collection" value="<#if mongodbConfig??>${mongodbConfig.collection}</#if>">
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="submit" value="提交">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
</#compress>