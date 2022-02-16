package ai.afarms.homie.order;

import lombok.Data;

@Data
public class Track {
	private String url;
	private int status;
	
	public Track(String url, int status) {
		this.url = url;
		this.status = status;
	}

	public Track() {
		this.url = "1";
		this.status = 0;
	}
}
