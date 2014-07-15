<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-任务处理项</title>
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
			<a href="${baseUrl}/job/create?jobId=${job.id}">编辑</a>
			<a href="${baseUrl}/job/urlRoad?jobId=${job.id}&sessionId=${job.sessionId}">追踪</a>
			<a href="${baseUrl}/job/testUrl?jobId=${job.id}" target="_blank">测试</a>
			<a href="${baseUrl}/job/jobSeed?jobId=${job.id}">种子</a>
		</div>
		<hr>
		<div>
			<h1 style="text-align:center;">${job.name}</h1>
		</div>
		<div class="menuButton">
			<a href="${baseUrl}/task/create?jobId=${job.id}&pageId=${page.id}" class="create">创建</a>
		</div>
		<div style="padding-left:5px; font-size:10px;">
			>>&nbsp;<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">${job.name}</a>
			/&nbsp;<a href="${baseUrl}/job/listPage?jobId=${job.id}">${page.name}</a>&nbsp;/&nbsp;任务项
		</div>
		<div id = "table_box" style = "padding-top:5px;">
			<table >
				<tr>
					<th>名称</th>
					<th>构造参数</th>
					<th>处理类</th>
					<th>操作</th>
				</tr>
				<#list taskVoList as it>
					<tr class="odd">
						<td>
							<a href="${baseUrl}/task/create?taskId=${it.id}&jobId=${job.id}&pageId=${page.id}">${it.caption!}</a>
						</td>
						<td>
							<table>
								<#list it.params?keys as k>
									<tr>
										<td style="width:150px;font-weight:bold;">${k}:</td>
										<td>${it.params[k]}</td>
									</tr>
								</#list>
							</table>
						</td>
						<td>${it.clazzName!}</td>
						<td>
							<a href="javascript:if(confirm('确认删除？'))location.href='${baseUrl}/task/delete?id=${it.id}&jobId=${job.id}&pageId=${page.id}'">删除</a>
						</td>
					</tr>
				</#list>
			</table>
		</div>
	</div>
</body>
</html>
</#compress>