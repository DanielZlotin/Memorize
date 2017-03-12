package zlotindaniel.memorize.data;

public interface DataLoader<T> {
	void load(OnSuccess<T> onSuccess, OnFailure onFailure);
}
