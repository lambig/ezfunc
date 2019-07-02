package com.github.lambig.ezfunc.function;

import java.util.Comparator;

import com.github.lambig.ezfunc.function.predicate.function.PredicateMapping;
import com.github.lambig.ezfunc.operand.Optional;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;

public abstract class Comparison<T> {
	public enum Order {
		FORWARD(-1), BACKWARD(1), TIE(0);
		private static final Mapping<Order, Integer> EXTRACTION = new Mapping<Order, Integer>() {
			@Override
			public Integer map(Order current, int index) {
				return current.value;
			}
		};
		private int value;

		private Order(int value) {
			this.value = value;
		}

		public static Order valueOf(int value) {
			return ListOperands
					.of(Order.values())
					.first(Optional
							.of(value)
							.map(PredicateMapping.having(Order.EXTRACTION))
							.get());
		}
	}

	public abstract Order compare(T a, T b);

	public Comparator<T> toComparator() {
		return new Comparator<T>() {
			@Override
			public int compare(T arg0, T arg1) {
				return Comparison.this.compare(arg0, arg1).value;
			}
		};
	}
}
