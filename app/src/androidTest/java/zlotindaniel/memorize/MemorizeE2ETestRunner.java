package zlotindaniel.memorize;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class MemorizeE2ETestRunner extends AndroidJUnitRunner {
	@Override
	public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return Instrumentation.newApplication(MemorizeE2EApplication.class, context);
	}
}
