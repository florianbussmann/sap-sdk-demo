package de.florianbussmann.temporal;

import java.time.Duration;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl(taskQueues = "sap-queue")
public class HelloWorldWorkflowImpl implements HelloWorldWorkflow {

	private final HelloWorldActivity activity = Workflow.newActivityStub(HelloWorldActivity.class,
			ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build());

	@Override
	public String sayHello(String name) {
		return activity.greet(name);
	}

}
