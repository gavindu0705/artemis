<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-URL_ROAD</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript">
		$(document).ready(function(){
			$("input[name^='sbtn_']").click(function(){
				var idx = $(this).attr('name').replace('sbtn_', '')
				$("#refererUrl").val($("#btnval_" + idx).val());
				$("#requestUrl").val('');
				$("#submitBtn").trigger('click');
			});
			
			$("input[name^='tbtn_']").click(function(){
				var idx = $(this).attr('name').replace('tbtn_', '')
				$("#refererUrl").val('');
				$("#requestUrl").val($("#btnval_" + idx).val());
				$("#submitBtn").trigger('click');
			});
		});
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div style="padding-top:10px;padding-left:10px;padding-left:10px;">
			<a href="${baseUrl}/job/create?jobId=${jobVo.id}">编辑</a>
			<a href="${baseUrl}/job/listPage?jobId=${jobVo.id}">配置</a>
			<a href="${baseUrl}/job/testUrl?jobId=${jobVo.id}">测试</a>
			<a href="${baseUrl}/job/jobSeed?jobId=${jobVo.id}">种子</a>
			<a href="${baseUrl}/job/stat?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}">运行状态</a>
		</div>
		<hr>
		
		<div style="padding-top:10px;padding-left:480px;">
			<a href="${baseUrl}/job/stat?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}">${jobVo.name!}</a>
		</div>
		
		<div id="table_box" style="padding-top:10px;padding-left:10px;">
			<form action="${baseUrl}/job/urlRoad">
				<input type="hidden" name="jobId" value="${jobId!}">
				<input type="hidden" name="sessionId" value="${sessionId!}">
				<table>
					<tr>
						<td style="width:100px;">URL</td>
						<td><input id="requestUrl" type="text" name="requestUrl" value="${requestUrl!}" style="width:800px;"></td>
					</tr>
					<tr>
						<td>REFERER</td>
						<td><input id="refererUrl" type="text" name="refererUrl" value="<#if refererUrl??>${refererUrl!}<#elseif seed??>${seed.url!}</#if>" style="width:800px;"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<input id="submitBtn" type="submit" value="来自">
							<input id="submitBtn" type="submit" value="去自">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<hr>
		<br>
		<#if urlRoadMap??>
			<div id = "table_box" style = "padding-top:10px;padding-left:10px;">
				<table>
					<tr>
						<th>URL</td>
						<th>流程</td>
					</tr>
					<#list urlRoadMap?keys as key>
						<tr>
							<td>
								<input name="sbtn_${key_index}" type="button" value="源">
								<input name="tbtn_${key_index}" type="button" value="目标">
								<input id="btnval_${key_index}" type="hidden" value="${key}">
								<a href="${baseUrl}/job/crawlData?jobId=${jobId!}&url=${t_util.encode(key)}" target="_blank">${key}</a>
							</td>
							<td>
								<#list urlRoadMap[key] as it>
									<span style="">
										<span style="font-weight: bold;font-size: 16px;">[${urlsStatusCodeMap[it.status?string]}]</span>
										<span title="[url] ${it.requestUrl} [referer] ${it.refererUrl!}">${t_util.formatDate(it.creationDate!, 'yy-MM-dd HH:mm:ss')}</span>&nbsp;
									</span>
								</#list>
							</td>
						</tr>
					</#list>
				</table>
			</div>
		</#if>
	</div>
</body>
</html>
</#compress>