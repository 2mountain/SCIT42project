// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

window.onload = function(){
	barChartData();
	pieChartData();
	
}
// 일자
let resultLabel = [];
let resultData = [];

function barChartData(){
		$.ajax({
			url : 'administrator/getJoinNumber',
			type : 'post',
			dataType : 'json',
			success : function(result){
				// 오늘 날짜 - 7일 사이의 가입자 수 받아오기
				for(let i = 0; i < result.length; i++){
					resultLabel.push(result[i].joinDate);
					resultData.push(result[i].memberJoinCount);
				}
			},
			error : function(e){
				alert('차트 데이터 로딩 실패');
				console.log(JSON.stringify(e));
			}
		});
	}

// Bar Chart Example
var ctx = document.getElementById("myBarChart");
var myLineChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: resultLabel, // 
    datasets: [{
      label: "가입자 수",
      backgroundColor: "rgba(2,117,216,1)",
      borderColor: "rgba(2,117,216,1)",
      data: resultData,
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'day'
        },
        gridLines: { // X축 라인
          display: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: 10,
          maxTicksLimit: 5
        },
        gridLines: { // Y축 라인
          display: true
        }
      }],
    },
    legend: { // 차트 설명
      display: false
    }
  }
});
