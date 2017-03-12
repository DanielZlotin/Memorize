package zlotindaniel.memorize;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MemorizeInteractorTest extends BaseTest {
	@Test
	public void start_LoadsData() throws Exception {
		MemorizeInteractor.Display mockDisplay = Mockito.mock(MemorizeInteractor.Display.class);
		TestDataLoader testDataLoader = new TestDataLoader();
		MemorizeInteractor uut = new MemorizeInteractor(mockDisplay, testDataLoader);

		uut.start();

		verify(mockDisplay, times(1)).startLoading();
	}
}
