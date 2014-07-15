<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-${jobVo.name}-种子列表</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("a[id^='edit_']").click(function() {
				var idx = $(this).attr('id').replace('edit_', '');
				$("#ua_" + idx).hide();
				$("#pa_" + idx).hide();
				$("#ca_" + idx).hide();
				$("#edit_" + idx).hide();
				$("#clk_" + idx).show();
				$("#uipt_" + idx).show();
				$("#pipt_" + idx).show();
				$("#cipt_" + idx).show();
			});
			
			$("a[id^='clk_']").click(function() {
				var idx = $(this).attr('id').replace('clk_', '');
				var seedId = $("#seid_" + idx).val();
				var newSeed = $("#uipt_" + idx).val();
				var newParam = $("#pipt_" + idx).val();
				var newCharset = $("#cipt_" + idx).val();
				
				jQuery.ajax({
					type: 'GET', 
					url: '${baseUrl}/job/editSeed',
					data: "seedId=" + seedId + "&newSeed=" + encodeURIComponent(newSeed) + "&newParam=" + encodeURIComponent(newParam) + "&newCharset=" + encodeURIComponent(newCharset),
					dataType: 'json',
					timeout: 100000,
					success: function(data) {
						location.reload();
					},
					error : function() {
						
					}
				});
				
			});
		});
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div style="padding-top:10px;padding-left:10px;padding-left:10px;">
			<a href="${baseUrl}/job/urlRoad?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}">追踪</a>
			<a href="${baseUrl}/job/listPage?jobId=${jobVo.id}">配置</a>
			<a href="${baseUrl}/job/testUrl?jobId=${jobVo.id}">测试</a>
			<a href="${baseUrl}/job/stat?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}">运行状态</a>
		</div>
		<hr>
		<div style = "padding-top:10px;">
			<form action="${baseUrl}/job/saveSeed">
			<table border=0 style="width:800px;">
				<tr>
					<td>
						<textarea name="seeds" style="width:800px;height:100px;"></textarea>
					</td>
					<td>
						<input type="hidden" name="jobId" value="${jobVo.id}">
						<input type="submit" value="增加种子"><br>
						<span style="color:red;font-size:8px;">(一行一个)</span>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div style="padding-top:10px;padding-left:480px;">
			<a href="${baseUrl}/job/stat?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}">${jobVo.name!}</a>
		</div>
		
		<div id = "table_box" style = "padding-top:30px;">
			<div style="padding-left:10px;">
				<table>
					<tr>
						<td>
							<a href="${baseUrl}/job/jobSeed?jobId=${jobVo.id}&status=-1<#if keyword??>&keyword=${t_util.encode(keyword!)}</#if>" style="<#if !status?? || status == -1>background:rgb(182, 182, 228)</#if>" >总${totalCount}个</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="${baseUrl}/job/jobSeed?jobId=${jobVo.id}&status=0<#if keyword??>&keyword=${t_util.encode(keyword!)}</#if>" style="<#if status?? && status == 0>background:rgb(182, 182, 228)</#if>">启用${enableCount}个</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="${baseUrl}/job/jobSeed?jobId=${jobVo.id}&status=1<#if keyword??>&keyword=${t_util.encode(keyword!)}</#if>" style="<#if status?? && status == 1>background:rgb(182, 182, 228)</#if>">禁用${disableCount}个</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td style="text-align:right">
							<form id="queryForm" action="${baseUrl}/job/jobSeed" method="get">
								<input type="hidden" name="jobId" value="${jobVo.id}">
								<input type="text" style="width:300px;" name="keyword" value="${keyword!}">
								<input id="queryBtn" type="button" value="查找">
							</form>
							<a href="${baseUrl}/job/jobSeed?jobId=${jobVo.id}">清除条件</a>
						</td>
					</tr>
				</table>
			</div>
			<@d_pager action = "jobSeed" currentPage = pager.currentPage controller = "job" totalPages = pager.totalPages/>
			<form id="seedForm" method="post">
			<input type="hidden" name="jobId" value="${jobVo.id}">
			<input type="hidden" id="type" name="type" value="">
			<table class="list" >
				<tr>
					<td>
						<#if !keyword?? || keyword == ''>
							<input type="button" id="disableAll" value="禁用全部">&nbsp;<input type="button" id="enableAll" value="启用全部">
						</#if>
					</td>
					<td colspan="6"></td>
				</tr>
				
				<tr>
					<th style="width:140px;">
						<input type="checkbox" id="checkall">
						<input type="button" id="disableChecked" value="禁用">&nbsp;<input type="button" id="enableChecked" value="启用">
					</th>
					<th style="width:80px;">操作</th>
					<th style="width:700px;">种子</th>
					<th style="width:180px;">参数</th>
					<th style="width:70px;">编码</th>
					<th>操作</th>
					<th>时间</th>
				</tr>
				
				<#list seedList as it>
					<tr>
						<td style="text-align:center;">
							<label for="urls_${it_index}" style="cursor:pointer;">
							<input type="checkbox" id="urls_${it_index}" name="urls" value="${it.url!}" tabIndex=${it_index}>
							<#if it.status==0>
								正常
							<#else>
								<span style="color:red;font-weight:bold;">禁用</span>
							</#if>
							</label>
						</td>
						<td>
							<input type="button" value="追踪" onclick="javascript:window.open('${baseUrl}/job/urlRoad?jobId=${jobVo.id}&sessionId=${jobVo.sessionId}&refererUrl=${t_util.encode(it.url)}')"><input type="button" value="测试" onclick="javascript:window.open('${baseUrl}/job/testUrl?jobId=${jobVo.id}&charset=${it.charset!}&url=${t_util.encode(it.url)}&extraParams=${t_util.encode(it.params!)}')">
						</td>
						<td>
							<a id="ua_${it_index}" href="${it.url}" target="_blank">${it.url}</a>
							<input style="display:none;width:700px;" id="uipt_${it_index}" type="text" name="url" value="${it.url!}" />
						</td>
						<td>
							<span id="pa_${it_index}">${it.params!}</span>
							<input style="display:none;width:180px;" id="pipt_${it_index}" type="text" name="params" value="${it.params!}" />
						</td>
						<td>
							<span id="ca_${it_index}">${it.charset!}</span>
							<input style="display:none;width:65px;" id="cipt_${it_index}" type="text" name="charset" value="${it.charset!}" />
						</td>
						<td>
							<input type="hidden" id="seid_${it_index}" value="${it.id}">
							<a id="edit_${it_index}" href="javascript:;">编辑</a>
							<a style="display:none;" id="clk_${it_index}" href="javascript:;">确定</a>
						</td>
						<td>
							${t_util.formatDate(it.creationDate!, 'yyyy-MM-dd')}
							<a href="${baseUrl}/job/deleteSeed?jobId=${jobVo.id}&seedId=${it.id}">删除</a>
						</td>
					</tr>
				</#list>
			</table>
			</form>
			<@d_pager action = "jobSeed" currentPage = pager.currentPage controller = "job" totalPages = pager.totalPages/>
		</div>
	</div>
	
	<script type="text/javascript">
		$("#checkall").click(function(){
			if($(this).attr('checked') == 'checked') {
				$("input[name='urls']").attr('checked', 'checked');
			}else {
				$("input[name='urls']").removeAttr('checked');
			}			
		})
		
		$("#disableChecked").click(function(){
			var cbox = $("input[name='urls']:checked");
			if(cbox.length > 0) {
				if(confirm('确认禁用这' + cbox.length + "个种子？")) {
					$("#seedForm").attr("action", "${baseUrl}/job/disableSeed");
					$("#seedForm").submit();
				}
			}else {
				alert("没有选中项！");
			}
		})
				
		$("#enableChecked").click(function(){
			var cbox = $("input[name='urls']:checked");
			if(cbox.length > 0) {
				if(confirm('确认启用这' + cbox.length + "个种子？")) {
					$("#seedForm").attr("action", "${baseUrl}/job/enableSeed");
					$("#seedForm").submit();
				}
			}else {
				alert("没有选中项！");
			}
		})
		
		$("#disableAll").click(function(){
			if(confirm('确认禁用所有种子吗？')) {
				$("#type").val("all");
				$("#seedForm").attr("action", "${baseUrl}/job/disableSeed");
				$("#seedForm").submit();
			}
		})
		
		$("#enableAll").click(function(){
			if(confirm('确认启用所有种子吗？')) {
				$("#type").val("all");
				$("#seedForm").attr("action", "${baseUrl}/job/enableSeed");
				$("#seedForm").submit();
			}
		})
		
		$("#queryBtn").click(function(){
			$("#queryForm").submit();
		})
		
	</script>
</body>
</html>
</#compress>