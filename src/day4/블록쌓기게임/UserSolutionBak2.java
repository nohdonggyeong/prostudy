package day4.블록쌓기게임;

class UserSolutionBak2 {
	final int MOD = 1000000;
	int[] segTree ;
	int[] lazy;
	int[] maxTree;
	int[] minTree;
  
	long total;
	int C;
    void init(int C) {
        this.C = C;
        total = 0;
        segTree = new int[C*4];
        maxTree = new int[C*4];
        minTree = new int[C*4];
        lazy = new int[C*4];
    }
    void lazy_update(int node, int start, int end) {
	    //lazy 값이 존재하면 수정
	    if(lazy[node] != 0) {
	        segTree[node] = segTree[node] +(end-start+1)*lazy[node];
	        maxTree[node] += lazy[node];
	        minTree[node] += lazy[node];
	        if(start != end) {
	            lazy[node*2] += lazy[node];
	            lazy[node*2+1] += lazy[node];
	       }

	        lazy[node] = 0;

	    }

	}
	void update(int node, int start, int end, int left, int right, int diff) {
	    lazy_update(node, start, end);
	    if(right<start || left>end) return;
	    if(left<=start && end<=right) {
	    	segTree[node] += (end-start+1)*diff;
	        maxTree[node] += diff;
	        minTree[node] += diff;
	        if(start != end) {
	            lazy[node*2] += diff;
	            lazy[node*2+1] += diff;
	        }
	        return;
	    }
	    int mid = (start+end)/2;
	    update(node*2, start, mid, left, right, diff);
	    update(node*2+1, mid+1, end, left, right, diff);
	    segTree[node] = segTree[node*2] + segTree[node*2+1];
	    maxTree[node] = Math.max(maxTree[node*2], maxTree[node*2+1]);
	    minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
	}

  
  
    Solution.Result dropBlocks(int mCol, int mHeight, int mLength) {
        update(1, 0, C-1, mCol, mCol + mLength - 1, mHeight);
        total += mLength * mHeight;
        Solution.Result ret = new Solution.Result();
        ret.top = maxTree[1] - minTree[1];
        ret.count = (int) ((total - minTree[1] * C ) % MOD);
        return ret;
    }
  
  
}
  
  
