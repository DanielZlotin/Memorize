package zlotindaniel.memorize.extern.cards;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zlotindaniel.memorize.cards.CardsDisplay;
import zlotindaniel.memorize.cards.CardsPresentation;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static zlotindaniel.memorize.MemorizeApplication.dp;

public class CardsView extends RelativeLayout implements CardsDisplay {
	private TextView title;
	private TextView textView;
	private ProgressBar progressBar;

	public CardsView(Context context) {
		super(context);
		int p = dp(context, 30);
		setPadding(p, p, p, p);
		initProgress(context);
		initText(context);
		initTitle(context);
	}

	private void initProgress(Context context) {
		progressBar = new ProgressBar(context);
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(GONE);
		progressBar.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_IN_PARENT);
		addView(progressBar, params);
	}

	private void initText(Context context) {
		textView = new TextView(context);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		textView.setVisibility(GONE);
		textView.setMaxLines(10);
		textView.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_IN_PARENT);
		addView(textView, params);
	}

	private void initTitle(Context context) {
		title = new TextView(context);
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
	public void bind(final CardsPresentation presentation, final String text) {
		progressBar.setVisibility(presentation.isProgressVisible() ? VISIBLE : GONE);
		title.setVisibility(presentation.isTitleVisbile() ? VISIBLE : GONE);
		textView.setVisibility(presentation.isTextVisible() ? VISIBLE : GONE);
		textView.setText(text);
	}
}
