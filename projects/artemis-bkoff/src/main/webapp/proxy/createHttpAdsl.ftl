<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-创建HTTP ADSL</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	
	<script type="text/javascript">
				
	</script>
</head>
<body>
	<#include "/layout/header.ftl" />
	<div class="main-content">
		<div class="menuButton">
			<a href="${baseUrl}/proxy/listHttpAdsl" class="create">HTTP ADSL代理列表</a>
		</div>
		
		<div id = "table_box" style = "padding-top:30px;">
			<form action="${baseUrl}/proxy/saveHttpAdsl">
				<table>
					<tr>
						<td>URL</td>
						<td>
							<#if httpAdsl??>
								<input type="hidden" name="httpAdsl.id" value="${httpAdsl.id}">
							</#if>
							<input type="text" name="httpAdsl.url" style="width:300px;" <#if httpAdsl??>value="${httpAdsl.url}"</#if> >
						</td>
					</tr>
					<tr>
						<td>端口</td>
						<td>
							<input type="text" name="httpAdsl.port" <#if httpAdsl??>value="${httpAdsl.port}"</#if> >
						</td>
					</tr>
					
					<tr>
						<td>项目路径</td>
						<td>
							<input type="text" name="httpAdsl.contextPath" style="width:300px;" <#if httpAdsl??>value="${httpAdsl.contextPath}"</#if>>
						</td>
					</tr>
					<tr>
						<td>允许访问云</td>
						<td>
							<input type="radio" name="httpAdsl.cloudVisite" value="0" <#if httpAdsl?? && httpAdsl.cloudVisite == 0>checked</#if>>不允许
							<input type="radio" name="httpAdsl.cloudVisite" value="1" <#if httpAdsl?? && httpAdsl.cloudVisite == 1>checked</#if>>允许
						</td>
					</tr>
					<tr>
						<td>云访问频次(sec/次)</td>
						<td>
							<select name="httpAdsl.cloudFreq">
								<option value="0" <#if httpAdsl?? && httpAdsl.cloudFreq == 0 >selected</#if> >不限</option>
								<option value="10" <#if httpAdsl?? && httpAdsl.cloudFreq == 10 >selected</#if> >10</option>
								<option value="12" <#if httpAdsl?? && httpAdsl.cloudFreq == 12 >selected</#if> >12</option>
								<option value="15" <#if httpAdsl?? && httpAdsl.cloudFreq == 15 >selected</#if> >15</option>
								<option value="20" <#if httpAdsl?? && httpAdsl.cloudFreq == 20 >selected</#if> >20</option>
								<option value="30" <#if httpAdsl?? && httpAdsl.cloudFreq == 30 >selected</#if> >30</option>
								<option value="45" <#if httpAdsl?? && httpAdsl.cloudFreq == 45 >selected</#if> >45</option>
								<option value="60" <#if httpAdsl?? && httpAdsl.cloudFreq == 60 >selected</#if> >60</option>
								<option value="90" <#if httpAdsl?? && httpAdsl.cloudFreq == 90 >selected</#if> >90</option>
								<option value="120" <#if httpAdsl?? && httpAdsl.cloudFreq == 120 >selected</#if> >120</option>
								<option value="180" <#if httpAdsl?? && httpAdsl.cloudFreq == 180 >selected</#if> >180</option>
								<option value="240" <#if httpAdsl?? && httpAdsl.cloudFreq == 240 >selected</#if> >240</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>访问频次(sec/次)</td>
						<td>
							<select name="httpAdsl.freq">
								<option value="0" <#if httpAdsl?? && httpAdsl.freq == 0 >selected</#if> >不限</option>
								<option value="1" <#if httpAdsl?? && httpAdsl.freq == 1 >selected</#if> >1</option>
								<option value="2" <#if httpAdsl?? && httpAdsl.freq == 2 >selected</#if> >2</option>
								<option value="3" <#if httpAdsl?? && httpAdsl.freq == 3 >selected</#if> >3</option>
								<option value="4" <#if httpAdsl?? && httpAdsl.freq == 4 >selected</#if> >4</option>
								<option value="5" <#if httpAdsl?? && httpAdsl.freq == 5 >selected</#if> >5</option>
								<option value="6" <#if httpAdsl?? && httpAdsl.freq == 6 >selected</#if> >6</option>
								<option value="7" <#if httpAdsl?? && httpAdsl.freq == 7 >selected</#if> >7</option>
								<option value="8" <#if httpAdsl?? && httpAdsl.freq == 8 >selected</#if> >8</option>
								<option value="9" <#if httpAdsl?? && httpAdsl.freq == 9 >selected</#if> >9</option>
								<option value="10" <#if httpAdsl?? && httpAdsl.freq == 10 >selected</#if> >10</option>
								<option value="12" <#if httpAdsl?? && httpAdsl.freq == 12 >selected</#if> >12</option>
								<option value="15" <#if httpAdsl?? && httpAdsl.freq == 15 >selected</#if> >15</option>
								<option value="20" <#if httpAdsl?? && httpAdsl.freq == 20 >selected</#if> >20</option>
								<option value="30" <#if httpAdsl?? && httpAdsl.freq == 30 >selected</#if> >30</option>
								<option value="60" <#if httpAdsl?? && httpAdsl.freq == 60 >selected</#if> >60</option>
								<option value="90" <#if httpAdsl?? && httpAdsl.freq == 90 >selected</#if> >90</option>
								<option value="120" <#if httpAdsl?? && httpAdsl.freq == 120 >selected</#if> >120</option>
								<option value="180" <#if httpAdsl?? && httpAdsl.freq == 180 >selected</#if> >180</option>
								<option value="240" <#if httpAdsl?? && httpAdsl.freq == 240 >selected</#if> >240</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>重启频次(分/次)</td>
						<td>
							<select name="httpAdsl.restartFreq">
								<option value="0" <#if httpAdsl?? && httpAdsl.restartFreq == 0 >selected</#if> >不重启</option>
								<option value="300" <#if httpAdsl?? && httpAdsl.restartFreq == 300 >selected</#if> >5分钟</option>
								<option value="600" <#if httpAdsl?? && httpAdsl.restartFreq == 600 >selected</#if> >10分钟</option>
								<option value="900" <#if httpAdsl?? && httpAdsl.restartFreq == 900 >selected</#if> >15分钟</option>
								<option value="1200" <#if httpAdsl?? && httpAdsl.restartFreq == 1200 >selected</#if> >20分钟</option>
								<option value="1800" <#if httpAdsl?? && httpAdsl.restartFreq == 1800 >selected</#if> >30分钟</option>
								<option value="2700" <#if httpAdsl?? && httpAdsl.restartFreq == 2700 >selected</#if> >45分钟</option>
								<option value="3600" <#if httpAdsl?? && httpAdsl.restartFreq == 3600 >selected</#if> >60分钟</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>参数</td>
						<td>
							<input type="text" name="httpAdsl.params" style="width:300px;"  <#if httpAdsl??>value="${httpAdsl.params!}"</#if>>(逗号分隔)
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