<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-任务页面</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
				
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div style="padding-top:10px;padding-left:10px;padding-left:10px;">
			<a href="${baseUrl}/job/urlRoad?jobId=${job.id}&sessionId=${job.sessionId}">追踪</a>
			<a href="${baseUrl}/job/testUrl?jobId=${job.id}" target="_blank">测试</a>
			<a href="${baseUrl}/job/jobSeed?jobId=${job.id}">种子</a>
			<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">运行状态</a>	
		</div>
		<hr>
		
		<div>
			<h1 style="text-align:center;">
				<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">${job.name}</a>
			</h1>
		</div>
		
		<div class="menuButton">
			<a href="${baseUrl}/job/createPage?jobId=${job.id}" class="create">创建页面</a>
		</div>
		<div id = "table_box" style = "padding-top:2px;">
			<table class="list" >
				<tr>
					<th>页面名称</th>
					<th>匹配正则</th>
					<th>任务</th>
					<th>存储时间</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<#list pageVoList as it>
					<tr class="odd">
						<td>
							<a href="${baseUrl}/job/createPage?jobId=${job.id}&pageId=${it.id}">${it.name}</a>
						</td>
						<td>
							<#if it.patterns??>
							<#list it.patterns as p>
								${p}<br>
							</#list>
							</#if>
						</td>
						<td>
							<#if it.tasks??>
								<a href="${baseUrl}/task/list?pageId=${it.id}&jobId=${job.id}">任务项</a>(${it.tasks?size})								
							<#else>
								<a href="${baseUrl}/task/list?pageId=${it.id}&jobId=${job.id}">任务项</a>(0)
							</#if>
						</td>
						<td>
							${t_util.timesString(it.expires! * 60)}
						</td>
						<td>${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd')}</td>
						<td>
							<a href="javascript:if(confirm('确认删除？页面：${it.name!}'))location.href='${baseUrl}/job/deletePage?pageId=${it.id}&jobId=${job.id}'">删除</a>
						</td>
					</tr>
				</#list>
			</table>
		</div>
	</div>
</body>
</html>
</#compress>