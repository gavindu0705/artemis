<link rel="icon" href="${baseUrl}/favicon.ico" type="image/x-icon" /> 
<script type="text/javascript" src="${baseUrl}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/city_select.js"></script>
<script type="text/javascript" src="${baseUrl}/js/jquery.datepick.js"></script>
<script type="text/javascript" src="${baseUrl}/js/jquery.datepick-zh-CN.js"></script>


<script type="text/javascript" src="../style/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../style/bootstrap/js/alert.js"></script>
<script type="text/javascript" src="../style/bootstrap/js/tooltip.js"></script>
    
<script type="text/javascript">
var Constants = {
	baseUrl : '${baseUrl}',
}
if($.browser.msie && parseInt($.browser.version.slice(0, 1))<8){
	alert("您正在使用基于IE内核的浏览器，而且版本小于8，请使用更高版本的浏览器，或者改用firefox或chrome浏览器！")
}
$(document).ready(function(){
	$('.list tr:odd').addClass('odd');
	$('.list tr:even').addClass('even');
});
</script>