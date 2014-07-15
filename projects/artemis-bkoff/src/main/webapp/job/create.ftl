<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-创建Job</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
				
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div id = "table_box" style = "padding-top:30px;">
			<form action="${baseUrl}/job/save">
				<table>
					<tr>
						<td>名称</td>
						<td>
							<#if job??>
								<input type="hidden" name="job.id" value="${job.id}">
							</#if>
							<input style="width:260px;" type="text" name="job.name" value="<#if job??>${job.name}</#if>">
						</td>
					</tr>
					<tr>
						<td>类型</td>
						<td>
							<input type="text" name="job.cat" value="<#if job??>${job.cat!}</#if>" >
						</td>
					</tr>
					<tr>
						<td>根域名</td>
						<td>
							<select name="job.root">
								<option>==根域名==</option>
								<#list siteConfigList as siteConfig>
									<option <#if job?? && job.root?? && siteConfig.root == job.root>selected</#if> value="${siteConfig.root}">${siteConfig.root}</option>
								</#list>
							</select>
							<a href="${baseUrl}/site/list" style="font-size:8px;">网站配置</a>
						</td>
					</tr>
					<tr>
						<td>优先级</td>
						<td>
							<select name="job.priority">
								<option value="0" <#if job?? && job.priority==0>selected</#if> >普通</option>
								<option value="1" <#if job?? && job.priority==1>selected</#if> >快速</option>
								<option value="2" <#if job?? && job.priority==2>selected</#if> >最快</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>定时器</td>
						<td>
							<select name="job.interval">
								<option value="0">无</option>
								<option value="3600" <#if job?? && job.interval==3600>selected</#if> >1小时</option>
								<option value="7200" <#if job?? && job.interval==7200>selected</#if> >2小时</option>
								<option value="14400" <#if job?? && job.interval==14400>selected</#if> >4小时</option>
								<option value="28800" <#if job?? && job.interval==28800>selected</#if> >8小时</option>
								<option value="43200" <#if job?? && job.interval==43200>selected</#if> >12小时</option>
								<option value="86400" <#if job?? && job.interval==86400>selected</#if> >1天</option>
								<option value="172800" <#if job?? && job.interval==172800>selected</#if> >2天</option>
								<option value="259200" <#if job?? && job.interval==259200>selected</#if> >3天</option>
								<option value="604800" <#if job?? && job.interval==604800>selected</#if> >7天</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>运行时长</td>
						<td>
							<select name="job.worktime">
								<option value="0">不限</option>
								<option value="3600" <#if job?? && job.worktime==3600>selected</#if> >1小时</option>
								<option value="7200" <#if job?? && job.worktime==7200>selected</#if> >2小时</option>
								<option value="14400" <#if job?? && job.worktime==14400>selected</#if> >4小时</option>
								<option value="28800" <#if job?? && job.worktime==28800>selected</#if> >8小时</option>
								<option value="43200" <#if job?? && job.worktime==43200>selected</#if> >12小时</option>
								<option value="86400" <#if job?? && job.worktime==86400>selected</#if> >1天</option>
								<option value="172800" <#if job?? && job.worktime==172800>selected</#if> >2天</option>
								<option value="259200" <#if job?? && job.worktime==259200>selected</#if> >3天</option>
								<option value="604800" <#if job?? && job.worktime==604800>selected</#if> >7天</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>序号</td>
						<td>
							<input type="text" name="job.sequence" size="5" value="<#if job??>${job.sequence!}</#if>" >
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