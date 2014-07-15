var city_select_current_key = "";
$(document).ready(function(){
	var city_select_showed = false;
	$("#city-border-mid").click(function(){
		if (city_select_showed) {
			hideSelectDiv();
		} else {
			showSelectDiv();
		}
	});
	
	$(document).bind("click", function(e){
		// 在选择区域外单击，关闭层
		if ($(e.target).closest("div[class='wt-city']").length == 0 && city_select_showed) {
			hideSelectDiv();
		}
	}).bind("keydown", function(e){
		// 键盘选择
		if (city_select_showed && e.keyCode >= 65 && e.keyCode <= 90) {
			var key = String.fromCharCode(e.keyCode);	//当前按键字母
			if (city_select_current_key.length == 0) {
				// 没有保留的字母，去找是否有该字母的标签，有则高亮
				showSpell(key);
			} else {
				// 有保留的字母，组合后去找城市
				if ($("a[cn^='" + city_select_current_key + key.toLowerCase() + "']").length > 0) {
					// 有字母组合的城市，高亮
					$("a[name=city-display][class*=hover]").removeClass("hover");
					$("a[cn^='" + city_select_current_key + key.toLowerCase() + "']").eq(0).addClass("hover");
				} else {
					// 没有字母组合的城市，则选择字母标签
					showSpell(key);
				}
			}
			
			// 3秒内可以按多个字母键，根据城市简拼，组合查找城市
			city_select_current_key += key.toLowerCase();
			setTimeout("city_select_current_key='';", 3000);
		} 
	});
	
	// 字母选择
	$("a[name='city-spell']").click(function(){
		$("div[id^='city-con']:visible").hide();
		$("#city-con" + $(this).attr("letter")).show();
		
		$("a[name='city-spell'][class*='current']").removeClass("current");
		$(this).addClass("current");
	});
	
	// 城市选择
	$("a[name='city-display']").click(function(){
		hideSelectDiv();
		
		// 选择的城市没变
		if ($("#wt-city-title").text() == $(this).attr("display")) {
			return;
		}
		
		// 修改显示的城市名
		$("#wt-city-title").text($(this).attr("display"));
		$("div.wt-city").find(":hidden").val($(this).attr("display"));
		
		// 没有回调函数
		if (!city_select_callback) {
			return;
		}
		
		// 回调函数未定义
		try {
			eval(city_select_callback);
		} catch(e) {
			alert('回调函数未定义！');
			return;
		}
		
		// 根据回调值域获取参数值，并调用回调函数
		var params = "";
		if (city_select_callbackField) {
			for (var i = 0; i < city_select_callbackField.length; i++) {
				if (i < city_select_callbackField.length - 1) {
					params += "'" + $(this).attr(city_select_callbackField[i]) + "', ";
				} else {
					params += "'" + $(this).attr(city_select_callbackField[i]) + "'";
				}
			}
			
			eval(city_select_callback + "(" + params + ")");
		}
	}).mouseover(function(){
		$("a[name=city-display][class*=hover]").removeClass("hover");
	});
	
	$("#city_clear").click(function(){
		hideSelectDiv();
		$("#wt-city-title").text(city_select_show_clear?"无":"请选择城市");
		$("a[name=city-display][class*=current]").removeClass("current");
		
		// 没有回调函数
		if (!city_select_callback) {
			return;
		}
		
		// 回调函数未定义
		try {
			eval(city_select_callback);
		} catch(e) {
			alert('回调函数未定义！');
			return;
		}
		eval(city_select_callback + "()");
	});
	
	$("#city_all").click(function(){
		hideSelectDiv();
		$("#wt-city-title").text(city_select_show_all?"全部":"请选择城市");
		$("a[name=city-display][class*=current]").removeClass("current");
		
		// 没有回调函数
		if (!city_select_callback) {
			return;
		}
		
		// 回调函数未定义
		try {
			eval(city_select_callback);
		} catch(e) {
			alert('回调函数未定义！');
			return;
		}
		eval(city_select_callback + "()");
	});
	
	// 根据首字母，选中字母标签
	function showSpell(key) {
		if (key) {
			key = key.toUpperCase();
			if ($("#city-con" + key).length > 0) {
				$("div[id^='city-con']:visible").hide();
				$("#city-con" + key).show();
				
				$("a[name='city-spell'][class*='current']").removeClass("current");
				$("#city-spell" + key).addClass("current");
			}
		}
	}
	
	function showSelectDiv() {
		$("#city-border-mid").addClass("city-border-mid");
		$("#city-border-left").show();
		$("#city-border-right").show();
		$("#wt-city-list-new").show();
		city_select_showed = !city_select_showed;
	}
	
	function hideSelectDiv() {
		$("#city-border-mid").removeClass("city-border-mid");
		$("#city-border-left").hide();
		$("#city-border-right").hide();
		$("#wt-city-list-new").hide();
		$("a[name=city-display][class*=hover]").removeClass("hover");
		city_select_showed = !city_select_showed;
	}
});