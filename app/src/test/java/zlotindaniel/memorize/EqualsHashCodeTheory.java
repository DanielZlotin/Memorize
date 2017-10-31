package zlotindaniel.memorize;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Example:
 * * <pre>
 * {@code
 * public static class StringTest extends EqualsHashCodeTheory {
 * <@DataPoints> public static Object[] data = { "abc", "abd", "a", "" }; }
 * }
 * </pre>
 */
@RunWith(Theories.class)
public abstract class EqualsHashCodeTheory {
	/**
	 * Type to instantiate for tests.
	 */
	private static final class OtherType {
	}

	public static final int NUM_ITERATIONS = 5;

	/**
	 * It is <i>consistent</i>: for any non-null reference values <code>x</code>
	 * and <code>y</code>, multiple invocations of <tt>x.equals(y)</tt>
	 * consistently return <code>true</code> or consistently return
	 * <code>false</code>, provided no information used in <code>equals</code>
	 * comparisons on the objects is modified.
	 */
	@Theory
	public final void equalsIsConsistent(final Object uutX, final Object uutY) {
		assumeNotNull(uutX);
		final boolean result = uutX.equals(uutY);
		for (int i = 0; i < NUM_ITERATIONS; i++)
			assertThat(uutX.equals(uutY)).isEqualTo(result);
	}

	/**
	 * It is <i>reflexive</i>: for any non-null reference value <code>x</code>,
	 * <code>x.equals(x)</code> should return <code>true</code>.
	 */
	@Theory
	public final void equalsIsReflexive(final Object uut) {
		assumeNotNull(uut);
		assertThat(uut.equals(uut)).isTrue();
	}

	/**
	 * It is <i>symmetric</i>: for any non-null reference values <code>x</code>
	 * and <code>y</code>, <code>x.equals(y)</code> should return
	 * <code>true</code> if and only if <code>y.equals(x)</code> returns
	 * <code>true</code>.
	 */
	@Theory
	public final void equalsIsSymmetric(final Object uutX, final Object uutY) {
		assumeNotNull(uutX);
		assumeNotNull(uutY);
		assumeTrue(uutY.equals(uutX));
		assertThat(uutX.equals(uutY)).isTrue();
	}

	/**
	 * It is <i>transitive</i>: for any non-null reference values <code>x</code>
	 * , <code>y</code>, and <code>z</code>, if <code>x.equals(y)</code> returns
	 * <code>true</code> and <code>y.equals(z)</code> returns <code>true</code>,
	 * then <code>x.equals(z)</code> should return <code>true</code>.
	 */
	@Theory
	public final void equalsIsTransitive(final Object uutX, final Object uutY, final Object uutZ) {
		assumeNotNull(uutX);
		assumeNotNull(uutY);
		assumeNotNull(uutZ);
		assumeTrue(uutX.equals(uutY) && uutY.equals(uutZ));
		assertThat(uutZ.equals(uutX)).isTrue();
	}

	/**
	 * Checks the consistent property of the {@link Object#equals(Object)}
	 * method
	 * regarding comparison with elements of other type.
	 */
	@Theory
	public final void equalsReturnFalseOnInstanceOfOtherType(final Object uut) {
		assumeNotNull(uut);
		final Object instanceOfOtherType = new OtherType();
		assertThat(uut).isNotEqualTo(instanceOfOtherType);
	}

	/**
	 * For any non-null reference value <code>x</code>,
	 * <code>x.equals(null)</code> should return <code>false</code>.
	 */
	@Theory
	public final void equalsReturnFalseOnNull(final Object uut) {
		assumeNotNull(uut);
		assertThat(uut.equals(null)).isFalse();
	}

	/**
	 * Whenever it is invoked on the same object more than once during an
	 * execution of a Java application, the <tt>hashCode</tt> method must
	 * consistently return the same integer, provided no information used in
	 * <tt>equals</tt> comparisons on the object is modified. This integer need
	 * not remain consistent from one execution of an application to another
	 * execution of the same application.
	 */
	@Theory
	public final void hashCodeIsConsistent(final Object uut) {
		assumeNotNull(uut);
		final int hashCode = uut.hashCode();
		for (int i = 0; i < NUM_ITERATIONS; i++)
			assertThat(uut.hashCode()).isEqualTo(hashCode);
	}

	/**
	 * If two objects are equal according to the <tt>equals(Object)</tt> method,
	 * then calling the <code>hashCode</code> method on each of the two objects
	 * must produce the same integer result.
	 */
	@Theory
	public final void hashCodeIsConsistentWithEquals(final Object uutX, final Object uutY) {
		assumeNotNull(uutX);
		assumeTrue(uutX.equals(uutY)); // uutY is not 'null'.
		assertThat(uutX.hashCode()).isEqualTo(uutY.hashCode());
	}

	/**
	 * It is <em>not</em> required that if two objects are unequal according to
	 * the {@link java.lang.Object#equals(java.lang.Object)} method, then
	 * calling the <tt>hashCode</tt> method on each of the two objects must
	 * produce distinct integer results. However, the programmer should be aware
	 * that producing distinct integer results for unequal objects may improve
	 * the performance of hashtables.
	 */
	@Theory
	public final void hashCodeProducesUnequalHashCodesForUnequalInstances(final Object uutX, final Object uutY) {
		assumeNotNull(uutX);
		assumeNotNull(uutY);
		assumeFalse(uutX.equals(uutY));
		assertThat(uutX.hashCode()).isNotEqualTo(uutY.hashCode());
	}

	/**
	 * This theory simply checks that calling the {@link Object#toString()}
	 * method
	 * of the unit under test (UUT) does not fail with raising an exception.
	 * <p>
	 * The theory is only executed on UUTs that are not <code>null</code>.
	 * </p>
	 */
	@Theory
	public final void toStringRunsWithoutFailure(final Object uut) {
		assumeNotNull(uut);
		final String string = uut.toString();
		assertThat(string).isNotNull();
	}
}