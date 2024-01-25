package com.example.restapiadvcdbdemo.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningController {
	
	@GetMapping(path="/v1/person")
	public PersonV1 getPersonUrlVersioning1() {
		return new PersonV1("Vamshi Krishna");
	}
	
	@GetMapping(path="/v2/person")
	public PersonV2 getPersonUrlVersioning2() {
		return new PersonV2(new Name("Vamshi", "Krishna"));
	}
	
	@GetMapping(path="/person/reqParam", params="version=1")
	public PersonV1 getPersonReqParamVersioning1() {
		return new PersonV1("Vamshi Krishna");
	}
	
	@GetMapping(path="/person/reqParam", headers="version=2")
	public PersonV2 getPersonReqParamVersioning2() {
		return new PersonV2(new Name("Vamshi", "Krishna"));
	}
	
	@GetMapping(path="/person/headers", headers="X-API-VERSION=1")
	public PersonV1 getPersonHeaderVersioning1() {
		return new PersonV1("Vamshi Krishna");
	}
	
	@GetMapping(path="/person/headers", headers="X-API-VERSION=2")
	public PersonV2 getPersonHeaderVersioning2() {
		return new PersonV2(new Name("Vamshi", "Krishna"));
	}
	
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v1+json")
	public PersonV1 getPersonAcceptVersioning1() {
		return new PersonV1("Vamshi Krishna");
	}
	
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")
	public PersonV2 getPersonAcceptVersioning2() {
		return new PersonV2(new Name("Vamshi", "Krishna"));
	}
}
