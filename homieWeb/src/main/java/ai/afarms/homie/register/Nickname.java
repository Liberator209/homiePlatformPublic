package ai.afarms.homie.register;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "nickname")
public class Nickname {
	@Id
	private String id;
	private String nickname;
}
