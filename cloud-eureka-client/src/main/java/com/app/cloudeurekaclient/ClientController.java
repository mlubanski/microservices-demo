package com.app.cloudeurekaclient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@RequestMapping("/data")
	public String info() {
		return "some data";
	}
}
