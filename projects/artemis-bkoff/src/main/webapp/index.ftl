<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-监控系统</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
				
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div class="menuButton">
			<h1>系统总览</h1>
		</div>
		<div id = "table_box" style = "padding-top:5px;">
			<table>
				<tr>
					<th>待抓取量</th>
					<th>待处理量</th>
					<th>待发布量</th>
				</tr>
				<tr>
					<td>${urlsCount!}</td>
					<td>${pendsCount!}</td>
					<td>${metaCount!}</td>
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>
</#compress>