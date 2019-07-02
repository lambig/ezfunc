package com.github.lambig.ezfunc.function.comparison;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.github.lambig.ezfunc.function.Comparison;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;

@RunWith(Enclosed.class)
public class ComparisonMappingTest {
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

	private static final Comparison<String> COMPARISON = new Comparison<String>() {
		@Override
		public Order compare(String a, String b) {
			return Comparison.Order.valueOf(StringUtils.compare(a, b));
		}
	};

	public static class escalate {
		@Test
		public void returning_Comparison() {
			// SetUp
			Box a = new Box("a");
			Box b = new Box("b");
			// Exercise
			ListOperands.of(a, b).sort(ComparisonMapping.escalate(Box.EXTRACTION).map(COMPARISON));
			// Verify
		}
	}
}