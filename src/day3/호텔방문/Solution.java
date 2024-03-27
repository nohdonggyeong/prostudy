package day3.호텔방문;

import java.util.Scanner;

class Solution {
    private static Scanner sc;
    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_CONNECT = 200;
    private final static int CMD_MERGE = 300;
    private final static int CMD_MOVE = 400;

    private static boolean run() throws Exception {

        int query_num = sc.nextInt();
		int ans;
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            int query = sc.nextInt();

            if (query == CMD_INIT) {
                int N = sc.nextInt();
                int mBrands[] = new int[N];
                for (int i = 0; i < N; i++){
                    mBrands[i] = sc.nextInt();
                }
                usersolution.init(N, mBrands);
                ok = true;
            } else if (query == CMD_CONNECT) {
                int mHotelA = sc.nextInt();
                int mHotelB = sc.nextInt();
                int mDistance = sc.nextInt();
                usersolution.connect(mHotelA, mHotelB, mDistance);
            } else if (query == CMD_MERGE) {
                int mHotelA = sc.nextInt();
                int mHotelB = sc.nextInt();
                int ret = usersolution.merge(mHotelA, mHotelB);
                ans = sc.nextInt();
                if (ans != ret) {
                    ok = false;
                }
            } else if (query == CMD_MOVE) {
                int mStart = sc.nextInt();
                int mBrandA = sc.nextInt();
                int mBrandB = sc.nextInt();
                int ret = usersolution.move(mStart, mBrandA, mBrandB);
                ans = sc.nextInt();
                if (ans != ret) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        // System.setIn(new java.io.FileInputStream("input.txt"));
        sc = new Scanner(System.in);
        T = sc.nextInt();
        MARK = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }
        sc.close();
    }
}
