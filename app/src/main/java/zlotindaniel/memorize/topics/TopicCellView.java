package zlotindaniel.memorize.topics;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopicCellView extends LinearLayout {
	private TextView title;

	public TopicCellView(final Context context) {
		super(context);
		setOrientation(HORIZONTAL);
		int pad = (int) (getResources().getDisplayMetrics().density * 16);
		setPadding(pad, pad, pad, pad);
		initTitle();
	}

	private void initTitle() {
		title = new TextView(getContext());
		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		title.setMaxLines(1);
		title.setSingleLine();
		addView(title);
	}

	public void bind(final Topic item) {
		title.setText(item.title);
	}
}
