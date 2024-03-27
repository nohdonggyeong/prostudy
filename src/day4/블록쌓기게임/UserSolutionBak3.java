package day4.블록쌓기게임;

class UserSolutionBak3 {
	class Block {
	    int start, end, max, min, h;
	    Block left, right;
	    Block(int mStart, int mEnd) {
	        start = mStart;
	        end = mEnd;
	    }
	}
	final int MOD = 1000000;
    Block root;
    long total, C;
  
  
    void init(int C) {
        this.C = C;
        total = 0;
        root = new Block(0, C - 1);
    }
  
  
    void update(Block b, int start, int end, int h) {
        if (b.end < start || b.start > end)
            return;
        if (b.start >= start && b.end <= end) {
            b.h += h;
            return;
        }
        if (b.left == null) {
            int mid = (b.start + b.end) / 2;
            b.left = new Block(b.start, mid);
            b.right = new Block(mid + 1, b.end);
        }
        update(b.left, start, end, h);
        update(b.right, start, end, h);
        b.max = Math.max(b.left.max + b.left.h, b.right.max + b.right.h);
        b.min = Math.min(b.left.min + b.left.h, b.right.min + b.right.h);
    }
  
  
    Solution.Result dropBlocks(int mCol, int mHeight, int mLength) {
        update(root, mCol, mCol + mLength - 1, mHeight);
        total += mLength * mHeight;
        Solution.Result ret = new Solution.Result();
        ret.top = root.max - root.min;
        ret.count = (int) ((total - (root.min + root.h) * C ) % MOD);
        return ret;
    }
  
  
}
  
  
