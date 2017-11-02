package zlotindaniel.memorize.android.edit;

import android.app.*;
import android.view.*;
import android.widget.*;

public class EditTopicView extends LinearLayout {

	private static final String menuBtnDeleteTopicTitle = "Delete Topic";
	private static final int idMenuBtnDeleteTopic = View.generateViewId();
	private final String topicName;

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
		navigateHome();
	}

	private void navigateHome() {
		((Activity) getContext()).finish();
	}
}
