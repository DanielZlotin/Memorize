package zlotindaniel.memorize;

import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import zlotindaniel.memorize.MemorizeInteractor.Display;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MemorizeView extends FrameLayout implements Display {

	private final TextView textView;
	private final ProgressBar progressBar;

	public MemorizeView(Context context) {
		super(context);
		int pad = (int) (getResources().getDisplayMetrics().density * 30);
		setPadding(pad, pad, pad, pad);

		textView = new TextView(context);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		textView.setVisibility(GONE);
		textView.setMaxLines(10);
		addView(textView, new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, CENTER));

		progressBar = new ProgressBar(context);
		progressBar.setIndeterminate(true);
		textView.setVisibility(GONE);
		addView(progressBar, new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, CENTER));
	}

	@Override
	public void showCard(String text) {
		textView.setText(text);
	}

	@Override
	public void showError(String text) {
		textView.setText(text);
	}

	@Override
	public void startLoading() {
		textView.setVisibility(GONE);
		progressBar.setVisibility(VISIBLE);
	}

	@Override
	public void endLoading() {
		textView.setVisibility(VISIBLE);
		progressBar.setVisibility(GONE);
	}
}
