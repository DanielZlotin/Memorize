package zlotindaniel.memorize.cards;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CardsView extends RelativeLayout implements CardsInteractor.Display {
	private TextView title;
	private TextView textView;
	private ProgressBar progressBar;

	public CardsView(Context context) {
		super(context);
		int pad = (int) (getResources().getDisplayMetrics().density * 30);
		setPadding(pad, pad, pad, pad);
		initProgress();
		initText();
		initTitle();
	}

	private void initProgress() {
		progressBar = new ProgressBar(getContext());
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(GONE);
		progressBar.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_IN_PARENT);
		addView(progressBar, params);
	}

	private void initText() {
		textView = new TextView(getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		textView.setVisibility(GONE);
		textView.setMaxLines(10);
		textView.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_IN_PARENT);
		addView(textView, params);
	}

	private void initTitle() {
		title = new TextView(getContext());
		title.setVisibility(GONE);
		title.setText("What is:");
		title.setSingleLine();
		title.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_HORIZONTAL);
		params.addRule(ABOVE, textView.getId());
		addView(title, params);
	}

	@Override
	public void showPhrase(String phrase) {
		title.setVisibility(VISIBLE);
		textView.setText(phrase);
	}

	@Override
	public void showDefinition(String definition) {
		title.setVisibility(GONE);
		textView.setText(definition);
	}

	@Override
	public void showError(String text) {
		title.setVisibility(GONE);
		textView.setText(text);
	}

	@Override
	public void startLoading() {
		title.setVisibility(GONE);
		textView.setVisibility(GONE);
		progressBar.setVisibility(VISIBLE);
	}

	@Override
	public void endLoading() {
		title.setVisibility(GONE);
		textView.setVisibility(VISIBLE);
		progressBar.setVisibility(GONE);
	}
}
