package com.kakao.cafe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CafeApplicationTest {

	@Test
	void contextLoads() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(CafeApplication.class);
		assertThat(ac).isNotNull();
	}
}
