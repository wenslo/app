package com.github.wenslo.kotlin.app;

import java.util.Arrays;

/**
 * @author: 温海林
 * 2018年02月28日15:52:14
 */
public class SA {
	public static void main(final String... args) {
		final int[][] array = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
		printCurrent(0, array, new int[array.length]);
	}

	public static void printCurrent(final int position, final int[][] array, final int[] buffer) {
		final int[] current = array[position];
		final boolean isLast = position == (array.length - 1);
		for (final int i : current) {
			buffer[position] = i;
			//如果是最后一个,那么打印
			if (isLast) {
				System.out.println(Arrays.toString(buffer));
			} else {
				//进入下一层
				printCurrent(position + 1, array, buffer);
			}
		}
	}
}
