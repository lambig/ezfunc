package com.github.lambig.ezfunc.function;

import java.util.Comparator;

public abstract class Comparison<T> {
	public enum Order {
		FORWARD(-1), BACKWARD(1), TIE(0);
		private int value;

		private Order(int value) {
			this.value = value;
		}

		private int toValue() {
			return value;
		}
	}

	public abstract Order compare(T a, T b);

	public Comparator<T> toComparator() {
		return new Comparator<T>() {
			@Override
			public int compare(T arg0, T arg1) {
				return Comparison.this.compare(arg0, arg1).toValue();
			}
		};
	}
}
