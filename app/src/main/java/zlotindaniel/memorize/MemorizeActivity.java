package zlotindaniel.memorize;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MemorizeActivity extends Activity {

	private MemorizeView view;
	private MemorizeInteractor interactor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new MemorizeView(this);
		interactor = new MemorizeInteractor(view, ((MemorizeApplication) getApplication()).getDataLoader());
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				interactor.onClick();
			}
		});

		setContentView(view);

		interactor.start();
	}
}
