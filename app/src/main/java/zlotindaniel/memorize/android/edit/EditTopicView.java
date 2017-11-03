package zlotindaniel.memorize.android.edit;

import android.app.*;
import android.support.design.widget.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

import com.google.common.base.*;

import java.util.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.android.ViewUtils;
import zlotindaniel.memorize.cards.*;
import zlotindaniel.memorize.edit.*;

import static android.view.ViewGroup.LayoutParams.*;

public class EditTopicView extends FrameLayout implements EditTopicDisplay {

	public static final String menuBtnRenameTopicTitle = "Rename Topic";
	public static final int idInputRenameTopic = View.generateViewId();
	private static final String menuBtnDeleteTopicTitle = "Delete Topic";
	private static final int idMenuBtnDeleteTopic = View.generateViewId();
	private static final int idMenuBtnRenameTopic = View.generateViewId();
	private Listener listener;
	private AlertDialog dialog;
	private String topicName = "";
	private ListView cardsList;
	private EditTopicCardsAdapter adapter;

	public EditTopicView(final Activity context) {
		super(context);
		initCardsList();
	}

	private void initCardsList() {
		cardsList = new ListView(getContext());

		LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
		addView(cardsList, params);

		adapter = new EditTopicCardsAdapter();
		cardsList.setAdapter(adapter);
	}

	public void onCreateMenu(final Menu menu) {
		MenuItem renameItem = menu.add(Menu.NONE, idMenuBtnRenameTopic, Menu.NONE, menuBtnRenameTopicTitle);
		renameItem.setIcon(R.drawable.ic_mode_edit_whie_24dp);
		renameItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		MenuItem deleteItem = menu.add(Menu.NONE, idMenuBtnDeleteTopic, Menu.NONE, menuBtnDeleteTopicTitle);
		deleteItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	}

	public void onClickMenu(final MenuItem item) {
		if (item.getItemId() == idMenuBtnDeleteTopic) {
			askDeleteTopic();
		} else if (item.getItemId() == idMenuBtnRenameTopic) {
			askRenameTopic();
		}
	}

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void bind(String topicName, List<Card> cards, final boolean loading, String error) {
		this.topicName = topicName;

		if (loading && dialog == null) {
			dialog = showLoading();
		} else if (!loading && dialog != null) {
			dialog.dismiss();
			dialog = null;
		}

		((Activity) getContext()).setTitle(topicName);

		if (!Strings.isNullOrEmpty(error)) Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

		adapter.bind(cards);
	}

	private AlertDialog showLoading() {
		FrameLayout frame = new FrameLayout(getContext());
		int p = ViewUtils.dp(16);
		frame.setPadding(p, p, p, p);

		ProgressBar progress = new ProgressBar(getContext());
		progress.setIndeterminate(true);
		frame.addView(progress);

		return new AlertDialog.Builder(getContext())
				.setCancelable(false)
				.setView(frame)
				.show();
	}

	@Override
	public void navigateHome() {
		if (dialog != null) dialog.dismiss();
		((Activity) getContext()).finish();
	}

	private void askRenameTopic() {
		TextInputLayout layout = new TextInputLayout(getContext());
		int p = ViewUtils.dp(16);
		layout.setPadding(p, p, p, p);

		final TextInputEditText input = new TextInputEditText(getContext());
		input.setInputType(EditorInfo.TYPE_CLASS_TEXT
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT
				| EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
		input.setSingleLine();
		input.setHint("Name");
		input.setText(topicName);
		input.selectAll();
		input.setId(idInputRenameTopic);

		layout.addView(input);

		new AlertDialog.Builder(getContext())
				.setTitle("Rename Topic " + topicName)
				.setView(layout)
				.setPositiveButton("Save", (dialog, which) -> {
					String name = input.getText().toString();
					listener.renameTopic(name);
				}).show();
	}

	private void askDeleteTopic() {
		new AlertDialog.Builder(getContext())
				.setTitle("Delete " + topicName + "?")
				.setPositiveButton("Yes", (dialogInterface, i) -> askDeleteTopicAgain())
				.setNegativeButton("Oops. NO!", null)
				.show();
	}

	private void askDeleteTopicAgain() {
		new AlertDialog.Builder(getContext())
				.setTitle("Are you sure?")
				.setMessage("There's no going back! " + topicName + " will be lost forever!")
				.setPositiveButton("Yes yes go ahead!", (dialogInterface, i) -> deleteTopic())
				.setNegativeButton("Oops. NO!", null)
				.show();
	}

	private void deleteTopic() {
		listener.deleteTopic();
	}
}
