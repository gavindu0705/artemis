<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-创建页面</title>
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
		<br>
		<div>
			<h1 style="text-align:center;">
				【${job.name}】
			</h1>
		</div>
		
		<div style="padding-left:5px; font-size:10px;">
			>>&nbsp;<a href="${baseUrl}/job/stat?jobId=${job.id}&sessionId=${job.sessionId}">${job.name}</a>
			<#if page??>
				/&nbsp;<a href="${baseUrl}/job/listPage?jobId=${job.id}">页面规则</a>&nbsp;/&nbsp;${page.name}
			 <#else>
			 	/&nbsp;<a href="${baseUrl}/job/listPage?jobId=${job.id}">页面规则</a>&nbsp;/&nbsp;创建
			 </#if>
		</div>
		
		<div id = "table_box" style = "padding-left:5px; padding-top:5px;">
			<form action="${baseUrl}/job/savePage">
				<table>
					<tr>
						<td>名称</td>
						<td>
							<input type="hidden" name="page.jobId" value="${job.id}">
							<input type="hidden" name="jobId" value="${job.id}">
							<#if page??>
								<input type="hidden" name="page.id" value="${page.id}">
								<input type="hidden" name="pageId" value="${page.id}">
							</#if>
							<input type="text" name="page.name" value="<#if page??>${page.name}</#if>">
						</td>
					</tr>
					<tr>
						<td>PASS PATTERN</td>
						<td>
							<textarea name="patterns" style="width:600px;height:80px;"><#if page?? && page.patterns??>${t_util.join(page.patterns!, '\r\n')}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td>存储时间</td>
						<td>
							<select name="page.expires">
								<option value="1" <#if page?? && page.expires == 1>selected</#if>>1分钟</option>
								<option value="10" <#if page?? && page.expires == 10>selected</#if>>10分钟</option>
								<option value="30" <#if page?? && page.expires == 30>selected</#if>>30分钟</option>
								<option value="60" <#if page?? && page.expires == 60>selected</#if>>1小时</option>
								<option value="120" <#if page?? && page.expires == 120>selected</#if>>2小时</option>
								<option value="240" <#if page?? && page.expires == 240>selected</#if>>4小时</option>
								<option value="480" <#if page?? && page.expires == 480>selected</#if>>8小时</option>
								<option value="720" <#if page?? && page.expires == 720>selected</#if>>12小时</option>
								<option value="1440" <#if page?? && page.expires == 1440>selected</#if>>1天</option>
								<option value="2880" <#if page?? && page.expires == 2880>selected</#if>>2天</option>
								<option value="4320" <#if page?? && page.expires == 4320>selected</#if>>3天</option>
								<option value="10080" <#if page?? && page.expires == 10080>selected</#if>>7天</option>
								<option value="30240" <#if page?? && page.expires == 30240>selected</#if>>21天</option>
								<option value="43200" <#if page?? && page.expires == 43200>selected</#if>>30天</option>
								<option value="129600" <#if page?? && page.expires == 129600>selected</#if>>90天</option>
								<option value="-1" <#if page?? && page.expires == -1>selected</#if>>永不过期</option>
							</select>
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
</body>
</html>
</#compress>