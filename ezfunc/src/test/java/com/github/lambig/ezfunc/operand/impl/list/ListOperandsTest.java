package com.github.lambig.ezfunc.operand.impl.list;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;

@RunWith(Enclosed.class)
public class ListOperandsTest {
	public static class wrap_aslist {
		@Test
		public void wraps_and_return_new_one() {
			// SetUp
			List<String> orig = Lists.newArrayList("foo", "bar", "baz");
			// Exercise
			List<String> dest = ListOperands.of(orig).asList();
			// Verify
			assertThat(dest, is(not(sameInstance(orig))));
		}
	}

	public static class empty {

		@Test
		public void is_empty() {
			// SetUp
			List<String> orig = Lists.newArrayList();
			// Exercise
			ListOperands<String> dest = ListOperands.of(orig);
			// Verify
			assertThat(dest.isEmpty(), is(true));
			assertThat(dest.isNotEmpty(), is(false));
		}

		@Test
		public void not_empty() {
			// SetUp
			List<String> orig = Lists.newArrayList("abc");
			// Exercise
			ListOperands<String> dest = ListOperands.of(orig);
			// Verify
			assertThat(dest.isEmpty(), is(false));
			assertThat(dest.isNotEmpty(), is(true));
		}
	}
}
