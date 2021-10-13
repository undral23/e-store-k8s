package edu.miu.sa.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

	@GetMapping("/serviceFallBack")
	public String serviceFallBackBackMethod() {
		return "Requested Service is taking longer than Expected." + " Please try again later";
	}
}
