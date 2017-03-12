package zlotindaniel.memorize;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MemorizeView extends FrameLayout implements MemorizeController.Controlled {

	private final TextView textView;

	public MemorizeView(Context context) {
		super(context);
		textView = new TextView(context);
		textView.setText("Phrase 1");
		addView(textView, new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, CENTER));
	}

	@Override
	public void show(String text) {
		textView.setText(text);
	}
}
