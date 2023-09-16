package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RestHandler {
	
	String authToken = null;
	
		
	final String getAuthToken() {
		return authToken;
	}


	final void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
		
	@PostMapping("/logins")
    public Object logins(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password ) {
        
    String loginUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

    HttpHeaders loginHeaders = new HttpHeaders();
    loginHeaders.set("Content-Type", "application/json");

    String loginRequestBody;
    
    if ("test@sunbasedata.com".equals(userId) && "Test@123".equals(password)) {
        // Successful login
        // Create a JSON request body for login
        loginRequestBody = "{\"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\"}";

        HttpEntity<String> loginEntity = new HttpEntity<>(loginRequestBody, loginHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> loginResponseEntity = restTemplate.exchange(
            loginUrl,
            HttpMethod.POST,
            loginEntity,
            String.class
        );

        if (loginResponseEntity.getStatusCode() == HttpStatus.OK) {
            // Extract and store the authorization token
            authToken = loginResponseEntity.getBody();
            setAuthToken(authToken);
            
            // Redirect to a success page
            return new RedirectView("/success");
        } else {
            // Handle non-OK status codes if needed
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    // Redirect to the login page with an error message
    return new RedirectView("/error");
    }
	
//	
	
	
	@GetMapping("/userLists")
    public String getUserLists() throws JsonMappingException, JsonProcessingException {
		String authToken=getAuthToken();
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("authToken_1");
		JsonNode jsonNode = objectMapper.readTree(authToken);
		System.out.println("authToken");
		authToken = jsonNode.get("access_token").asText();
		System.out.println(authToken);
		
		if(authToken == null) {
            // Handle the case where the token is not available
            return "Authorization token not available. Please log in first.";
        }

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+authToken);
        // Add any other headers as needed

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
            apiUrl,
            HttpMethod.GET,
            entity,
            String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Error occurred: " + responseEntity.getStatusCode();
        }
    }
	
	
	
	@PostMapping("/postUser")
    public Object postUser(@RequestBody Users user) throws JsonMappingException, JsonProcessingException {
		String authToken=getAuthToken();
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("authToken_1");
		JsonNode jsonNode = objectMapper.readTree(authToken);
		System.out.println("authToken");
		authToken = jsonNode.get("access_token").asText();
		System.out.println(authToken);
		
		if(authToken == null) {
            // Handle the case where the token is not available
            return "Authorization token not available. Please log in first.";
        }
		
		

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
        
        System.out.println(user);
        
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+authToken);
        // Add any other headers as needed

        HttpEntity<?> requestEntity = new HttpEntity<>(user,headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            requestEntity,
            String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
        	return responseEntity.getBody();
        }
        else if(responseEntity.getStatusCode() == HttpStatus.CREATED){
        	return responseEntity.getBody();
        }
        else {
            return "Error occurred: " + responseEntity.getStatusCode();
        }
    }
	
	
	@PostMapping("/updateUser")
    public Object updateUser(@RequestBody Users user,String uuid) throws JsonMappingException, JsonProcessingException {
		String authToken=getAuthToken();
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("authToken_1");
		JsonNode jsonNode = objectMapper.readTree(authToken);
		System.out.println("authToken");
		authToken = jsonNode.get("access_token").asText();
		System.out.println(authToken);
		
		if(authToken == null) {
            // Handle the case where the token is not available
            return "Authorization token not available. Please log in first.";
        }
		
		

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=uuid_value";
        
        apiUrl=apiUrl.replace("uuid_value",uuid);
        
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+authToken);
        // Add any other headers as needed

        HttpEntity<?> requestEntity = new HttpEntity<>(user,headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            requestEntity,
            String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
        	return responseEntity.getBody();
        }
        else if(responseEntity.getStatusCode() == HttpStatus.CREATED){
        	return responseEntity.getBody();
        }
        else {
            return "Error occurred: " + responseEntity.getStatusCode();
        }
    }
	
	
	
	
	
	@PostMapping("/delUser")
    public String delUser(String uuid) throws JsonMappingException, JsonProcessingException {
		String authToken=getAuthToken();
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("authToken_1");
		JsonNode jsonNode = objectMapper.readTree(authToken);
		System.out.println("authToken");
		authToken = jsonNode.get("access_token").asText();
		System.out.println(authToken);
		
		if(authToken == null) {
            // Handle the case where the token is not available
            return "Authorization token not available. Please log in first.";
        }
		
		

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=uuid_value";
        
        apiUrl=apiUrl.replace("uuid_value",uuid);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+authToken);
        // Add any other headers as needed

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            entity,
            String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Error occurred: " + responseEntity.getStatusCode();
        }
    }
	
	
	//@PostMapping("/login")
//  public ResponseEntity<String> login( @RequestParam("userId") String userId,
//          @RequestParam("password") String password) {
//      String loginUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
//
//      HttpHeaders loginHeaders = new HttpHeaders();
//      loginHeaders.set("Content-Type", "application/json");
//      
//      String loginRequestBody;
//      
//      if ("test@sunbasedata.com".equals(userId) && "Test@123".equals(password)) {
//          // Successful login
//      	 // Create a JSON request body for login
//          loginRequestBody = "{\"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\"}";
//          
//      } else {
//          // Handle failed login
//          return ResponseEntity.badRequest().body("Login failed");
//      }
//
//      HttpEntity<String> loginEntity = new HttpEntity<>(loginRequestBody, loginHeaders);
//
//      RestTemplate restTemplate = new RestTemplate();
//
//      ResponseEntity<String> loginResponseEntity = restTemplate.exchange(
//          loginUrl,
//          HttpMethod.POST,
//          loginEntity,
//          String.class
//      );
//
//      if (loginResponseEntity.getStatusCode() == HttpStatus.OK) {
//          // Extract and store the authorization token
//          authToken = loginResponseEntity.getBody();
//          setAuthToken(authToken);
//          
//          ModelAndView modelAndView = new ModelAndView("success");
//          modelAndView.addObject("message", "Login successful");
//        
//      } else {
//      	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//      }
//
//      return loginResponseEntity;    }
	
}


