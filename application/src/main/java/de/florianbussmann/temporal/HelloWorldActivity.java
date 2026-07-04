package de.florianbussmann.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface HelloWorldActivity {

	@ActivityMethod
	abstract String greet(String name);

}
