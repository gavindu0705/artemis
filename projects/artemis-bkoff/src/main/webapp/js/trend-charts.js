	function createHighChart(renderId, data) {
	    var chart = new Highcharts.Chart({
			credits:{
				enabled:false,
				text: '房价网',
		        href: Constants.cityUrl,
				position: {
					align: 'center',
					verticalAlign: 'top',
					x: 0,	
					y: 95
				},
				itemStyle: {
					cursor: 'pointer'
				}
			},
	        chart: {
	            renderTo: renderId,
	            type: 'spline'
	        },
	        title: {
	            text: data.title,
	            floating: false,
	            align: 'center',
	            x : 0,
	            y : 10
	        },
	        legend: {
				enabled: true,
	            align: 'center',
	            backgroundColor: '#fff',
	            borderColor: '#fff',
	            borderWidth: 2,
	            layout: 'horizontal',
	            verticalAlign: 'top',
	            y: 20,
	            x: 240
			},
			xAxis: {
	            labels: {
	                formatter: function() {
	                    return Highcharts.dateFormat('%y年%m月', this.value);
	                }
	            }
	        },
	        yAxis: {
	            title: {
	        		enable: false,
	                text: ''
	            },
	            offset : -35,
	            labels: {
	                formatter: function() {
	                    return this.value + "元";
	                }
	            }
	        },
	        series: data.series,
	        tooltip: {
	            formatter: function() {
					return  Highcharts.dateFormat('%Y-%m-%d', this.x) +'<br>'+ this.series.name+ ": <span style='color:#F50;font-weight: bold;'>"+ this.y +'</span>元/平米';
	            }
	        }
	    });
	}
	
	function createHighChartFragment(renderId, data) {
	    var chart = new Highcharts.Chart({
			credits:{
				enabled:false
			},
	        chart: {
	            renderTo: renderId,
	            type: 'spline'
	        },
	        title: {
	        	text: data.title,
	            floating: false,
	            align: 'center',
	            x : 0,
	            y : 5,
	            style: {
	                fontSize : '12px'
	            }
	        },
	        legend: {
				enabled: false
			},
			xAxis: {
	            labels: {
	                formatter: function() {
	                    return Highcharts.dateFormat('%Y-%m', this.value);
	                }
	            }
	        },
	        yAxis: {
	            title: {
	        		enable: false,
	                text: ''
	            },
	            offset : -30,
	            labels: {
	                formatter: function() {
	                    return this.value;
	                }
	            }
	        },
	        series: data.series,
	        tooltip: {
	            formatter: function() {
					return  Highcharts.dateFormat('%Y-%m-%d', this.x) +'<br>'+ this.series.name+ ": <span style='color:#F50;font-weight: bold;'>"+ this.y +'</span>元/平米';
	            }
	        }
	    });
	}

	function createStockChart(renderId, data) {
	    window.chart = new Highcharts.StockChart({
			chart : {
	            renderTo : renderId,
	            type: 'spline'
	        },
			credits:{
				enabled:true,
				text: '房价网',
		        href: Constants.cityUrl,
				position: {
					align: 'center',
					verticalAlign: 'top',
					x: 0,
					y: 95
				},
				itemStyle: {
					cursor: 'pointer'
				}
			},
	        rangeSelector : {
	            enabled : false
	        },
	        legend: {
				enabled: true,
	            align: 'center',
	            backgroundColor: '#fff',
	            borderColor: '#fff',
	            borderWidth: 2,
	            layout: 'horizontal',
	            verticalAlign: 'top',
	            y: 20,
	            x: 240
			},
	        xAxis: {
	            labels: {
	                formatter: function() {
	                    return Highcharts.dateFormat('%y年%m月', this.value);
	                }
	            }
	        },
	        yAxis: {
	            labels: {
	                formatter: function() {
	                    return this.value + "元";
	                }
	            }
	        },
			tooltip: {
	            formatter: function() {
	            	var s = Highcharts.dateFormat('%Y-%m-%d', this.x) + "<br>";
	                $.each(this.points, function(i, point) {
	            		s = s + point.series.name + ": <span style='color:#F50;font-weight: bold;'>" + point.y + "</span>元/平米<br>";
	                });
	                return s;
	            }
	        },
	        title: {
	            text: data.title,
	            floating: true,
	            align: 'left',
	            x: 300,
	            y: 17
	        },
			series: data.series,
	        scrollbar : {
	        	enabled : false
	        },
	        navigator : {
	        	enabled : false
	        }
	    });
	}
	
	function createChart(renderId, data) {
		if(data == null || data == 'undefined' || typeof(data) == undefined) {
			return;
		}
		if(data.series == 'undefined' || data.series.length == 0) {
			return;
		}
		
		if(data.series[0].data.length >= 54) {
			createStockChart(renderId, data);
		}else {
			createHighChart(renderId, data);
		}
	}
	
	function createChartFragment(renderId, data) {
		if(data == null || data == 'undefined' || typeof(data) == undefined) {
			return;
		}
		if(data.series == 'undefined' || data.series.length == 0) {
			return;
		}
		if(data.series[0].data.length >= 54) {
			createStockChart(renderId, data);
		}else {
			createHighChartFragment(renderId, data);
		}
	}


