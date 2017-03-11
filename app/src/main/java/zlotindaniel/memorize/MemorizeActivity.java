package zlotindaniel.memorize;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class MemorizeActivity extends Activity {
	private TextView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new TextView(this);
		setContentView(view);
		FirebaseDatabase.getInstance().getReference().setValue("Hello, World!").addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				view.setBackgroundColor(Color.GREEN);
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				view.setText(e.toString());
				view.setBackgroundColor(Color.RED);
			}
		});
	}
}
