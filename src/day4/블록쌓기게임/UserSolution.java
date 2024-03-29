package day4.블록쌓기게임;

class UserSolution {
	static int C;
	static long total;
	int[] tree, minTree, maxTree;
	int[] lazy;
	
	static final int MOD = 1_000_000;
	
    void init(int C) {
    	this.C = C;
    	total = 0;
    	
    	tree = new int[C * 4];
    	minTree = new int[C * 4];
    	maxTree = new int[C * 4];
    	lazy = new int[C * 4];
    }

    Solution.Result dropBlocks(int mCol, int mHeight, int mLength) {
        Solution.Result ret = new Solution.Result();
        return ret;
    }

}