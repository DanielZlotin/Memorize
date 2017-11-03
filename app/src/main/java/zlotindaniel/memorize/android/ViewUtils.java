package zlotindaniel.memorize.android;

import zlotindaniel.memorize.*;

public class ViewUtils {
	public static int dp(float px) {
		return (int) (MemorizeApplication.context.getResources().getDisplayMetrics().density * px);
	}
}
