package com.exam.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextUtil {

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

	public static MatchPair[] LCS(String[] txt1, String[] txt2) {
		int[][] matchCount = (int[][]) null;
		int[][] direction = (int[][]) null;

		matchCount = new int[txt1.length + 1][txt2.length + 1];
		direction = new int[txt1.length + 1][txt2.length + 1];

		for (int i = 0; i <= txt1.length; i++) {
			matchCount[i][0] = 0;
			direction[i][0] = 0;
		}

		for (int i = 0; i <= txt2.length; i++) {
			matchCount[0][i] = 0;
			direction[0][i] = 0;
		}

		for (int plus = 2; plus <= txt1.length + txt2.length; plus++) {
			for (int x = Math.max(1, plus - txt2.length); x <= Math.min(
					plus - 1, txt1.length); x++) {
				int y = plus - x;
				int count = 0;
				int dir = 0;
				if (txt1[(x - 1)].equals(txt2[(y - 1)])) {
					count = matchCount[(x - 1)][(y - 1)] + 1;
					dir = 2;
				} else if (matchCount[(x - 1)][y] > matchCount[x][(y - 1)]) {
					count = matchCount[(x - 1)][y];
					dir = 1;
				} else {
					count = matchCount[x][(y - 1)];
					dir = 3;
				}

				matchCount[x][y] = count;
				direction[x][y] = dir;
			}
		}

		int x = txt1.length;
		int y = txt2.length;
		List<MatchPair> ret = new ArrayList<MatchPair>();
		int dir = direction[x][y];
		while (dir != 0) {
			if (dir == 2) {
				ret.add(new MatchPair(x - 1, y - 1));
				x--;
				y--;
			} else if (dir == 1) {
				x--;
			} else {
				y--;
			}

			dir = direction[x][y];
		}

		Collections.reverse(ret);
		return (MatchPair[]) ret.toArray(new MatchPair[0]);
	}

	public static MatchPair[] LCS_DN(String[] txt1, String[] txt2) {
		List<int[]> list = new ArrayList<int[]>();

		int M = txt1.length;
		int N = txt2.length;
		int MAX = M + N;

		int[] cur = (int[]) null;
		int[] pre = new int[2 * MAX + 1];

		pre[(1 + MAX)] = 0;

		for (int D = 0; D <= MAX; D++) {
			list.add(pre);
			cur = new int[2 * MAX + 1];

			for (int k = -1 * D; k <= D; k += 2) {
				int x;
				if ((k == -1 * D)
						|| ((k != D) && (pre[(k - 1 + MAX)] < pre[(k + 1 + MAX)]))) {
					x = pre[(k + 1 + MAX)];
				} else {
					x = pre[(k - 1 + MAX)] + 1;
				}
				int y = x - k;
				while ((x < txt1.length) && (y < txt2.length)
						&& (txt1[x].equals(txt2[y]))) {
					x++;
					y++;
				}
				cur[(k + MAX)] = x;

				if ((x != txt1.length) || (y != txt2.length)) {
					continue;
				}
				List<MatchPair> ret = new ArrayList<MatchPair>();
				do {
					pre = (int[]) list.get(D);
					int k1;
					int x1;
					if ((k == -1 * D)
							|| ((k != D) && (pre[(k - 1 + MAX)] < pre[(k + 1 + MAX)]))) {
						x1 = pre[(k + 1 + MAX)];
						k1 = k + 1;
					} else {
						x1 = pre[(k - 1 + MAX)];
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
