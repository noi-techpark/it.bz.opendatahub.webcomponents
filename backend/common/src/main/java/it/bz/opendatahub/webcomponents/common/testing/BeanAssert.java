package it.bz.opendatahub.webcomponents.common.testing;

import lombok.val;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanAssert extends AbstractAssert<BeanAssert, Object> {
	public BeanAssert(Object actual) {
		super(actual, BeanAssert.class);
	}

	public static BeanAssert assertThat(Object actual) {
		return new BeanAssert(actual);
	}

	public BeanAssert hasCopiedAllProperties(Object source, String... exceptions) {
		isNotNull();

		val properties = getPropertyListArray(source.getClass(), exceptions);

		Assertions.assertThat(actual).isEqualToComparingOnlyGivenFields(source, properties);

		return this;
	}

	private String[] getPropertyListArray(Class<?> c, String... exceptions) {
		val result = new ArrayList<String>();

		val exceptionsList = Arrays.asList(exceptions);

		val fields = c.getDeclaredFields();
		for(val field : fields) {
			if(!exceptionsList.contains(field.getName())) {
				result.add(field.getName());
			}
		}

		return result.toArray(new String[0]);
	}
}
