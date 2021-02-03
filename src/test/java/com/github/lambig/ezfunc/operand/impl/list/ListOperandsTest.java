package com.github.lambig.ezfunc.operand.impl.list;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;


import com.github.lambig.ezfunc.function.Action;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import com.github.lambig.ezfunc.function.string.StringReduction;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ListOperandsTest {

  private static final Mapping<Integer, Integer> ADD_ONE = new Mapping<Integer, Integer>() {
    @Override
    public Integer map(Integer current, int index) {
      return current + 1;
    }
  };
  private static final Mapping<Integer, String> TO_STRING = new Mapping<Integer, String>() {
    @Override
    public String map(Integer current, int index) {
      return String.valueOf(current);
    }
  };
  private static final Predicate<Object> NOT_FIRST = new Predicate<Object>() {
    @Override
    public boolean eval(Object current, int index) {
      return index > 0;
    }
  };

  private static final Reduction<Integer, Integer> SUM = new Reduction<Integer, Integer>() {
    @Override
    public Integer reduce(Integer accum, Integer current, int index) {
      return accum + current;
    }
  };

  private static class Box<E> {
    private E value;

    private Box(E value) {
      this.value = value;
    }
  }

  private final static <E> Mapping<Box<E>, E> extraction() {
    return new Mapping<Box<E>, E>() {
      @Override
      public E map(Box<E> current, int index) {
        return current.value;
      }
    };
  }

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

  public static class filter {
    @Test
    public void do_filter() {
      // SetUp
      ListOperands<String> target = ListOperands.of("a", "b", "c");
      // Exercise
      String actual = target.filter(NOT_FIRST).reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("bc"));
    }
  }

  public static class map {

    @Test
    public void do_map() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.map(TO_STRING).reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("123"));
    }

    @Test
    public void do_complex_map() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.map(ADD_ONE.then(TO_STRING)).reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("234"));
    }

    @Test
    public void do_map_with_condition() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.map(ADD_ONE, NOT_FIRST).map(TO_STRING).reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("134"));
    }
  }

  public static class reduceOptional {
    @Test
    public void reduce() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.reduceOptional(SUM).map(TO_STRING).get();
      // Verify
      assertThat(actual, is("6"));
    }

    @Test
    public void reduce_with_explicit_initial() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.reduceOptional(SUM, 1).map(TO_STRING).get();
      // Verify
      assertThat(actual, is("7"));
    }
  }

  public static class concat {
    @Test
    public void elements() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.concat(4, 5, 6).map(TO_STRING).reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("123456"));
    }

    @Test
    public void lists_of_elements() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.concat(
          Lists.newArrayList(4),
          Lists.<Integer>newArrayList(),
          Lists.newArrayList(5, 6))
          .map(TO_STRING)
          .reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("123456"));
    }
  }

  public static class first {
    @Test
    public void get_first() {
      // SetUp
      ListOperands<String> target = ListOperands.of("a", "b", "c");
      // Exercise
      String actual = target.first();
      // Verify
      assertThat(actual, is("a"));
    }

    @Test
    public void get_first_without_value() {
      // SetUp
      ListOperands<String> target = ListOperands.of("a");
      // Exercise
      String actual = target.first(NOT_FIRST);
      // Verify
      assertThat(actual, is(nullValue()));
    }

    @Test
    public void get_firstOr_with_value() {
      // SetUp
      ListOperands<String> target = ListOperands.of("a", "b", "c");
      // Exercise
      String actual = target.firstOr("x");
      // Verify
      assertThat(actual, is("a"));
    }

    @Test
    public void get_firstOr_without_value() {
      // SetUp
      ListOperands<String> target = ListOperands.of();
      // Exercise
      String actual = target.firstOr("x");
      // Verify
      assertThat(actual, is("x"));
    }

    @Test
    public void get_firstOr_with_condition_with_value() {
      // SetUp
      ListOperands<String> target = ListOperands.of("a", "b", "c");
      // Exercise
      String actual = target.firstOr(NOT_FIRST, "x");
      // Verify
      assertThat(actual, is("b"));
    }

    @Test
    public void get_firstOr_with_condition_without_value() {
      // SetUp
      ListOperands<String> target = ListOperands.of("a");
      // Exercise
      String actual = target.firstOr(NOT_FIRST, "x");
      // Verify
      assertThat(actual, is("x"));
    }
  }

  public static class forEach {
    @Test
    public void elements() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      final List<Integer> actual = Lists.newArrayList(0);
      Action<Integer> store = new Action<Integer>() {
        @Override
        public void exec(Integer current, int index) {
          actual.set(0, actual.get(0) + current);
        }
      };
      // Exercise
      target.forEach(store);
      // Verify
      assertThat(actual.get(0), is(6));
    }
  }

  public static class sort {

    @Test
    public void with_comparator() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.sort(new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
          return o1 - o2;
        }
      }).map(TO_STRING).reduce(StringReduction.join(""));

      // Verify
      assertThat(actual, is("123"));
    }

    @Test
    public void with_comparator_of_property() {
      // SetUp
      Box<Integer> a = new Box<>(1);
      Box<Integer> b = new Box<>(2);
      Box<Integer> c = new Box<>(3);
      ListOperands<Box<Integer>> target = ListOperands.of(a, b, c);
      // Exercise
      String actual =
          target
              .<Integer>sort(
                  new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                      return o1 - o2;
                    }
                  }, ListOperandsTest.<Integer>extraction())
              .map(ListOperandsTest.<Integer>extraction())
              .map(TO_STRING)
              .reduce(StringReduction.join(""));

      // Verify
      assertThat(actual, is("123"));
    }
  }

  public static class reverse {
    @Test
    public void return_reverse() {
      // SetUp
      ListOperands<Integer> target = ListOperands.of(1, 2, 3);
      // Exercise
      String actual = target.reverse().map(TO_STRING).reduce(StringReduction.join(""));
      // Verify
      assertThat(actual, is("321"));
    }
  }
}
