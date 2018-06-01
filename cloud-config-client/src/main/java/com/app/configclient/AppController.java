package com.app.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
	@Value ("${encryptedvalue}")
	private String encryptedvalue;
	@Value ("${cannotoverrride}")
	private String cannotoverrride;
	@Value ("${allowappoverride}")
	private String allowappoverride;




	@RequestMapping("/action")
	public Map<String, String> action() {
		Map<String, String> result = new HashMap<>();

		result.put("authorName", authorName);
		result.put("authorSurname", authorSurname);
		result.put("encryptedvalue", encryptedvalue);
		result.put("allowappoverride", allowappoverride);
		result.put("cannotoverrride", cannotoverrride);

		return result;
//		return authorName + " " + authorSurname + " " + key + " " + encryptedKey + " " + cannotoverride;
	}

}
