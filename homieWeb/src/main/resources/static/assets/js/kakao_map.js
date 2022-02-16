var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		  mapOption = { 
		  center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		  level: 9 // 지도의 확대 레벨
};

var map = new kakao.maps.Map(mapContainer, mapOption);
var geocoder = new kakao.maps.services.Geocoder();		

var farmLoc = document.getElementById('farm_loc').textContent;
var farmOwner = document.getElementById('farm_owner').textContent;

geocoder.addressSearch(farmLoc, function(result, status) {

	if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+ farmOwner +'농장</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
        
        }
	});




// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
// marker.setMap(null); 