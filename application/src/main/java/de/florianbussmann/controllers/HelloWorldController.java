package de.florianbussmann.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.florianbussmann.models.HelloWorldResponse;
import de.florianbussmann.temporal.HelloWorldWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

	@Autowired
	private WorkflowClient workflowClient;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<HelloWorldResponse> getHello(
			@RequestParam(name = "name", defaultValue = "world") final String name) {
		logger.info("I am running!");

		String result = workflowClient
				.newWorkflowStub(HelloWorldWorkflow.class,
						WorkflowOptions.newBuilder().setTaskQueue("sap-queue").setWorkflowId("say-hello").build())
				.sayHello(name);
		logger.info("Workflow result: " + result);

		return ResponseEntity.ok(new HelloWorldResponse(name));
	}
}
