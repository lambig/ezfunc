package com.github.lambig.ezfunc.function.comparator;

import java.util.Comparator;

import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.operand.Optional;

public abstract class ComparatorMapping {
	public static <O, P> Mapping<Comparator<? super P>, Comparator<? super O>> escalate(
			Mapping<O, P> extraction) {
		return new Mapping<Comparator<? super P>, Comparator<? super O>>() {
			@Override
			public Comparator<O> map(Comparator<? super P> current, int index) {
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
