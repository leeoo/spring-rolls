package com.exam.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TextUtil {

	public static MatchPair[] LCS_DN_refined(String[] txt1, String[] txt2) {
		List<int[]> list = new ArrayList<int[]>();

		int M = txt1.length;
		int N = txt2.length;
		int MAX = M + N;

		int[] cur = (int[]) null;
		int[] pre = new int[1];

		pre[0] = 0;

		for (int D = 0; D <= MAX; D++) {
			list.add(pre);
			cur = new int[D + 1];

			for (int k = -1 * D; k <= D; k += 2) {
				int x;
				if ((k == -1 * D)
						|| ((k != D) && (pre[(k - 1 + D - 1 >> 1)] < pre[(k + 1
								+ D - 1 >> 1)]))) {
					x = pre[(k + 1 + D - 1 >> 1)];
				} else {
					x = pre[(k - 1 + D - 1 >> 1)] + 1;
				}
				int y = x - k;
				while ((x < txt1.length) && (y < txt2.length)
						&& (txt1[x].equals(txt2[y]))) {
					x++;
					y++;
				}
				cur[(k + D >> 1)] = x;

				if ((x != txt1.length) || (y != txt2.length)) {
					continue;
				}
				List<MatchPair> ret = new ArrayList<MatchPair>();
				do {
					pre = (int[]) list.get(D);
					int k1;
					int x1;
					if ((k == -1 * D)
							|| ((k != D) && (pre[(k - 1 + D - 1 >> 1)] < pre[(k
									+ 1 + D - 1 >> 1)]))) {
						x1 = pre[(k + 1 + D - 1 >> 1)];
						k1 = k + 1;
					} else {
						x1 = pre[(k - 1 + D - 1 >> 1)];
						k1 = k - 1;
					}

					while ((x > x1) && (x - k > x1 - k1)) {
						x--;
						ret.add(new MatchPair(x, x - k));
					}
					k = k1;
					x = x1;

					D--;
				} while (D >= 0);
				Collections.reverse(ret);
				return (MatchPair[]) ret.toArray(new MatchPair[0]);
			}

			pre = cur;
		}

		return null;
	}

	public static class MatchPair {
		public int x;
		public int y;

		MatchPair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
