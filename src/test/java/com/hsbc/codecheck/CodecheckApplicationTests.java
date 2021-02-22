package com.hsbc.codecheck;

import com.hsbc.codecheck.controller.CodeCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CodecheckApplicationTests {

	@Autowired
	private CodeCheckController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
