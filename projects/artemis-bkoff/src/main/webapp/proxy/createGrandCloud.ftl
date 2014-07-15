<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-创建盛大云代理</title>
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
			<a href="${baseUrl}/proxy/listGrandCloud" class="create">盛大云代理列表</a>
		</div>
		
		<div id = "table_box" style = "padding-top:30px;">
			<form action="${baseUrl}/proxy/saveGrandCloud">
				<table>
					<tr>
						<td>URL</td>
						<td>
							http://<input type="text" name="grandCloud.url" style="width:300px;">
						</td>
					</tr>
					<tr>
						<td>端口</td>
						<td>
							<input type="text" name="grandCloud.port" >
						</td>
					</tr>
					<tr>
						<td>访问频次(sec/次)</td>
						<td>
							<select name="grandCloud.freq">
								<option value="0">不限</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="12">12</option>
								<option value="15">15</option>
								<option value="20">20</option>
								<option value="30">30</option>
								<option value="60">60</option>
								<option value="90">90</option>
								<option value="120">120</option>
								<option value="180">180</option>
								<option value="240">240</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>项目路径</td>
						<td>
							<input type="text" name="grandCloud.contextPath" style="width:300px;">
						</td>
					</tr>
					<tr>
						<td>并发访问</td>
						<td>
							<input type="radio" name="grandCloud.mutis" value="0" selected>不支持
							<input type="radio" name="grandCloud.mutis" value="1">支持
						</td>
					</tr>
					<tr>
						<td>参数</td>
						<td>
							<input type="text" name="grandCloud.params" style="width:300px;">(逗号分隔)
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