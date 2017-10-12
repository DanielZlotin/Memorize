package zlotindaniel.memorize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import zlotindaniel.memorize.extern.topics.TopicsActivity;

public class LaunchActivity extends BaseActivity {
	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, TopicsActivity.class));
		finish();
	}
}
