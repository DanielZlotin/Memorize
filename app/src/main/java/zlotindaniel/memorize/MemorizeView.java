package zlotindaniel.memorize;

import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MemorizeView extends FrameLayout implements MemorizeInteractor.Display {

	private final TextView textView;

	public MemorizeView(Context context) {
		super(context);
		int pad = (int) (getResources().getDisplayMetrics().density * 30);
		setPadding(pad, pad, pad, pad);
		textView = new TextView(context);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		addView(textView, new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, CENTER));
	}

	@Override
	public void show(String text) {
		textView.setText(text);
	}
}
