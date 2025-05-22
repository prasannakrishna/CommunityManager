package com.bhagwat.retail.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.bhagwat.retail")
@EnableJpaRepositories(basePackages = "com.bhagwat.retail.community.repository")
@ComponentScan(basePackages = {
		"com.bhagwat.retail.community"
})
public class CommunityManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityManagerApplication.class, args);
	}

}
