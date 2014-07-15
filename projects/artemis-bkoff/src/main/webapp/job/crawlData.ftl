<#ftl attributes={"content_type":"text/html; charset=UTF-8"}>
<#compress>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据中心-原始网页</title>
	<link rel="stylesheet" type="text/css" href="${baseUrl}/style/main.css" />
	<#include "/common_js.ftl" />
	<script type="text/javascript" src="${baseUrl}/js/highcharts-2.2.0.js"></script>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<div style="text-align:center;">
		原始URL： <a href="${url}" target="_blank">${url}</a>
	</div>
	<div  class="main-content">
		<div style="position:relative">
			${content!}	
		</div>
	</div>
</body>
</html>
</#compress>