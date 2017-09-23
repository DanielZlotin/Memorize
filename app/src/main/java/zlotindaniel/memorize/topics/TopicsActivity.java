package zlotindaniel.memorize.topics;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class TopicsActivity extends Activity {

	private TopicsView view;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Select A Topic");
		view = new TopicsView(this);
		setContentView(view);
	}
}
