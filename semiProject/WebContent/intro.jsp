<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>레시피</title>
	<link rel="stylesheet" href="home.css">
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=285177e645b698276895abf188247b00"></script>
</head>
<body>
	<div id="divPage">
		<div style="text-align: center;">
			<a href="/semiProject/index.jsp"> <img src="로고1.png" style="width: 200px; padding: 15px;"></a>
		</div>
		<div id="divMenu"><jsp:include page="menu.jsp" /></div>
		<div id="divHeader"><h2>회 사 소 개</h2></div>
		<!-- 여기부터 내용출력 시작 -->
		<div style="text-align: center; border:2px solid #F16E88; width: 1600px; height: 900px; margin:auto;">
			<img src="intro.png" style="width: 1600px; height: 900px;">
		</div>
		<br><br>		
		<div id="divHeader"><h4>찾아오시는 길</h4></div>
			<div id="map" style="width: 1000px; height: 500px;margin:auto; margin-bottom: 35px; border:2px solid #F16E88;"></div>
			<script>
				var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
				var options = { //지도를 생성할 때 필요한 기본 옵션
					center : new kakao.maps.LatLng(37.438862, 126.675115), //지도의 중심좌표.
					level : 3
				//지도의 레벨(확대, 축소 정도)
				};
				var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
				var marker = new kakao.maps.Marker({
					position : new kakao.maps.LatLng(37.438862, 126.675115)
				});
				marker.setMap(map);

				var str = "<div st>" + place + "<br>" + phone + "</div>";
				var info = new kakao.maps.InfoWindow({
					content : str
				});
				kakao.maps.event.addListener(marker, "mouseover", function() {
					info.open(map, marker);
				});
				kakao.maps.event.addListener(marker, "mouseout", function() {
					info.close(map, marker);
				});
			</script><br>
		<div id="divFooter"><jsp:include page="footer.jsp" /></div>
		<!-- 여기부터 내용출력 종료 -->
	</div>
</body>
</html>