package zlotindaniel.memorize;

public interface DataLoader<T> {
	void load(OnSuccess<T> onSuccess, OnFailure onFailure);
}
