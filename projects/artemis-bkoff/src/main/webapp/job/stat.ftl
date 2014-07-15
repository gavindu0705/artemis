<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-运行状态-${jobVo.name!}</title>
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
			<a href="${baseUrl}/job/urlRoad?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}">追踪</a>
			<a href="${baseUrl}/job/listPage?jobId=${jobVo.id}">配置</a>
			<a href="${baseUrl}/job/testUrl?jobId=${jobVo.id}" target="_blank">测试</a>
			<a href="${baseUrl}/job/jobSeed?jobId=${jobVo.id}">种子</a>
		</div>
		<hr>
		
		<div style="padding-top:10px;padding-left:10px;padding-left:380px;">
			<table border=1 style="width:600px;">
				<tr>
					<td colspan="4" style="text-align:center;">
						<h1>${jobVo.name!}</h1>
					</td>
				</tr>
				<tr>
					<td style="width:150px;">
						任务号
					</td>
					<td>
						<h1>${jobVo.id}</h1>
					</td>
					<td style="width:150px;">
						待发布
					</td>
					<td>
						${jobMetaCount!}
					</td>
				</tr>
				<tr>
					<td style="width:100px;">状态</td>
					<td style="width:300px;">
						<#if jobVo.status == 1>
							<#if jobVo.status == 1>
								<img src="${baseUrl}/images/running.png">
							</#if>
							<span style="font-weight: bold;color:green;">运行中</span>
							<a href="javascript:if(confirm('确认停止？'))location.href='${baseUrl}/job/stop?jobId=${jobVo.id}'">停止</a>
						<#elseif jobVo.status == 0>
							<span>待命</span>
							<a href="javascript:if(confirm('确认开始？'))location.href='${baseUrl}/job/start?jobId=${jobVo.id}'">开始</a>
						<#elseif jobVo.status == 2>
							<span>完成</span>
							<a href="javascript:if(confirm('确认开始？'))location.href='${baseUrl}/job/start?jobId=${jobVo.id}'">开始</a>
						<#elseif jobVo.status == 3>
							<span>暂停</span>
							<a href="javascript:if(confirm('确认开始？'))location.href='${baseUrl}/job/start?jobId=${jobVo.id}'">开始</a>
						</#if>
					</td>
					<td style="width:100px;">级别</td>
					<td style="width:300px;">
						<#if jobVo.priority == 0>
							普通
						<#elseif jobVo.priority == 1>
							快速
						<#elseif jobVo.priority == 2>
							最快
						</#if>
					</td>
				</tr>
				<tr>
					<td>开始于</td>
					<td><#if jobVo.startDate??>${t_util.formatDate(jobVo.startDate!, 'yy-MM-dd HH:mm:ss')}</#if></td>
					<td>最长运行</td>
					<td>
						<#if jobVo.worktime?? && jobVo.worktime gt 0>
							${t_util.timesString(jobVo.worktime!)}
						<#else>
							不限
						</#if>
					</td>
				</tr>
				<tr>
					<td>结束于</td>
					<td><#if jobVo.endDate??>${t_util.formatDate(jobVo.endDate!, 'yy-MM-dd HH:mm:ss')}</#if></td>
					<td>定时</td>
					<td>
						<#if jobVo.interval == 0>
							无
						<#else>
							${t_util.timesString(jobVo.interval!)}
						</#if>
					</td>
				</tr>
				<tr>
					<td>网页总量</td>
					<td>${jobVo.crawlCount!}</td>
					<td>异常总量</td>
					<td>${jobVo.errCount!}</td>
				</tr>
				<tr>
					<td>处理总量</td>
					<td>${jobVo.taskCount!}</td>
					<td>元数据量</td>
					<td>${jobVo.metaCount!}</td>
				</tr>
			</table>
		</div>
		
		<table border=1 style="width:600px;">
			<tr>
				<#list urlDataCountMap?keys as key>
					<td>${key}</td>
				</#list>
			</tr>
			
			<tr>
				<#list urlDataCountMap?keys as key>
					<td><a href="${baseUrl}/job/stat?status=${urlsStatusNameMap[key]}&jobId=${jobVo.id!}&sessionId=${jobVo.sessionId!}">${urlDataCountMap[key]}</a></td>
				</#list>
			</tr>
		</table>
		
		<#if status??>
			<hr>
			<table>
				<tr>
					<th>URL</td>
					<th>时间</td>
				</tr>
				<#if status gt 5>
					<#list pendsList as it>
						<tr>
							<td>
								<a href="${it.id}" target="_blank">${it.id}</a>
							</td>
							<td>${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd HH:mm:ss')}</td>
						</tr>
					</#list>
				<#else>
					<#list urlsList as it>
						<tr>
							<td>
								<input type="button" value="追踪" onclick="javascript:window.open('${baseUrl}/job/urlRoad?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}&refererUrl=${t_util.encode(it.id)}')"><input type="button" value="测试" onclick="javascript:window.open('${baseUrl}/job/testUrl?jobId=${jobVo.id}&url=${t_util.encode(it.id)}')">
							</td>
							<td>
								<a href="${it.id}" target="_blank">${it.id}</a>
							</td>
							<td>${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd HH:mm:ss')}</td>
						</tr>
					</#list>
				</#if>
			</table>
		</#if>
	</div>
</body>
</html>
</#compress>