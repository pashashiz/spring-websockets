package com.ps.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SpringBootApplication
public class SpringWebsocketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebsocketsApplication.class, args);
	}

	@Configuration
    @EnableWebSocketMessageBroker
    public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

        @Override
        public void configureMessageBroker(MessageBrokerRegistry config) {
            config.enableSimpleBroker("/topic");
            config.setApplicationDestinationPrefixes("/app");
        }

        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/websocket").withSockJS();
        }
    }

	@RestController
	public static class MainController {

        @RequestMapping(value = "/test", method = GET)
        public String getDocument() {
            return "test";
        }

	    @MessageMapping("/hello")
        @SendTo("/topic/messages")
		public GeneralMessage greeting(HelloMessage message) throws Exception {
		    Thread.sleep(1000);
		    return new GeneralMessage("Hello, " + message.getName() + "!");
        }

	}
}
