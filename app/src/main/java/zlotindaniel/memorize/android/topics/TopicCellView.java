package zlotindaniel.memorize.android.topics;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import zlotindaniel.memorize.topics.Topic;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static zlotindaniel.memorize.android.ViewUtils.dp;

public class TopicCellView extends LinearLayout {
	private TextView title;

	public TopicCellView(final Context context) {
		super(context);
		int p = dp(16);
		setPadding(p, p, p, p);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);
		initTitle();
	}

	private void initTitle() {
		title = new TextView(getContext());
		title.setSingleLine();
		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		LayoutParams params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		addView(title, params);
	}

	public void bind(final Topic item) {
		title.setText(item.getName());
	}
}
