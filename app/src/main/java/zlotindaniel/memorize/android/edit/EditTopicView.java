package zlotindaniel.memorize.android.edit;

import android.app.*;
import android.view.*;
import android.widget.*;

import com.google.common.base.*;

import zlotindaniel.memorize.android.*;
import zlotindaniel.memorize.edit.*;

public class EditTopicView extends LinearLayout implements EditTopicDisplay {

	private static final String menuBtnDeleteTopicTitle = "Delete Topic";
	private static final int idMenuBtnDeleteTopic = View.generateViewId();
	private final String topicName;
	private Listener listener;
	private AlertDialog dialog;

	public EditTopicView(final Activity context, final String topicName) {
		super(context);
		this.topicName = topicName;
		context.setTitle(topicName);
		setOrientation(VERTICAL);

	}

	public void onCreateMenu(final Menu menu) {
		MenuItem item = menu.add(Menu.NONE, idMenuBtnDeleteTopic, Menu.NONE, menuBtnDeleteTopicTitle);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	}

	public void onClickMenu(final MenuItem item) {
		if (item.getItemId() == idMenuBtnDeleteTopic) {
			askDeleteTopic();
		}
	}

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void bind(final boolean loading, String error) {
		if (loading && dialog == null) {
			FrameLayout frame = new FrameLayout(getContext());
			int p = ViewUtils.dp(16);
			frame.setPadding(p, p, p, p);
			ProgressBar progress = new ProgressBar(getContext());
			progress.setIndeterminate(true);
			dialog = new AlertDialog.Builder(getContext()).setCancelable(false).setView(progress).show();
		} else if (!loading && dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
		if (!Strings.isNullOrEmpty(error)) Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

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

	@Override
	public void navigateHome() {
		((Activity) getContext()).finish();
	}
}
