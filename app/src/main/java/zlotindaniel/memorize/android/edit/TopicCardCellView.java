package zlotindaniel.memorize.android.edit;

import android.content.*;
import android.util.*;
import android.widget.*;

import zlotindaniel.memorize.cards.*;

import static android.view.ViewGroup.LayoutParams.*;
import static zlotindaniel.memorize.android.ViewUtils.*;

public class TopicCardCellView extends LinearLayout {
	private TextView question;
	private TextView answer;

	public TopicCardCellView(final Context context) {
		super(context);
		int p = dp(8);
		setPadding(p, p, p, p);
		setOrientation(HORIZONTAL);
		initQuestion();
		initAnswer();
	}

	private void initQuestion() {
		question = new TextView(getContext());
		question.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, WRAP_CONTENT, 4);
		params.setMarginEnd(dp(8));
		addView(question, params);
	}

	private void initAnswer() {
		answer = new TextView(getContext());
		answer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, WRAP_CONTENT, 6);
		addView(answer, params);
	}

	public void bind(final Card item) {
		question.setText(item.getQuestion());
		answer.setText(item.getAnswer());
	}
}
