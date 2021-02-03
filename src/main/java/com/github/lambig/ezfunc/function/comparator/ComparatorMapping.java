package com.github.lambig.ezfunc.function.comparator;

import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.operand.Optional;
import java.util.Comparator;

public abstract class ComparatorMapping {
  public static <O, P> Mapping<Comparator<? super P>, Comparator<? super O>> escalate(
      final Mapping<O, P> extraction) {
    return new Mapping<Comparator<? super P>, Comparator<? super O>>() {
      @Override
      public Comparator<O> map(final Comparator<? super P> current, final int index) {
        return new Comparator<O>() {
          @Override
          public int compare(O o1, O o2) {
            return current.compare(
                Optional.of(o1).map(extraction).get(),
                Optional.of(o2).map(extraction).get());
          }
        };
      }
    };
  }
}
