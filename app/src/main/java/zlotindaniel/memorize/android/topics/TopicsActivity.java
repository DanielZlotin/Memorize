package zlotindaniel.memorize.android.topics;

import android.os.*;
import android.support.annotation.*;
import android.view.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.topics.*;

public class TopicsActivity extends BaseActivity {
	private TopicsView view;
	private TopicsInteractor interactor;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new TopicsView(this);
		setContentView(view);
		interactor = new TopicsInteractor(getUserDetails(), view, new DatabaseService(config.debug, getUserId(), config.database));
	}

	@Override
	protected void onResume() {
		super.onResume();
		interactor.start();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		view.onCreateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		view.onMenuItemClicked(item);
		return super.onOptionsItemSelected(item);
	}
}
