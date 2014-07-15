<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-网站配置</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
				
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div id = "table_box" style = "padding-top:30px;">
			<div class="menuButton">
				<a href="${baseUrl}/site/create" class="create">创建网站配置</a>
			</div>
			<table class="list" >
				<tr>
					<th>名称</th>
					<th>根域名</th>
					<th>编码</th>
					<th>访问概率</th>
					<th>云概率</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
				<#list siteConfigList as it>
					<tr class="odd">
						<td>${it.name!}</td>
						<td>${it.root!}</td>
						<td>${it.charset!}</td>
						<td>${it.shotRate!}%</td>
						<td>${it.cloudRate!}%</td>
						<td>${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd HH:mm:ss')}</td>
						<td>
							<a href="${baseUrl}/site/create?id=${it.id}">编辑</a>
							<a href="javascript:if(confirm('确认删除？'))location.href='${baseUrl}/site/delete?id=${it.id}'">删除</a>
						</td>
					</tr>
				</#list>
			</table>
		</div>
	</div>
</body>
</html>
</#compress>