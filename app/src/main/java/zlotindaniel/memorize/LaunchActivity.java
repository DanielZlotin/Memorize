package zlotindaniel.memorize;

import android.content.*;
import android.os.*;
import android.support.annotation.*;

import zlotindaniel.memorize.android.usecase.login.*;

public class LaunchActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
}
