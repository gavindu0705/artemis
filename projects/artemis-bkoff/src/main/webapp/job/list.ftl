<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-任务列表</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/my.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div class="menuButton">
			<a href="${baseUrl}/job/create" class="create">创建任务</a>
		</div>
		<table>
			<tr>
				<th>待抓取总量</th>
				<th>待处理总量</th>
				<th>待发布总量</th>
			</tr>
			<tr>
				<td>${urlsCount!}</td>
				<td>${pendsCount!}</td>
				<td>${metaCount!}</td>
			</tr>
		</table>
		<div id = "table_box" style = "padding-top:10px;">
			<div style="background:#EDEEEF;margin-bottom:5px;padding-left:5px;">
			</div>
			
			<div>
				<ul class="nav-tabs" style="margin-bottom:-1px;">
					<li <#if !cat?? && (!status?? || status == -1)>class="active"</#if> >
						<a href="${baseUrl}/job/list?status=-1" class="list">全部(${jobCount!0})</a>
					</li>
				    <li <#if !cat?? && status?? && status == 1>class="active"</#if>>
				    	<a href="${baseUrl}/job/list?status=1" class="list">正在运行(${runningCount!0})</a>
				    </li>
				    <li <#if !cat?? && status?? && status == 0>class="active"</#if>>
				    	<a href="${baseUrl}/job/list?status=0" class="list">未运行(${stoppingCount!0})</a>
				    </li>
				    <#list catList! as c>
				    	<li <#if cat! == c>class="active"</#if>>
				    		<a href="${baseUrl}/job/list?cat=${c!}" class="list">${c!}</a>
				    	</li>
				    </#list>
			  	</ul>
		  	</div>
			<table class="list" >
				<tr>
					<th style="width:200px;">任务号</th>
					<th>名称</th>
					<th style="width:120px;text-align:left;">根域</th>
					<th style="width:35px;">定时</th>
					<th style="width:35px;">运行</th>
					<th style="width:35px;">权重</th>
					<th style="width:80px;text-align:center;">状态</th>
					<th style="width:240px;text-align:center;">操作</th>
				</tr>
				<#list jobVoList as it>
					<tr class="odd" <#if it.status == 1>style="background-color: #87B787;border-bottom: 3px solid #ccc;"<#else>style="color:#333;"</#if> >
						<td>${it.id}</td>
						<td>
							<#if it.status == 1>
								<img src="${baseUrl}/images/light.png" style="width:12px;height:12px;">
							</#if>
							<#if it.sessionId??>
								<a href="${baseUrl}/job/stat?jobId=${it.id}&sessionId=${it.sessionId}">${it.name}</a>
							<#else>
								${it.name}
							</#if>
						</td>
						<td>
							${it.root!}
						</td>
						<td>
							<#if it.interval == 0>
								-
							<#else>
								${t_util.timesString(it.interval!)}
							</#if>
						</td>
						<td>
							<#if it.worktime?? && it.worktime gt 0>
								${t_util.timesString(it.worktime!)}
							<#else>
								不限
							</#if>
						</td>
						<td>
							<#if it.priority == 0>
								普通
							<#elseif it.priority == 1>
								快速
							<#elseif it.priority == 2>
								最快
							</#if>
						</td>
						<td>
							<#if it.status == 1>
								<span style="">运行</span>
								<a href="javascript:if(confirm('确认停止？ 任务：【${it.name!}】'))location.href='${baseUrl}/job/stop?jobId=${it.id}'">停止</a>
							<#elseif it.status == 0>
								<span>待命</span>
								<a href="javascript:if(confirm('确认开始？ 任务：【${it.name!}】'))location.href='${baseUrl}/job/start?jobId=${it.id}'">开始</a>
							<#elseif it.status == 2>
								<span>完成</span>
								<a href="javascript:if(confirm('确认开始？'))location.href='${baseUrl}/job/start?jobId=${it.id}'">开始</a>
							<#elseif it.status == 3>
								<span>暂停</span>
								<a href="javascript:if(confirm('确认开始？'))location.href='${baseUrl}/job/start?jobId=${it.id}'">开始</a>
							</#if>
						</td>
						<td style="text-align:left;">
							<a href="${baseUrl}/job/create?jobId=${it.id}">编辑</a>
							<a href="${baseUrl}/job/listPage?jobId=${it.id}">配置</a>
							<a href="${baseUrl}/job/jobSeed?jobId=${it.id}">种子</a>
							<a href="${baseUrl}/job/copyJob?jobId=${it.id}">复制</a>
							<a name="cleanMeta" href="javascript:;" ref="${baseUrl}/job/clean?jobId=${it.id}">清除</a>
							<a name="deleteJob" href="javascript:;" ref="${baseUrl}/job/delete?jobId=${it.id}">删除</a>
						</td>
					</tr>
				</#list>
			</table>
			<@d_pager action = "list" currentPage = pager.currentPage controller = "job" totalPages = pager.totalPages/>
		</div>
	</div>
	
	<script type="text/javascript">
		$("a[id^='moveup_']").click(function(){
			var ref = $(this).attr("ref");
			$.get(ref, function(data){
				location.reload();
			});
		});
		
		$("a[id^='movetop_']").click(function(){
			if(confirm('确认置顶？')) {
				$.get($(this).attr("ref"), function(data){
					location.reload();
				});
			}
		});
		
		$("a[name='cleanMeta']").click(function(){
			if(confirm('确认清除？')) {
				jQuery.ajax({
					type: "get",
					url: $(this).attr("ref"),
					dataType:'json',
					success: function(data){
						if(data.msg != "1") {
							alert("清除失败！")
						}
						location.reload();
					}
				});
			}			
		});
		
		$("a[name='deleteJob']").click(function(){
			if(confirm('确认删除任务？')) {
				jQuery.ajax({
					type: "get",
					url: $(this).attr("ref"),
					dataType:'json',
					success: function(data){
						if(data.msg != "1") {
							alert("删除失败！")
						}
						location.reload();
					}
				});
			}			
		});
		
		
	
	</script>
</body>
</html>
</#compress>