function createChart(data) {
    var chart = new Highcharts.Chart({
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
        chart: {
            renderTo: 'container',
            type: 'spline'
        },
        title: {
            text: data.title,
            floating: true,
            align: 'left',
            x: 350,
            y: 17
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
            title: {
        		enable: false,
                text: ''
            },
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

