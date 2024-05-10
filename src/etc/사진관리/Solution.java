package etc.사진관리;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	private final static int CMD_INIT = 1;
	private final static int CMD_SAVE = 2;
	private final static int CMD_FILTER = 3;
	private final static int CMD_DELETE = 4;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int N, M, K, index;
		String[] mPictureList;
		String mFilter;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					N = Integer.parseInt(st.nextToken());
					mPictureList = new String[st.countTokens()];
					index = 0;
					while (st.hasMoreTokens()) {
						mPictureList[index++] = st.nextToken();
					}
					usersolution.init(N, mPictureList);
					okay = true;
					break;
				case CMD_SAVE:
					M = Integer.parseInt(st.nextToken());
					mPictureList = new String[st.countTokens()];
					index = 0;
					while (st.hasMoreTokens()) {
						mPictureList[index++] = st.nextToken();
					}
					usersolution.savePicture(M, mPictureList);
					okay = true;
					break;
				case CMD_FILTER:
					mFilter = st.nextToken();
					K = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.filterPicture(mFilter, K);
					if (ret != ans)
						okay = false;
					break;
				case CMD_DELETE:
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.deleteOldest();
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

		br.close();
	}
}