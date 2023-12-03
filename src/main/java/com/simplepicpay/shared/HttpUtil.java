package com.simplepicpay.shared;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplepicpay.exception.ResponseMessage;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class HttpUtil {
    @Autowired
    private ObjectMapper objectMapper;

	private List<String> freePatterns = List.of(
			"/api/login", 
			"/h2-console",

			"/v1",
			"/api/v1",
			"/v2",
			"/api/v2",
			"/v3",
			"/api/v3",
			"/configuration",
			"/swagger",
			"/webjars",
			"/api-docs",
			"/favicon.ico",
			"/swagger-ui.html",

			"/css", 
			"/icons", 
			"/images",
			"/js", 
			"/plugins", 
			"/image");

    public boolean isFreeToNavigate(String pattern) {
		return this.freePatterns.stream().filter(p -> pattern.startsWith(p)).count() > 0;
    }

	public void sendResponseMessage(HttpServletResponse response, int responseCode, 
			String responseBody) throws IOException {
	    response.addHeader("Content-Type", "application/json;charset=UTF-8");
	    response.setStatus(responseCode);
	    ResponseMessage responseMessage = new ResponseMessage(responseCode, responseBody);
	    objectMapper.writeValue(response.getOutputStream(), responseMessage);
	    response.flushBuffer();			
	}
}
