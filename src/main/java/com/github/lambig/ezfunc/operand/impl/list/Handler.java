package com.github.lambig.ezfunc.operand.impl.list;

import com.github.lambig.ezfunc.function.Action;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

;

class Handler {
  @SuppressWarnings("unchecked")
  static <F, T> T reduce(List<F> orig, Reduction<? super F, ? super T> r, T dest) {
    int i = 0;
    for (F current : orig) {
      dest = (T) r.reduce(dest, current, i);
      i++;
    }
    return dest;
  }

  static <E> List<E> filter(List<E> orig, Predicate<? super E> f) {
    int i = 0;
    List<E> newList = new ArrayList<>(orig.size());
    for (E current : orig) {
      if (f.eval(current, i)) {
        newList.add(current);
      }
      i++;
    }
    return newList;
  }

  static <T, F> List<T> map(List<F> orig, Mapping<? super F, ? extends T> m) {
    int i = 0;
    List<T> newList = new ArrayList<>(orig.size());
    for (F current : orig) {
      newList.add(m.map(current, i));
      i++;
    }
    return newList;
  }

  static <F> List<F> map(List<F> orig, Mapping<? super F, ? extends F> m, Predicate<? super F> condition) {
    int i = 0;
    List<F> newList = new ArrayList<>(orig.size());
    for (F current : orig) {
      newList.add(condition.eval(current, i) ? m.map(current, i) : current);
      i++;
    }
    return newList;
  }

  static <E> void forEach(List<E> orig, Action<? super E> a) {
    int i = 0;
    for (E current : orig) {
      a.exec(current, i);
      i++;
    }
  }

  @SafeVarargs
  public static <T> List<T> concat(List<T> orig, T... elems) {
    List<T> result = new ArrayList<>(orig);
    result.addAll(Lists.newArrayList(elems));
    return result;
  }

  @SafeVarargs
  public static <T> List<T> concat(List<? extends T> orig, List<? extends T>... lists) {
    List<T> result = new ArrayList<>(orig);
    for (List<? extends T> list : lists) {
      result.addAll(list);
    }
    return result;
  }

  static <P, C> Comparator<? super P> toParentComparator(
      final Comparator<? super C> childComparator, final Mapping<? super P, C> extraction) {
    return new Comparator<P>() {
      @Override
      public int compare(P a, P b) {
        return childComparator.compare(extraction.map(a), extraction.map(b));
      }
    };
  }

}
