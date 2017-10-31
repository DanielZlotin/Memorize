package zlotindaniel.memorize.cards;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CardsPresentationTest extends BaseTest {

	@Test
	public void isEnum() throws Exception {
		assertThat(CardsPresentation.valueOf("Loading")).isEqualTo(CardsPresentation.Loading);
		assertThat(CardsPresentation.values()).hasSize(4);
	}

	@Test
	public void progressVisibility() throws Exception {
		assertThat(CardsPresentation.Loading.isProgressVisible()).isTrue();
		assertThat(CardsPresentation.Question.isProgressVisible()).isFalse();
		assertThat(CardsPresentation.Error.isProgressVisible()).isFalse();
	}

	@Test
	public void titleVisibility() throws Exception {
		assertThat(CardsPresentation.Question.isTitleVisbile()).isTrue();
		assertThat(CardsPresentation.Answer.isTitleVisbile()).isFalse();
		assertThat(CardsPresentation.Error.isTitleVisbile()).isFalse();
	}

	@Test
	public void textVisibility() throws Exception {
		assertThat(CardsPresentation.Question.isTextVisible()).isTrue();
		assertThat(CardsPresentation.Answer.isTextVisible()).isTrue();
		assertThat(CardsPresentation.Error.isTextVisible()).isTrue();
		assertThat(CardsPresentation.Loading.isTextVisible()).isFalse();
	}
}
