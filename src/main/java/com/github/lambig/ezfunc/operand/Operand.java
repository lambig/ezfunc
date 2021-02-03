package com.github.lambig.ezfunc.operand;

import com.github.lambig.ezfunc.function.Action;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import java.util.List;

public interface Operand<T> {
  /**
   * returns if the Operand is empty
   *
   * @return true if empty
   */
  boolean isEmpty();

  /**
   * returns if the Operand is not empty
   *
   * @return true if empty
   */
  boolean isNotEmpty();

  /**
   * reduces element[s] to dest by patameter
   *
   * @param <X>  type of destination
   * @param r    reduction
   * @param dest destination
   * @return reduced object
   */
  <X> X reduce(Reduction<? super T, ? super X> r, X dest);

  /**
   * reduces element[s] to Optional of dest by paramter
   *
   * @param <X>  type of destination
   * @param r    reduction
   * @param dest destination
   * @return reduced object's Optional
   */
  <X> Optional<X> reduceOptional(Reduction<? super T, ? super X> r, X dest);

  /**
   * filter element[s] with parameter
   *
   * @param f predicate
   * @return filtered operand
   */
  Operand<T> filter(Predicate<? super T> f);

  /**
   * map element[s] with parameter
   *
   * @param <S>     destination type of mapping
   * @param mapping mapping function
   * @return operand wrapping mapped element[s]
   */
  <S> Operand<S> map(Mapping<? super T, S> mapping);

  /**
   * map element[s] that satisfies the condition, and do nothing to the rest
   *
   * @param mapping   mapping
   * @param condition decides what kind of element[s] to map
   * @return
   */
  Operand<T> map(Mapping<? super T, T> mapping, Predicate<? super T> condition);

  /**
   * do action to each element[s]
   *
   * @param e action
   */
  void forEach(Action<? super T> e);

  /**
   * get element[s] as a list
   *
   * @return list (may be empty)
   */
  List<T> asList();

  /**
   * concatenate parameter to the element
   *
   * @param elements follow this.element[s]
   * @return concatenated elements
   */
  Operand<T> concat(@SuppressWarnings("unchecked") T... elements);

  /**
   * concatenate parameters flatly to the element
   *
   * @param elements follow this.element[s]
   * @return concatenated elements
   */
  Operand<T> concat(@SuppressWarnings("unchecked") List<? extends T>... elements);

  /**
   * returns the size of operand
   *
   * @return size
   */
  int size();
}
