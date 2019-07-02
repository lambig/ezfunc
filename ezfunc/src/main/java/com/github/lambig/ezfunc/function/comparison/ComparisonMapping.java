package com.github.lambig.ezfunc.function.comparison;

import com.github.lambig.ezfunc.function.Comparison;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.operand.Optional;

public abstract class ComparisonMapping {
	public static <O, P> Mapping<Comparison<? super P>, Comparison<? super O>> escalate(
			Mapping<O, P> extraction) {
		return new Mapping<Comparison<? super P>, Comparison<? super O>>() {
			@Override
			public Comparison<O> map(Comparison<? super P> current, int index) {
				return new Comparison<O>() {
					@Override
					public Order compare(O a, O b) {
						return current.compare(
								Optional.of(a).map(extraction).get(),
								Optional.of(b).map(extraction).get());
					}

				};
			}
		};
	}
}
