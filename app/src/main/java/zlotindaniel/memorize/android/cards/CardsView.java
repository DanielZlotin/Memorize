package zlotindaniel.memorize.android.cards;

import android.content.*;
import android.util.*;
import android.widget.*;

import zlotindaniel.memorize.cards.*;

import static android.view.ViewGroup.LayoutParams.*;
import static zlotindaniel.memorize.android.ViewUtils.*;

public class CardsView extends RelativeLayout implements CardsDisplay {
	public static final String title = "What is:";

	private TextView titleView;
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
		titleView = new TextView(getContext());
		titleView.setVisibility(GONE);
		titleView.setText(title);
		titleView.setSingleLine();
		titleView.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_HORIZONTAL);
		params.addRule(ABOVE, textView.getId());
		addView(titleView, params);
	}

	@Override
	public void bind(final CardsPresentation presentation, final String text) {
		progressBar.setVisibility(presentation.isProgressVisible() ? VISIBLE : GONE);
		titleView.setVisibility(presentation.isTitleVisbile() ? VISIBLE : GONE);
		textView.setVisibility(presentation.isTextVisible() ? VISIBLE : GONE);
		textView.setText(text);
	}
}
