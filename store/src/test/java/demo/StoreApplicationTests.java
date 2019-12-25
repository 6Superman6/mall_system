package demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class StoreApplicationTests {

	@Test
	void contextLoads() {
		String s="123456";
//		String s6 = DigestUtils.md5DigestAsHex(s.getBytes());
//		System.out.println(s6);
	}

}
