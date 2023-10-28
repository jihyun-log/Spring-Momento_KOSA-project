//공통: MomentoApplication 클래스는 스프링 부트 어플리케이션의 진입점이며, 해당 어플리케이션을 실행하기 위해 사용됩니다.

package com.momento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MomentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentoApplication.class, args);
	}

}
