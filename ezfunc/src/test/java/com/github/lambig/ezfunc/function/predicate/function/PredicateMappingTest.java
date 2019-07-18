package com.github.lambig.ezfunc.function.predicate.function;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import com.github.lambig.ezfunc.function.string.StringReduction;
import com.github.lambig.ezfunc.operand.Optional;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;

@RunWith(Enclosed.class)
public class PredicateMappingTest {
	private static class Box<E> {
		private E value;

		private Box(E value) {
			this.value = value;
		}
	}

	public static class having {
		@Test
		public void map() {
			// SetUp
			Optional<Integer> criteria = Optional.of(5);
			Mapping<Box<Integer>, Integer> extraction = new Mapping<Box<Integer>, Integer>() {
				@Override
				public Integer map(Box<Integer> current, int index) {
					return current.value;
				}
			};
			Predicate<Box<Integer>> predicate = criteria.map(PredicateMapping.having(extraction)).get();

			ListOperands<Box<Integer>> target = ListOperands.of(new Box<>(1), new Box<>(5), new Box<>(3));
			// Exercise
			String actual = target.map(predicate).map(Mapping.TO_STRING).reduce(StringReduction.join(""));
			// Verify
			assertThat(actual, is("falsetruefalse"));

		}
	}
}
