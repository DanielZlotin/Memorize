package zlotindaniel.memorize.edit;

import android.support.annotation.*;

import zlotindaniel.memorize.data.*;

public class DeleteTopicRequest extends DeleteRequest {
	public DeleteTopicRequest(@NonNull final String topicId, @NonNull final OnSuccess<Boolean> onSuccess, @NonNull final OnFailure onFailure) {
		super("topics/index/" + topicId, onSuccess, onFailure);
	}
}
