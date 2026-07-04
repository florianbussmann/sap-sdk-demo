package de.florianbussmann.temporal;

import org.springframework.stereotype.Component;

import io.temporal.spring.boot.ActivityImpl;

@Component
@ActivityImpl(taskQueues = "sap-queue")
public class HelloWorldActivityImpl implements HelloWorldActivity {

	@Override
	public String greet(String name) {
		return String.format("Hello, %s.", name);
	}

}
