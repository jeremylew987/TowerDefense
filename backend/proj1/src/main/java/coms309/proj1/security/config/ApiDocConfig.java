package coms309.proj1.security.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfig
{
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.pathsToMatch("/**")
				.build();
	}
}
