package day4.onlinestreamer;

class UserSolution {
<<<<<<< HEAD
	public void init(int N, int mSubscriber[]) {
	}

	public int subscribe(int mId, int mNum) {
		return -1;
	}

	public int unsubscribe(int mId, int mNum) {
		return -1;
	}

	public int count(int sId, int eId) {
		return -1;
	}

	public int calculate(int sId, int eId) {
		return -1;
	}
=======
    static int N;
    static int[] subscribers;
    static int[] sumTree, minTree, maxTree;
     
    static void setTree(int start, int end, int node) {
        if (start == end) {
            sumTree[node] = subscribers[start];
            minTree[node] = subscribers[start];
            maxTree[node] = subscribers[start];
            return;
        }
         
        int mid = (start + end) / 2;
        setTree(start, mid, node * 2);
        setTree(mid + 1, end, node * 2 + 1);
         
        sumTree[node] = sumTree[node * 2] + sumTree[node * 2 + 1];
        minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
        maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
        return;
    }
     
    static void update(int start, int end, int node, int index, int diff) {
        if (index < start || index > end) {
            return;
        }
         
        if (start == end) {
            sumTree[node] += diff;
            minTree[node] += diff;
            maxTree[node] += diff;
            return;
        }
         
        int mid = (start + end) / 2;
        update(start, mid, node * 2, index, diff);
        update(mid + 1, end, node * 2 + 1, index, diff);
         
        sumTree[node] = sumTree[node * 2] + sumTree[node * 2 + 1];
        minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
        maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
        return;
    }
     
    static int querySum(int start, int end, int node, int left, int right) {
        if (left > end || right < start) {
            return 0;
        }
         
        if (left <= start && end <= right) {
            return sumTree[node];
        }
         
        int mid = (start + end) / 2;
        return querySum(start, mid, node * 2, left, right) + querySum(mid + 1, end, node * 2 + 1, left, right);
    }
     
    static int queryMin(int start, int end, int node, int left, int right) {
        if (left > end || right < start) {
            return Integer.MAX_VALUE;
        }
         
        if (left <= start && end <= right) {
            return minTree[node];
        }
         
        int mid = (start + end) / 2;
        return Math.min(queryMin(start, mid, node * 2, left, right), queryMin(mid + 1, end, node * 2 + 1, left, right));
    }
     
    static int queryMax(int start, int end, int node, int left, int right) {
        if (left > end || right < start) {
            return Integer.MIN_VALUE;
        }
         
        if (left <= start && end <= right) {
            return maxTree[node];
        }
         
        int mid = (start + end) / 2;
        return Math.max(queryMax(start, mid, node * 2, left, right), queryMax(mid + 1, end, node * 2 + 1, left, right));
    }
     
    public void init(int N, int mSubscriber[]) {
        this.N = N;
         
        subscribers = new int[N + 1];
        System.arraycopy(mSubscriber, 0, subscribers, 1, N);
         
        sumTree = new int[N * 4];
        minTree = new int[N * 4];
        maxTree = new int[N * 4];
        setTree(1, N, 1);
    }
 
    public int subscribe(int mId, int mNum) {
        update(1, N, 1, mId, mNum);
        return subscribers[mId] += mNum;
    }
 
    public int unsubscribe(int mId, int mNum) {
        update(1, N, 1, mId, -mNum);
        return subscribers[mId] -= mNum;
    }
 
    public int count(int sId, int eId) {
        return querySum(1, N, 1, sId, eId);
    }
 
    public int calculate(int sId, int eId) {
        return queryMax(1, N, 1, sId, eId) - queryMin(1, N, 1, sId, eId);
    }
>>>>>>> 8ad255d9ae581eebb83fef307c7821d9dfc64054
}