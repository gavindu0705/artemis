<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-HTTP代理</title>
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
			<a href="${baseUrl}/proxy/listGrandCloud" class="create">盛大云代理列表<#if grandCloudList??>(${grandCloudList?size})</#if></a>
		</div>
		<a href="${baseUrl}/proxy/createGrandCloud" class="create">创建盛大云代理</a>
		<div>
			<#assign enableCount=0>
			<#assign disableCount=0>
			<#assign restartingCount=0>
			<#assign errorCount=0>
			<#assign usingCound=0>
			<#assign otherCound=0>
			<#list grandCloudList as it>
				<#if it.status == 0>
					<#assign enableCount>${enableCount?number+1}</#assign>
				<#elseif it.status == 1>
					<#assign disableCount>${disableCount?number+1}</#assign>
				<#elseif it.status == 2>
					<#assign restartingCount>${restartingCount?number+1}</#assign>
				<#elseif it.status == 3>
					<#assign errorCount>${errorCount?number+1}</#assign>
				<#elseif it.status == 4>
					<#assign usingCound>${usingCound?number+1}</#assign>
				<#else>
					<#assign otherCound>${otherCound?number+1}</#assign>
				</#if>
			</#list>
			
			共有记录${grandCloudList?size}条  正常(${enableCount})  禁用(${disableCount}) 重启(${restartingCount}) 禁用(${errorCount}) 使用中(${usingCound}) 其他(${otherCound})			
		</div>
		<div id = "table_box" style = "padding-top:10px;">
			<table class="list" >
				<tr>
					<th>URL</th>
					<th>状态</th>
					<th>端口</th>
					<th>频次</th>
					<th>项目路径</th>
					<th>参数</th>
					<th>操作</th>
					<th>上次访问</th>
					<th>创建时间</th>
				</tr>
				<#if grandCloudList??>
				<#list grandCloudList as it>
					<tr class="odd">
						<td>
							<#if it.status == 0>
								${it.url}
							<#elseif it.status == 1>
								<span style="color:red;font-weight: bold;">${it.url}</span>
							<#elseif it.status == 2>
								<span style="color:blue;font-weight: bold;">${it.url}</span>
							<#elseif it.status == 3>
								<span style="color:orange;font-weight: bold;">${it.url}</span>
							<#elseif it.status == 4>
								<span style="color:green;font-weight: bold;">${it.url}</span>
							</#if>
						</td>
						<td>
							<#if it.status == 0>
								正常
							<#elseif it.status == 1>
								禁用
							<#elseif it.status == 2>
								重启中
							<#elseif it.status == 3>
								错误
							<#elseif it.status == 4>
								使用中
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
								<a href="javascript:if(confirm('确认禁用？'))location.href='${baseUrl}/proxy/updateGrandCloud?id=${it.id}&status=1'">禁用</a>
							<#else>
								<a href="javascript:if(confirm('确认启用？'))location.href='${baseUrl}/proxy/updateGrandCloud?id=${it.id}&status=0'">启用</a>
							</#if>
							&nbsp;&nbsp;
							<a href="javascript:if(confirm('确认删除？'))location.href='${baseUrl}/proxy/deleteGrandCloud?id=${it.id}'">删除</a>
						</td>
						<td><#if it.visitedDate??>${t_util.formatDate(it.visitedDate!, 'yyyy-MM-dd HH:mm:ss')}<#else>从未</#if></td>
						<td>${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd HH:mm:ss')}</td>
					</tr>
				</#list>
				</#if>
			</table>
		</div>
	</div>
</body>
</html>
</#compress>