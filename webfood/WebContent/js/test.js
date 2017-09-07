/*获取json数据开始*/
//定义变量
var memberAll;
var jsonXData = [];
var jsonyD1 = [];
var jsonyD2 = [];
$(function (){

	//获取数据
	$.ajax({ 
		url:'test',
		type:"POST",
		dataType:"JSON",
		cache: false,
		async: false,
		success: function (data) {
			memberAll=data.obj;
			for(var i=0;i<memberAll.length;i++){

				var Year = memberAll[i].year;
				var ShouldPay = memberAll[i].shouldPay;
				var TruePay = memberAll[i].truePay;
				jsonXData.push(Year); //赋值
				jsonyD1.push(ShouldPay);
				jsonyD2.push(TruePay);
			}


			var chart;
			chart = new Highcharts.Chart({
				chart: {
					renderTo: 'containerliuliang',//放置图表的容器
					plotBackgroundColor: null,
					plotBorderWidth: null,
					defaultSeriesType: 'column'   //图表类型line, spline, area, areaspline, column, bar, pie , scatter 
				},
				title: {
					text: '近年会费缴纳情况',
					style: { font: 'normal 13px 宋体' }
				},
				xAxis: {//X轴数据
					categories: jsonXData,
					lineWidth: 2,
					labels: {
						rotation: -45, //字体倾斜
						align: 'right',
						style: { font: 'normal 13px 宋体' }
					}
				},
				yAxis: {//Y轴显示文字
					lineWidth: 2,
					title: {
						text: '金额/万元'
					}
				},
				tooltip: {
					formatter: function () {
						return '<b>' + this.x + '</b><br/>' +
						this.series.name + ': ' + this.y+'万元';
					}
				},
				plotOptions: {
					column: {
						dataLabels: {
							enabled: true
						},
						enableMouseTracking: true//是否显示title
					}
				},
				series: [{
					name: '应缴',
					data: jsonyD1
				}, {
					name: '实缴',
					data: jsonyD2
				}]
			});
			//$("tspan:last").hide(); //把广告删除掉
		} //if

	});

});

