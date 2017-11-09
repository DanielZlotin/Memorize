package zlotindaniel.memorize;

import android.support.test.espresso.*;

import org.junit.*;
import org.junit.runners.*;

import zlotindaniel.memorize.android.cards.*;
import zlotindaniel.memorize.android.edit.*;
import zlotindaniel.memorize.android.topics.*;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E2ETest extends BaseE2ETest {

	@Test
	public void _1_topicsCardsHappyPath() throws Exception {
		launchApp();
		waitForText(TopicsView.title);

		waitForText("Topic Name 1");
		waitForText("Topic Name 2");
		waitForText("Topic Name 1");
		waitForText("Topic Name 3");

		onView(withText("Topic Name 1")).perform(click());

		waitForText(CardsView.title);

		waitForText("Card Question 1");
		waitForText("Card Question 1");
		onView(withText("Card Question 1")).perform(click());
		waitForText("Card Answer 1");
		onView(withText("Card Answer 1")).perform(click());
		waitForText("Card Question 2");
		onView(withText("Card Question 2")).perform(click());
		waitForText("Card Answer 2");
		onView(withText("Card Answer 2")).perform(click());
		waitForText("Card Question 3");
		onView(withText("Card Question 3")).perform(click());
		waitForText("Card Answer 3");
		onView(withText("Card Answer 3")).perform(click());
		waitForText("Card Question 1");
	}

	@Test
	public void _2_addNewTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withContentDescription(TopicsView.menuBtnNewTopicName)).perform(click());

		waitForText("New Topic");
		onView(withHint("Name")).check(matches(isDisplayed()));
		onView(withId(TopicsView.idInputCreateNewTopic)).perform(typeText("New Topic"));

		onView(withText("Create")).perform(click());
		waitForText(TopicsView.title);
		waitForText("New Topic");
		waitForText("New Topic");
	}

	@Test
	public void _3_renameTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("New Topic")).perform(longClick());

		Espresso.onIdle();
		waitForText("New Topic");

		onView(withContentDescription(EditTopicView.strMenuBtnRenameTopic)).perform(click());
		onView(withId(EditTopicView.idAlertRenameTopicInput)).perform(clearText());
		onView(withId(EditTopicView.idAlertRenameTopicInput)).perform(typeText("renamed topic"));

		onView(withText("Save")).perform(click());
		Espresso.onIdle();
		waitForText("Renamed Topic");
		waitForText("Renamed Topic");

		device().pressBack();
		waitForText("Renamed Topic");
		assertNotDisplayed("New Topic");
	}

	@Test
	public void _4_topicDetailsRemoveTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("Renamed Topic")).perform(longClick());

		Espresso.onIdle();
		waitForText("Renamed Topic");

		onView(withContentDescription("More options")).perform(click());
		onView(withText("Delete Topic")).perform(click());
		waitForText("Delete Renamed Topic?");
		onView(withText("Oops. NO!")).perform(click());
		waitForText("Renamed Topic");

		onView(withContentDescription("More options")).perform(click());
		onView(withText("Delete Topic")).perform(click());
		waitForText("Delete Renamed Topic?");
		onView(withText("Yes")).perform(click());
		waitForText("Are you sure?");
		waitForText("There's no going back! Renamed Topic will be lost forever!");
		onView(withText("Yes yes go ahead!")).perform(click());

		Espresso.onIdle();
		waitForText(TopicsView.title);

		assertNotDisplayed("Renamed Topic");
	}

	@Test
	public void _5_topicDetailsAddCard() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("Topic Name 3")).perform(longClick());
		waitForText("Topic Name 3");

		waitForText("This is a very long card question. What is the actual reason for this long a question?");
		waitForText("This is a very long card answer. The reason being that we have to make sure it all looks good in multiline etc.");

		assertNotDisplayed("The new question");
		assertNotDisplayed("The new answer");

		onView(withContentDescription(EditTopicView.strMenuBtnAddCard)).perform(click());
		waitForText(EditTopicView.strAlertNewCardTitle);

		onView(withId(EditTopicView.idAlertQuestionInput)).perform(typeText("The new question"));
		onView(withId(EditTopicView.idAlertAnswerInput)).perform(typeText("The new answer"));

		onView(withText(EditTopicView.strAlertNewCardCreateBtn)).perform(click());

		waitForText("Topic Name 3");
		waitForText("The new question");
		waitForText("The new answer");
	}

	@Test
	public void _6_topicDetailsCardDetailsSaveCard() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("Topic Name 3")).perform(longClick());
		waitForText("Topic Name 3");
		waitForText("The new question");
		waitForText("The new answer");

		onView(withText("The new question")).perform(click());
		waitForText(EditTopicView.strAlertEditCardTitle);

		onView(withId(EditTopicView.idAlertQuestionInput)).perform(clearText()).perform(typeText("The renamed question"));
		onView(withId(EditTopicView.idAlertAnswerInput)).perform(clearText()).perform(typeText("The renamed answer"));

		onView(withText(EditTopicView.strAlertEditCardSaveBtn)).perform(click());
		waitForText("Topic Name 3");

		assertNotDisplayed("The new question");
		waitForText("The renamed question");
		assertNotDisplayed("The new answer");
		waitForText("The renamed answer");
	}

	@Test
	public void _7_topicDetailsCardDetailsDeleteCard() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("Topic Name 3")).perform(longClick());
		waitForText("Topic Name 3");
		waitForText("The renamed question");

		onView(withText("The renamed question")).perform(click());
		waitForText(EditTopicView.strAlertEditCardTitle);

		onView(withText(EditTopicView.strAlertEditCardDeleteBtn)).perform(click());
		waitForText("Topic Name 3");

		assertNotDisplayed("The renamed question");
		assertNotDisplayed("The renamed answer");
	}
}
