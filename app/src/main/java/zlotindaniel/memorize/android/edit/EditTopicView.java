package zlotindaniel.memorize.android.edit;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

import com.google.common.base.*;

import java.util.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.android.*;
import zlotindaniel.memorize.cards.*;
import zlotindaniel.memorize.edit.*;

import static android.view.ViewGroup.LayoutParams.*;

public class EditTopicView extends FrameLayout implements EditTopicDisplay {

	public static final String strMenuBtnRenameTopic = "Rename Topic";
	public static final String strMenuBtnDeleteTopic = "Delete Topic";
	public static final String strMenuBtnAddCard = "Add Card";
	public static final String strAlertEditCardTitle = "Edit Card";
	public static final String strAlertNewCardTitle = "New Card";
	public static final String strAlertNewCardCreateBtn = "Create";
	public static final String strAlertEditCardSaveBtn = "Save";
	public static final String strAlertEditCardDeleteBtn = "Delete";
	public static final int idMenuBtnDeleteTopic = View.generateViewId();
	public static final int idMenuBtnRenameTopic = View.generateViewId();
	public static final int idMenuBtnAddCard = View.generateViewId();
	public static final int idAlertRenameTopicInput = View.generateViewId();
	public static final int idAlertQuestionInput = View.generateViewId();
	public static final int idAlertAnswerInput = View.generateViewId();
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
		MenuItem addCard = menu.add(Menu.NONE, idMenuBtnAddCard, Menu.NONE, strMenuBtnAddCard);
		addCard.setIcon(R.drawable.ic_add_white_24dp);
		addCard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		MenuItem renameItem = menu.add(Menu.NONE, idMenuBtnRenameTopic, Menu.NONE, strMenuBtnRenameTopic);
		renameItem.setIcon(R.drawable.ic_mode_edit_whie_24dp);
		renameItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		MenuItem deleteItem = menu.add(Menu.NONE, idMenuBtnDeleteTopic, Menu.NONE, strMenuBtnDeleteTopic);
		deleteItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	}

	public void onClickMenu(final MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == idMenuBtnDeleteTopic) {
			askDeleteTopic();
		} else if (itemId == idMenuBtnRenameTopic) {
			askRenameTopic();
		} else if (itemId == idMenuBtnAddCard) {
			askNewCard();
		}
	}

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
		cardsList.setOnItemClickListener((adapterView, view, pos, id) -> showCardDetails(adapter.getItem(pos)));
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
		TextInputDialogView view = new TextInputDialogView(getContext(), "Name", idAlertRenameTopicInput);
		view.getEditText().setText(topicName);
		view.getEditText().selectAll();

		new AlertDialog.Builder(getContext())
				.setTitle("Rename Topic " + topicName)
				.setView(view)
				.setPositiveButton("Save", (dialog, which) -> listener.renameTopic(view.getText()))
				.show();
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
				.setPositiveButton("Yes yes go ahead!", (dialogInterface, i) -> listener.deleteTopic())
				.setNegativeButton("Oops. NO!", null)
				.show();
	}

	private void askNewCard() {
		CardDetailsView view = new CardDetailsView(getContext());
		new AlertDialog.Builder(getContext())
				.setTitle(strAlertNewCardTitle)
				.setView(view)
				.setPositiveButton(strAlertNewCardCreateBtn,
						(dialog, which) -> listener.createCard(view.questionView.getText(), view.answerView.getText()))
				.show();
	}

	private void showCardDetails(final Card card) {
		CardDetailsView view = new CardDetailsView(getContext());
		view.questionView.getEditText().setText(card.getQuestion());
		view.answerView.getEditText().setText(card.getAnswer());

		new AlertDialog.Builder(getContext())
				.setTitle(strAlertEditCardTitle)
				.setView(view)
				.setPositiveButton(strAlertEditCardSaveBtn,
						(dialogInterface, i) -> listener.saveCard(card, view.questionView.getText(), view.answerView.getText()))
				.setNegativeButton(strAlertEditCardDeleteBtn,
						(dialogInterface, i) -> listener.deleteCard(card))
				.show();
	}

	private static class CardDetailsView extends LinearLayout {
		public final TextInputDialogView questionView;
		public final TextInputDialogView answerView;

		public CardDetailsView(final Context context) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);

			questionView = new TextInputDialogView(context, "Question", idAlertQuestionInput);
			answerView = new TextInputDialogView(context, "Answer", idAlertAnswerInput);
			int p = ViewUtils.dp(16);
			answerView.setPadding(p, 0, p, p);

			addView(questionView);
			addView(answerView);
		}
	}
}
