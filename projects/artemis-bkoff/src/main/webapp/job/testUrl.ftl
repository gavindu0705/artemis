<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-${job.name}-测试抓取</title>
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
			<a href="${baseUrl}/job/listPage?jobId=${job.id}">配置</a>
			<a href="${baseUrl}/job/jobSeed?jobId=${job.id}">种子</a>
			<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">运行状态</a>
		</div>
		<hr>
		<div style="padding-top:10px;padding-left:10px;padding-left:480px;">
			<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">${job.name!}</a>
		</div>		
		<div class="menuButton">
			<form action="${baseUrl}/job/testUrl">
				<input type="hidden" name="jobId" value="${job.id}">
				<table class="list">
					<tr>
						<td>URL:</td>
						<td>
							<input type="text" name="url" value="${url!}" style="width:360px;"><#if msg??><span style="color:red;font-size:8px;">${msg!}</span></#if>
						</td>
					</tr>
					<tr>
						<td>参数:</td>
						<td>
							<input type="text" name="extraParams" value="${extraParams!}" style="width:360px;">
						</td>
					</tr>
					<tr>
						<td>编码:</td>
						<td>
							<input type="text" name="charset" value="${charset!}" style="width:360px;">
							<input type="submit" value="开始测试">
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id = "table_box" style = "padding-top:2px;">
			<#if productData??>
				<#if productData.urls??>	
					<table class="list">
						<tr>
							<th style="width:60px;">&nbsp;</th>
							<th style="width:860px;">URL (${productData.urls?size}条)</th>
							<th style="width:40px;">编码</th>
							<th style="width:80px;">参数</th>
						</tr>
						<#list productData.urls as it>
							<tr>
								<td>
									<input type="button" value="测试" onclick="javascript:location.href='${baseUrl}/job/testUrl?jobId=${job.id}&charset=${charset!}&url=${t_util.encode(it.id!)}&extraParams=${t_util.encode(t_util.mapToString(it.params!, ','))}'">
								</td>
								<td><a href="${it.id}" target="_blank">${it.id}</a></td>
								<td>${it.charset!}</td>
								<td>${t_util.mapToString(it.params!, ',')}</td>
							</tr>
						</#list>
					</table>
				</#if>
				
				<hr>
				<#if productData.metadata??>	
					<table class="list">
						<tr>
							<td style="width:60px;">URL：</td>
							<td>${productData.metadata.url!}</td>
						</tr>
						<tr>
							<td>cat：</td>
							<td>${productData.metadata.cat!}</td>
						</tr>
						<tr>
							<td>JOB_ID：</td>
							<td>${productData.metadata.jobId!}</td>
						</tr>
						<tr>
							<td>数据：</td>
							<td>
								${t_util.metadataToString(productData.metadata.data!, '<br>')}
							</td>
						</tr>
					</table>
				</#if>
			</#if>
		</div>

	</div>
</body>
</html>
</#compress>