// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

let resultPieLabel = [];
let resultPieData = [];


$.ajax({
	url : 'administrator/getDogType',
	type : 'post',
	dataType : 'json',
	success : function(result){
		console.log(result);
		// 오늘 날짜 - 7일 사이의 가입자 수 받아오기
		for(let i = 0; i < result.length; i++){
			resultPieLabel.push(result[i].petBreed);
			resultPieData.push(result[i].petBreedCount);
		}
		console.log(resultPieLabel);
		console.log(resultPieData);
		pieChart();
	},
	error : function(e){
		alert('차트 데이터 로딩 실패');
		console.log(JSON.stringify(e));
	}
});

function pieChart(){
	var ctx = document.getElementById("myPieChart");
	var myPieChart = new Chart(ctx, {
	  type: 'pie',
	  data: {
	    labels: resultPieLabel,
	    datasets: [{
	      data: resultPieData,
	      backgroundColor: 
	      	[
			'#007bff', '#dc3545', '#ffc107', '#28a745', '#a349a4', 
	      	'#d7ffd0', '#ffdebf', '#f7d7f7','#000000','#4ebdf2',
	      	'#d9ff63', '#ef6bf0', '#756bf0', '#6be6f0', '#c9adc8',
	      	'#bc88ff'
	      	], // 16개 색 필요?
	    }],
	  },
	});
}