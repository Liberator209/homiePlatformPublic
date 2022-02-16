function toModal(on, fn, fr) {
		document.getElementById("modal_title").innerHTML=on + " " + fn + " 농장을 보고 계십니다.";
				var video = document.getElementById('video-player');
				var videoSrc = fr + '-1/index.m3u8';
											//
											// First check for native browser HLS support
											//
				if (Hls.isSupported()) {
					var hls = new Hls();
									hls.loadSource(videoSrc);
									hls.attachMedia(video);
					}
					
				else if (video.canPlayType('application/vnd.apple.mpegurl')) {
					video.src = videoSrc;
				}
};
