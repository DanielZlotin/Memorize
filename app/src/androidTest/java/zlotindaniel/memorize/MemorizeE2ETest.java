package zlotindaniel.memorize;

import org.junit.Test;

import zlotindaniel.memorize.android.cards.CardsView;
import zlotindaniel.memorize.android.topics.TopicsView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MemorizeE2ETest extends BaseE2ETest {

	@Test
	public void topicsCardsHappyPath() throws Exception {
		launchApp();
		waitForText(TopicsView.title);

		waitForText("Topic Name 1");
		assertDisplayed("Topic Name 1");
		assertDisplayed("Topic Name 2");
		assertDisplayed("Topic Name 3");

		clickOn("Topic Name 1");

		waitForText(CardsView.TITLE);

		waitForText("Card Question 1");
		assertDisplayed("Card Question 1");
		clickOn("Card Question 1");
		assertDisplayed("Card Answer 1");
		clickOn("Card Answer 1");
		assertDisplayed("Card Question 2");
		clickOn("Card Question 2");
		assertDisplayed("Card Answer 2");
		clickOn("Card Answer 2");
		assertDisplayed("Card Question 3");
		clickOn("Card Question 3");
		assertDisplayed("Card Answer 3");
		clickOn("Card Answer 3");
		assertDisplayed("Card Question 1");
	}

	@Test
	public void addNewTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withContentDescription(TopicsView.menuBtnNewTopicName)).perform(click());

		assertDisplayed("New Topic");
		onView(withHint("Name")).check(matches(isDisplayed()));
		onView(withId(TopicsView.idInputCreateNewTopic)).perform(typeText("New Topic 1"));

		clickOn("Create");
	}
}
