package com.jbote;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author jobert
 *
 */
@Configuration
public class ChatConfig {
	@Bean
	public ChatClient chatClient(ChatClient.Builder builder) {
		return builder.build();
	}
}
