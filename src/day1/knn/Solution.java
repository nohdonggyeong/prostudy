package day1.knn;

import java.util.Scanner;

class Solution
{
	private static final int CMD_INIT				= 100;
	private static final int CMD_ADD_SAMPLE			= 200;
	private static final int CMD_DELETE_SAMPLE		= 300;
	private static final int CMD_PREDICT			= 400;

	private static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) throws Exception
	{
		int Q;
		int K, L;
		int mID, mX, mY, mC;
		
		int ret = -1, ans;

		Q = sc.nextInt();
		
		boolean okay = false;
		
		for (int q = 0; q < Q; ++q)
		{
			int cmd = sc.nextInt();
			
			switch(cmd)
			{
			case CMD_INIT:
				K = sc.nextInt();
				L = sc.nextInt();
				usersolution.init(K, L);
				okay = true;
				break;
			case CMD_ADD_SAMPLE:
				mID = sc.nextInt();
				mX = sc.nextInt();
				mY = sc.nextInt();
				mC = sc.nextInt();
				usersolution.addSample(mID, mX, mY, mC);
				break;
			case CMD_DELETE_SAMPLE:
				mID = sc.nextInt();
				usersolution.deleteSample(mID);
				break;
			case CMD_PREDICT:
				mX = sc.nextInt();
				mY = sc.nextInt();
				ret = usersolution.predict(mX, mY);
				ans = sc.nextInt();
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
	
	public static void main(String[] args) throws Exception
	{
		//System.setIn(new java.io.FileInputStream("input.txt"));

		Scanner sc = new Scanner(System.in);
	
		int TC = sc.nextInt();
		int MARK = sc.nextInt();
		
		for (int testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		sc.close();
	}
}