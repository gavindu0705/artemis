function createChart(data) {
    window.chart = new Highcharts.StockChart({
		chart : {
            renderTo : 'container',
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
                    return Highcharts.dateFormat('%y年%m月', this.value)
                }
            }
        },
        yAxis: {
            labels: {
                formatter: function() {
                    return this.value + "元"
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
