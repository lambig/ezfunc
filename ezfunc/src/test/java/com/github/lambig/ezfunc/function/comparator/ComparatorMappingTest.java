package com.github.lambig.ezfunc.function.comparator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.string.StringReduction;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;

@RunWith(Enclosed.class)
public class ComparatorMappingTest {
	private static class Box {
		private static final Mapping<Box, String> EXTRACTION = new Mapping<Box, String>() {
			@Override
			public String map(Box current, int index) {
				return current.value;
			}

		};
		private String value;

		Box(String value) {
			this.value = value;
		}
	}

	private static final Comparator<String> Comparator = new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return StringUtils.compare(a, b);
		}
	};

	public static class escalate {
		@Test
		public void returning_Comparator() {
			// SetUp
			Box a = new Box("a");
			Box b = new Box("b");
			Box c = new Box("c");
			// Exercise
			Comparator<? super Box> actual = ComparatorMapping.escalate(Box.EXTRACTION).map(Comparator);
			// Verify
			assertThat(
					ListOperands.of(a, b, c).sort(actual).map(Box.EXTRACTION).reduce(StringReduction.join("")),
					is("abc"));
			ListOperands.of(b, c, a).sort(actual).map(Box.EXTRACTION).reduce(StringReduction.join(""));
			ListOperands.of(c, b, a).sort(actual).map(Box.EXTRACTION).reduce(StringReduction.join(""));
		}
	}
}
