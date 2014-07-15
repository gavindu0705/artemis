<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-Nginx HTTP代理</title>
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
			<a href="${baseUrl}/proxy/listHttpAdsl" class="create">Http ADSL代理列表<#if httpAdslList??>(${httpAdslList?size})</#if></a>
			<a href="${baseUrl}/proxy/listNginxAdsl" class="create">Nginx ADSL代理列表<#if nginxAdslList??>(${nginxAdslList?size})</#if></a>
			<a href="${baseUrl}/proxy/listGrandCloud" class="create">盛大云代理列表<#if grandCloudList??>(${grandCloudList?size})</#if></a>
		</div>
		<a href="${baseUrl}/proxy/createNginxAdsl" class="create">创建Nginx ADSL</a>
		<div>
			共有记录${nginxAdslList?size}条
		</div>
		<div id = "table_box" style = "padding-top:10px;">
			<table class="list" >
				<tr>
					<th>URL</th>
					<th>端口</th>
					<th>频次</th>
					<th>项目路径</th>
					<th>参数</th>
					<th>操作</th>
					<th>上次访问</th>
					<th>创建时间</th>
				</tr>
				<#if nginxAdslList??>
				<#list nginxAdslList as it>
					<tr class="odd">
						<td>
							<#if it.status == 0>
								${it.url!}
							<#else>
								<span style="color:red;font-weight: bold;">${it.url}</span>
							</#if>
						</td>
						<td>${it.port!}</td>
						<td>
							<#if it.freq?? && it.freq gt 0>
								${it.freq!}sec/次
							<#else>
								不限
							</#if>
						</td>
						<td>${it.contextPath!}</td>
						<td>${it.params!}</td>
						<td style="width:150px;">
							<#if it.status == 0>
								<a href="javascript:if(confirm('确认禁用？'))location.href='${baseUrl}/proxy/updateNginxAdsl?id=${it.id}&status=1'">禁用</a>
							<#else>
								<a href="javascript:if(confirm('确认启用？'))location.href='${baseUrl}/proxy/updateNginxAdsl?id=${it.id}&status=0'">启用</a>
							</#if>
							&nbsp;&nbsp;
							<a href="javascript:if(confirm('确认删除？'))location.href='${baseUrl}/proxy/deleteNginxAdsl?id=${it.id}'">删除</a>
						</td>
						<td><#if it.visitedDate??>${t_util.formatDate(it.visitedDate!, 'yy-MM-dd HH:mm:ss')}<#else>从未</#if></td>
						<td>${t_util.formatDate(it.creationDate!, 'yy-MM-dd HH:mm:ss')}</td>
					</tr>
				</#list>
				</#if>
			</table>
		</div>
	</div>
</body>
</html>
</#compress>