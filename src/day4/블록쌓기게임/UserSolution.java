package day4.블록쌓기게임;

class UserSolution {
	static class Node {
		int sum;
		int max;
		int min;
		
		Node(int sum, int max, int min) {
			this.sum = sum;
			this.max = max;
			this.min = min;
		}
	}
	
	static int C;
	static long total;
	static Node[] tree;
	static int[] lazy;
	
	static final int MOD = 1_000_000;

	static void create(int start, int end, int node) {
		if (start == end) {
			tree[node] = new Node(0, 0, 0);
			return;
		}
		
		int mid = (start + end) / 2;
		create(start, mid, node * 2);
		create(mid + 1, end, node * 2 + 1);
		
		Node leftNode = tree[node * 2];
		Node rightNode = tree[node * 2 + 1];
		tree[node] = new Node(leftNode.sum + rightNode.sum,
				Math.max(leftNode.max, rightNode.max),
				Math.min(leftNode.min, rightNode.min));
		return;
	}
	
    static void lazy_update(int start, int end, int node) {
    	if (lazy[node] != 0) {
    		tree[node].sum += (end - start + 1) * lazy[node];
    		tree[node].min += lazy[node];
    		tree[node].max += lazy[node];
    		
    		if (start != end) {
    			lazy[node * 2] += lazy[node];
    			lazy[node * 2 + 1] += lazy[node];
    		}
    		
    		lazy[node] = 0;
    	}
    }
    
    static void update(int start, int end, int node, int left, int right, int diff) {
    	lazy_update(start, end, node);
    	
    	if (right < start || left > end) {
    		return;
    	}
    	
    	if (left <= start && right >= end) {
    		tree[node].sum += (end - start + 1) * diff;
    		tree[node].max += diff;
    		tree[node].min += diff;
    		
    		if (start != end) {
    			lazy[node * 2] += diff;
    			lazy[node * 2 + 1] += diff;
    		}
    		
    		return;
    	}
    	
    	int mid = (start + end) / 2;
    	update(start, mid, node * 2, left, right, diff);
    	update(mid + 1, end, node * 2 + 1, left, right, diff);
    	
    	tree[node].sum = tree[node * 2].sum + tree[node * 2 + 1].sum;
    	tree[node].max = Math.max(tree[node * 2].max, tree[node * 2 + 1].max);
    	tree[node].min = Math.min(tree[node * 2].min, tree[node * 2 + 1].min);
    }
    
    void init(int C) {
    	this.C = C;
    	total = 0;
    	
    	tree = new Node[C * 4];
    	lazy = new int[C * 4];
    	create(0, C - 1, 1);
    }

    Solution.Result dropBlocks(int mCol, int mHeight, int mLength) {
    	update(0, C - 1, 1, mCol, mCol + mLength - 1, mHeight);
    	total += mLength * mHeight;
    	
        Solution.Result ret = new Solution.Result();
        ret.top = tree[1].max - tree[1].min;
        ret.count = (int) ((total - tree[1].min * C) % MOD);
        
        return ret;
    }

}