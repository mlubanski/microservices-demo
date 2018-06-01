package com.app.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //Wstrzykniecie aktualnej wartosci, endpoint /actuator/refresh
//refresh mozna wywolac przez:
// - orkiestracje (np. mesos sam to ogarnia!)
// - git hook + curl
// - plugin gita + curl
public class AppController {
	
	@Value ("${author.name}")
	private String authorName;
	@Value ("${author.surname}")
	private String authorSurname;
	@Value ("${app.specific.config.key}")
	private String key;
	@Value ("${app.specific.config.encrypted.key}")
	private String encryptedKey;



	@RequestMapping("/action")
	public String action() {
		
		return authorName + " " + authorSurname + " " + key + " " + encryptedKey;
//		return "hello";
	}

}
