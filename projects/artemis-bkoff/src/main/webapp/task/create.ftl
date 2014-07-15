<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-创建处理任务</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
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
		
		<div style="padding-left:5px; font-size:10px;">
			>>&nbsp;<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">${job.name}</a>
			/&nbsp;<a href="${baseUrl}/job/listPage?jobId=${job.id}">${page.name}</a>&nbsp;
			/&nbsp;<a href="${baseUrl}/task/list?jobId=${job.id}&pageId=${page.id}">任务项</a>&nbsp;
			<#if task??>
				/&nbsp;编辑任务
			<#else>
				/&nbsp;创建任务
			</#if>
		</div>
		
		<div id = "table_box" style = "padding-top:5px;">
			<form action="${baseUrl}/task/save">
				<input type="hidden" name="task.jobId" value="${job.id}">
				<input type="hidden" name="task.pageId" value="${page.id}">
				<input type="hidden" name="jobId" value="${job.id}">
				<input type="hidden" name="pageId" value="${page.id}">
				<#if task??>
					<input type="hidden" name="task.id" value="${task.id}">
				</#if>
				<table>
					<tr>
						<td>处理类</td>
						<td>
							<#if task??>
								<input type="hidden" name="task.clazz" value="${task.clazz!}">
								<span>
									${task.clazz!}
								</span>
							<#else>
								<select name="task.clazz" id="_clazz">
									<#list funcList as it>
										<option value="${it.clazz}" <#if it.clazz == clazz>selected</#if>>${it.name}</option>
									</#list>
								</select>
							</#if>
						</td>
					</tr>
					<tr>
						<td>说明</td>
						<td>
							<input type="text" name="task.caption" value="<#if task??>${task.caption!}</#if>" style="width:303px;">
						</td>
					</tr>
					<tr>
						<td>参数</td>
						<td>
							<#if task??>
								<#list task.params?keys as k>
									<table>
										<tr>
											<td style="width:90px;">
												${k}: 
											</td>
											<td>
												<input type="hidden" name="params" value="${k}">
												<input type="text" name="values" style="width:500px;" value="${task.params[k]}"><br>
											</td>		
										</tr>
									</table>
								</#list>
							<#else>
								<#list params as p>
									<table>
										<tr>
											<td style="width:90px;">
												${p}: 
											</td>
											<td>
												<input type="hidden" name="params" value="${p}">
												<input type="text" name="values" value="" style="width:500px;"><br>
											</td>		
										</tr>
									</table>
								</#list>
							</#if>
							
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<input type="submit" value="提交">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
		$("#_clazz").change(function(){
			location.href="${baseUrl}/task/create?jobId=${job.id}&pageId=${page.id}&clazz=" + $("#_clazz").val();
		})			
	</script>
	
</body>
</html>
</#compress>