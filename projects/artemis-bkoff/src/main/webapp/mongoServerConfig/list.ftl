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
		<div class="menuButton">
			<a href="${baseUrl}" class="list">首页</a>
			<a href="${baseUrl}/mongoServerConfig/edit" class="create">创建数据服务器</a>
		</div>
		
		<table>
			<tr>
				<th>
					<a href="#">域名/IP</a>
				</th>
				<th>
					<a href="#">端口</a>
				</th>
				<th>
					<a href="#">DB</a>
				</th>
				<th>
					<a href="#">表名</a>
				</th>
				<th>
					<a href="#">时间</a>
				</th>
				<th>
					<a href="#">操作</a>
				</th>
			</tr>
			<#list mongodbConfigList as it>
				<tr>
					<td>${it.host}</td>
					<td>${it.port}</td>
					<td>${it.db}</td>
					<td>${it.collection}</td>
					<td>
					<#if it.creationDate??>
						${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd HH:mm:ss')}
					</#if>
					</td>
					<td>
						<a href="javascript:if(confirm('确认删除？'))location.href='${baseUrl}/mongoServerConfig/delete?id=${it.id}'">删除</a>
						<a href="${baseUrl}/mongoServerConfig/edit?id=${it.id}">修改</a>
					</td>
				</tr>
			</#list>
		</table>
	</div>
</body>
</html>
</#compress>