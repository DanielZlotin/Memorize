package zlotindaniel.memorize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import zlotindaniel.memorize.android.login.LoginActivity;

public class BaseActivity extends Activity {

	public Config config;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ((MemorizeApplication) getApplication()).getConfig();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!(this instanceof LaunchActivity) && !(this instanceof LoginActivity)) {
			if (!LoginActivity.isSignedIn()) {
				startActivity(new Intent(this, LaunchActivity.class));
				finish();
			}
		}
	}
}
