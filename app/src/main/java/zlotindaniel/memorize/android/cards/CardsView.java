package zlotindaniel.memorize.android.cards;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zlotindaniel.memorize.cards.CardsDisplay;
import zlotindaniel.memorize.cards.CardsPresentation;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static zlotindaniel.memorize.android.ViewUtils.dp;

public class CardsView extends RelativeLayout implements CardsDisplay {
	public static final String TITLE = "What is:";

	private TextView title;
	private TextView textView;
	private ProgressBar progressBar;

	public CardsView(Context context) {
		super(context);
		int p = dp(30);
		setPadding(p, p, p, p);
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
		title.setText(TITLE);
		title.setSingleLine();
		title.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_HORIZONTAL);
		params.addRule(ABOVE, textView.getId());
		addView(title, params);
	}

	@Override
	public void bind(final CardsPresentation presentation, final String text) {
		progressBar.setVisibility(presentation.isProgressVisible() ? VISIBLE : GONE);
		title.setVisibility(presentation.isTitleVisbile() ? VISIBLE : GONE);
		textView.setVisibility(presentation.isTextVisible() ? VISIBLE : GONE);
		textView.setText(text);
	}
}
