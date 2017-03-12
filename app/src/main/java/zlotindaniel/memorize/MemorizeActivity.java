package zlotindaniel.memorize;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MemorizeActivity extends Activity {

	private MemorizeView view;
	private MemorizeController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new MemorizeView(this);
		controller = new MemorizeController(view);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.onClick();
			}
		});

		setContentView(view);
	}
}
