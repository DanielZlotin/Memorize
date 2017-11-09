package zlotindaniel.memorize.android;

import android.content.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.view.inputmethod.*;

public class TextInputDialogView extends TextInputLayout {

	private final TextInputEditText input;

	public TextInputDialogView(Context context, String hint, int id) {
		super(context);
		int p = ViewUtils.dp(16);
		setPadding(p, p, p, p);

		input = new TextInputEditText(context);
		input.setInputType(EditorInfo.TYPE_CLASS_TEXT
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT
				| EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
		input.setSingleLine();
		input.setHint(hint);
		input.setId(id);

		addView(input);
	}

	public String getText() {
		return input.getText().toString();
	}

	@Override
	@NonNull
	public TextInputEditText getEditText() {
		return input;
	}
}
