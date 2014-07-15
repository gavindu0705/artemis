<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-创建功能方法</title>
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
			<a href="${baseUrl}/func/list" class="create">列表</a>
		</div>
		
		<form action="${baseUrl}/func/save">
			<table>
				<tr>
					<td>方法名称</td>
					<td>
						<input type="text" name="func.name" size="50" >
					</td>
				</tr>
				<tr>
					<td>class</td>
					<td>
						<input type="text" name="func.clazz" size="100">
					</td>
				</tr>
				<tr>
					<td>参数选项</td>
					<td>
						1.<input type="text" name="params" size="5"><br>
						2.<input type="text" name="params" size="5"><br>
						3.<input type="text" name="params" size="5"><br>
						4.<input type="text" name="params" size="5"><br>
						5.<input type="text" name="params" size="5"><br>
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
</body>
</html>
</#compress>