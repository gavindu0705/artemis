<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-功能方法列表</title>
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
			<a href="${baseUrl}/func/create" class="create">创建</a>
		</div>
		
		<div id = "table_box">
			<table class="list" >
				<tr>
					<th>名称</th>
					<th>class</th>
					<th>参数</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
				<#list funcList as it>
					<tr class="odd">
						<td>${it.name}</td>
						<td>${it.clazz}</td>
						<td>
							<#list it.params as p>
								${p},
							</#list>
						</td>
						<td>${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd HH:mm:ss')}</td>
						<td>
							<a href="javascript:if(confirm('确认删除？'))location.href='${baseUrl}/func/delete?id=${it.id}'">删除</a>
						</td>
					</tr>
				</#list>
			</table>
		</div>
	</div>
</body>
</html>
</#compress>