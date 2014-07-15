<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-网站配置</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div id = "table_box" style = "padding-top:5px;">
			<form action="${baseUrl}/site/save">
				<table>
					<tr>
						<td>网站名称</td>
						<td>
							<#if siteConfig??>
								<input type="hidden" name="siteConfig.id" value="${siteConfig.id}">
							</#if>
							<input type="text" name="siteConfig.name" value="<#if siteConfig??>${siteConfig.name!}</#if>" style="width:303px;">
						</td>
					</tr>
					<tr>
						<td>根域名</td>
						<td>
							<input type="text" name="siteConfig.root" value="<#if siteConfig??>${siteConfig.root!}</#if>" style="width:303px;">
						</td>
					</tr>
					<tr>
						<td>编码</td>
						<td>
							<input type="text" name="siteConfig.charset" value="<#if siteConfig??>${siteConfig.charset!}</#if>">
						</td>
					</tr>
					<tr>
						<td>访问概率</td>
						<td>
							<input type="text" name="siteConfig.shotRate" value="<#if siteConfig??>${siteConfig.shotRate!}</#if>">
						</td>
					</tr>
					<tr>
						<td>云概率</td>
						<td>
							<input type="text" name="siteConfig.cloudRate" value="<#if siteConfig??>${siteConfig.cloudRate!}</#if>">
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
	</script>
	
</body>
</html>
</#compress>