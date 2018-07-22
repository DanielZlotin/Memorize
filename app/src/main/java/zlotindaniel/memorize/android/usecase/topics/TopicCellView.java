package zlotindaniel.memorize.android.usecase.topics;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import zlotindaniel.memorize.data.*;

import static android.view.ViewGroup.LayoutParams.*;
import static zlotindaniel.memorize.android.ViewUtils.*;

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
