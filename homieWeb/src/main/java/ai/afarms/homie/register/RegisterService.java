package ai.afarms.homie.register;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.afarms.homie.farm.Farm;
import ai.afarms.homie.repository.NicknameRepository;
import ai.afarms.homie.repository.UserRepository;
import ai.afarms.homie.shippingAddress.ShippingAddress;
import ai.afarms.homie.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RegisterService {
	private UserRepository userRepository;
	private NicknameRepository nicknameRepository;
	private JavaMailSender javaMailSender;
	
	public RegisterService(UserRepository userRepository, NicknameRepository nicknameRepository, JavaMailSender javaMailSender){
		this.userRepository = userRepository;
		this.nicknameRepository = nicknameRepository;
		this.javaMailSender = javaMailSender;
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	public String makeValidCode() {
		char[] set = new char[] {
				'0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'A', 'B',
				'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
				'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
		StringBuffer pwd = new StringBuffer();
		SecureRandom pr = new SecureRandom();
		pr.setSeed(new Date().getTime());
		
		int idx = 0;
		int len =set.length;
		
		for (int i=0; i<8; i++) {
			idx=pr.nextInt(len);
			pwd.append(set[idx]);
		}
		
		return pwd.toString();
	}
	
	public void sendAuthEmail(User user) {
		MimeMessage msg = javaMailSender.createMimeMessage();
		try {
			msg.setSubject("에이팜스 - 호미 이메일 인증 안내","utf-8");
			msg.setFrom("afarms@afarms.ai");
			try {
				msg.setContent("<div style=\"font-family: 'Cereal',Helvetica,Arial,sans-serif; font-weight: normal; font-size: 1.1rem; line-height: 1.8rem; letter-spacing: -.05rem; width: 100%; height: 100%; display: flex; justify-content: center; align-items: center; padding: 1rem; box-sizing: border-box; word-break: keep-all;\">\r\n"
						+ "    <div style=\"\">\r\n"
						+ "        <a href=\"http://homie.afarms.ai\" target=\"_blank\"><img src=\"https://homie.afarms.ai/assets/img/brand/logo_green.jpg\" alt=\"HOMIE\" style=\"width: 130px; border: 0;\"></a>\r\n"
						+ "        <div style=\"\">\r\n"
						+ "            <div style=\"margin-top: .5rem;\"><span th:text=\"${name}\"></span><strong>" + user.getName() + "님, 안녕하세요.</strong></div>\r\n"
						+ "            <div style=\"margin-top: .5rem;\">호미에 오신 것을 환영합니다! 인증하시려면 이메일 주소를 인증해 주세요.</div>\r\n"
						+ "            <div style=\"\"><a href=\"https://homie.afarms.ai/authentication?id="  + URLEncoder.encode(user.getId(),"UTF-8") + "&&val=" + URLEncoder.encode(user.getValid_code(),"UTF-8") + "\"" + "target=\"_blank\" style=\"font-family: 'Cereal',Helvetica,Arial,sans-serif; font-weight: normal; margin: 0; text-align: left; line-height: 1.3; color: #ffffff; text-decoration: none; background-color: #039b13; border-radius: 4px; display: inline-block; padding: 12px 24px 12px 24px; margin: 1rem 0;\">이메일 인증하기</a></div>\r\n"
						+ "            <div style=\"\">\r\n"
						+ "                감사합니다.<br>\r\n"
						+ "                호미 드림\r\n"
						+ "            </div>\r\n"
						+ "            <div style=\"font-size: .8rem; line-height: 1.2rem; padding-top: 1.5rem; border-top: 1px solid #e7e7e7; margin-top: 1.5rem;\">\r\n"
						+ "                제주특별자치도 제주시 건입동 산지로 25 3F <br>\r\n"
						+ "                FAX : 02-312-0118 TEL : 02-312-0117 afarms@afarms.ai<br>\r\n"
						+ "                <div style=\"\">© 2021 Copyright afarms</div>\r\n"
						+ "            </div>\r\n"
						+ "        </div>\r\n"
						+ "    </div>\r\n"
						+ "</div>\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "","text/html; charset=UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getId()));
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Mono<Integer> mapToUser(Register register){
		return userRepository.findById(register.getId())
				.map(Exist -> {
					return 0;
				})
				.defaultIfEmpty(1)
				.filter(i -> i==1)
				.map(users-> {
					List<Farm> farm = new ArrayList<>();
					List<ShippingAddress> addr = new ArrayList<>();
					
					ShippingAddress shippingAddress = new ShippingAddress();
					shippingAddress.setName(register.getName());
					shippingAddress.setAddr(register.getZip() + register.getAddress());
					shippingAddress.setAddr_spec(register.getAddress_detail());
					shippingAddress.setRec_name(register.getName());
					shippingAddress.setRec_phone(register.getPhone());
					
					addr.add(shippingAddress);
					
					randomNickname().map(nickname -> {
						User user = new User(register.getId(), register.getName(),
								passwordEncoder().encode(register.getPw()), register.getPhone(), 
								farm, addr, false, nickname.getNickname(), false, makeValidCode(), false);
						
						if(register.getId().contains("minervasoft") || register.getId().contains("abrain") || register.getId().contains("afarms"))
							user.setMembership(true);
							
						this.nicknameRepository.deleteById(nickname.getId()).subscribe();
						userRepository.save(user).subscribe();
						sendAuthEmail(user);
						return nickname;
					}).subscribe();
					
					return 1;
				})
				.defaultIfEmpty(0);

				
	}
	
	public Flux<Nickname> randomNickname(){
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);

		Pageable page = PageRequest.of(rand.nextInt(9600), 1);
		return nicknameRepository.findFirstByNicknameNotNull(page);
	}
}
