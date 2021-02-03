package com.github.lambig.ezfunc.function.string;

import com.github.lambig.ezfunc.function.Reduction;

public class StringReduction {
	public static final Reduction<String, String> join(String delimiter) {
		return new Reduction<String, String>() {
			@Override
			public String reduce(String accum, String current, int index) {
				return accum + current;
			}
		};
	}

	public static final Reduction<String, StringBuilder> joinToBuilder(String delimiter) {
		return new Reduction<String, StringBuilder>() {
			@Override
			public StringBuilder reduce(StringBuilder accum, String current, int index) {
				return accum.append(current);
			}
		};
	}
}
