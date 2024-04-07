package day4.onlinestreamer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	private final static int MAX_N = 200000;
	private final static int CMD_INIT = 100;
	private final static int CMD_SUBSCRIBE = 200;
	private final static int CMD_UNSUBSCRIBE = 300;
	private final static int CMD_COUNT = 400;
	private final static int CMD_CALCULATE = 500;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int n, mId, mNum, sId, eId;
		int[] mSubscriber = new int[MAX_N];
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					okay = true;
					n = Integer.parseInt(st.nextToken());
					for (int j = 0; j < n; ++j) {
						mSubscriber[j] = Integer.parseInt(br.readLine());
					}
					usersolution.init(n, mSubscriber);
					break;
				case CMD_SUBSCRIBE:
					mId = Integer.parseInt(st.nextToken());
					mNum = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.subscribe(mId, mNum);
					if (ret != ans)
						okay = false;
					break;
				case CMD_UNSUBSCRIBE:
					mId = Integer.parseInt(st.nextToken());
					mNum = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.unsubscribe(mId, mNum);
					if (ret != ans)
						okay = false;
					break;
				case CMD_COUNT:
					sId = Integer.parseInt(st.nextToken());
					eId = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.count(sId, eId);
					if (ret != ans)
						okay = false;
					break;
				case CMD_CALCULATE:
					sId = Integer.parseInt(st.nextToken());
					eId = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.calculate(sId, eId);
					if (ret != ans)
						okay = false;
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
		int TC, MARK;

		//System.setIn(new java.io.FileInputStream("input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		long end = System.currentTimeMillis();
		System.out.println("Runtime: " + (end - start) + "ms");
		br.close();
	}
}