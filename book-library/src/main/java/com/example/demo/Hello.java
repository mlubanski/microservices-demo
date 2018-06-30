package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/hello", produces = {"application/json"})
public class Hello {

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, String> hey() {
		Map<String, String> result = new HashMap<>();
		result.put("message", "Hi from BookLibrary Demo!");
		return result;
	}
}
