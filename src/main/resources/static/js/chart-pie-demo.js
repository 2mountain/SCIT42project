// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

let resultLabel = [];
let resultData = [];

window.onload = function(){
	pieChartData();
}

function pieChartData(){
	$.ajax({
			url : 'administrator/getDogType',
			type : 'post',
			dataType : 'json',
			success : function(result){
				// 오늘 날짜 - 7일 사이의 가입자 수 받아오기
				for(let i = 0; i < result.length; i++){
					resultLabel.push(result[i].joinDate);
					resultData.push(result[i].memberJoinCount);
				}
				console.log(resultLabel);
				console.log(resultData);
				
				/*
				$(function(){
				    $("#myBarChart").load("administrator/administrator.html");
				});
				*/
			},
			error : function(e){
				alert('차트 데이터 로딩 실패');
				console.log(JSON.stringify(e));
			}
		});
}

// Pie Chart Example
var ctx = document.getElementById("myPieChart");
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: ["Blue", "Red", "Yellow", "Green"],
    datasets: [{
      data: [12.21, 15.58, 11.25, 8.32],
      backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745'],
    }],
  },
});
