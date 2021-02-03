package com.github.lambig.ezfunc.operand;

import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import java.util.Comparator;
import java.util.List;

public interface Operands<T> extends Operand<T> {
  /**
   * reduce to same type of the elements
   *
   * @param r reduction
   * @return reduced object
   */
  T reduce(Reduction<? super T, ? super T> r);

  /**
   * reduce to same type of the elements
   *
   * @param r reduction
   * @return optional of reduced object
   */
  Optional<T> reduceOptional(Reduction<? super T, ? super T> r);

  /**
   * sort elements
   *
   * @param c comparator
   * @return sorted operands
   */
  Operands<T> sort(Comparator<? super T> c);

  /**
   * sort elements with elements' property or so
   *
   * @param <X>        type of the elements' property
   * @param c          comparator
   * @param extraction mapping from element to property
   * @return sorted operands
   */
  <X> Operands<T> sort(Comparator<? super X> c, Mapping<? super T, X> extraction);

  /**
   * get first element
   *
   * @return first element
   */
  T first();

  /**
   * get first element satisfies condition
   *
   * @return first element
   */
  T first(Predicate<? super T> condition);

  /**
   * get first element or default
   *
   * @param def default
   * @return first element
   */
  T firstOr(T def);

  /**
   * get first element satisfies condition or default
   *
   * @param condition condition
   * @param def       default
   * @return first element
   */
  T firstOr(Predicate<? super T> condition, T def);

  /**
   * get reversed Operands
   *
   * @return reversed operands
   */
  Operands<T> reverse();
}
