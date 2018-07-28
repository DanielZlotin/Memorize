package zlotindaniel.memorize.android.usecase.cards;

import android.content.*;
import android.util.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import zlotindaniel.memorize.R;
import zlotindaniel.memorize.usecase.cards.*;

import static android.view.ViewGroup.LayoutParams.*;
import static zlotindaniel.memorize.android.ViewUtils.*;

public class CardsView extends RelativeLayout implements CardsDisplay {
	public static final String title = "What is:";
	public static final String menuBtnEasy = "Too easy!";
	public static final int idMenuBtnEasy = View.generateViewId();

	private TextView titleView;
	private TextView textView;
	private ProgressBar progressBar;
	private MenuItem easyBtn;
	private Listener listener;

	public CardsView(Context context) {
		super(context);
		int p = dp(30);
		setPadding(p, p, p, p);
		initProgress();
		initText();
		initTitle();
	}

	public void onCreateMenu(Menu menu) {
		easyBtn = menu.add(Menu.NONE, idMenuBtnEasy, Menu.NONE, menuBtnEasy);
		easyBtn.setIcon(R.drawable.ic_visibility_off_24);
		easyBtn.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		easyBtn.setVisible(false);
	}

	public void onClickMenu(MenuItem item) {
		if (item.getItemId() == idMenuBtnEasy) {
			listener.clickEasyCard();
		}
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
	public void setListener(final Listener listener) {
		this.listener = listener;
		setOnClickListener(view -> listener.click());
	}

	@Override
	public void bind(final CardsPresentation presentation, final String text) {
		progressBar.setVisibility(presentation.isProgressVisible() ? VISIBLE : GONE);
		titleView.setVisibility(presentation.isTitleVisbile() ? VISIBLE : GONE);
		textView.setVisibility(presentation.isTextVisible() ? VISIBLE : GONE);
		textView.setText(text);
		if (easyBtn != null)
			easyBtn.setVisible(presentation == CardsPresentation.Answer);
	}
}
