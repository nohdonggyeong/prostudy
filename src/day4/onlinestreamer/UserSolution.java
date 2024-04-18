package day4.onlinestreamer;

class UserSolution {
	static class Node {
		int sum;
		int min;
		int max;
		
		Node(int sum, int min, int max) {
			this.sum = sum;
			this.min = min;
			this.max = max;
		}
	}
	
	static int N;
	static int[] subscriber;
	static Node[] tree;
	
	static void build(int start, int end, int node) {
		if (start == end) {
			tree[node] = new Node(subscriber[start], subscriber[start], subscriber[start]);
			return;
		}
		
		int mid = (start + end) / 2;
		build(start, mid, node * 2);
		build(mid + 1, end, node * 2 + 1);
		
		tree[node] = new Node(tree[node * 2].sum + tree[node * 2 + 1].sum,
				Math.min(tree[node * 2].min, tree[node * 2 + 1].min),
				Math.max(tree[node * 2].max, tree[node * 2 + 1].max));
		return;
	}
	
	static void update(int start, int end, int node, int index, int diff) {
		if (index < start || index > end) {
			return;
		}
		
		if (start == end) {
			tree[node].sum += diff;
			tree[node].min += diff;
			tree[node].max += diff;
			return;
		}
		
		int mid = (start + end) / 2;
		update(start, mid, node * 2, index, diff);
		update(mid + 1, end, node * 2 + 1, index, diff);
		
		tree[node] = new Node(tree[node * 2].sum + tree[node * 2 + 1].sum,
				Math.min(tree[node * 2].min, tree[node * 2 + 1].min),
				Math.max(tree[node * 2].max, tree[node * 2 + 1].max));
		return;
	}
	
	static Node query(int start, int end, int node, int left, int right) {
		if (left > end || right < start) {
			return new Node(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
		}
		
		if (left <= start && end <= right) {
			return tree[node];
		}
		
		int mid = (start + end) / 2;
		Node leftChild = query(start, mid, node * 2, left, right);
		Node rightChild = query(mid + 1, end, node * 2 + 1, left, right);
		return new Node(leftChild.sum + rightChild.sum,
				Math.min(leftChild.min, rightChild.min),
				Math.max(leftChild.max, rightChild.max));
	}
	
	public void init(int N, int mSubscriber[]) {
		this.N = N;
		subscriber = new int[N + 1];
		System.arraycopy(mSubscriber, 0, subscriber, 1, N);
		
		tree = new Node[N * 4];
		build(1, N, 1);
	}

	public int subscribe(int mId, int mNum) {
		update(1, N, 1, mId, mNum);
		return subscriber[mId] += mNum;
	}

	public int unsubscribe(int mId, int mNum) {
		update(1, N, 1, mId, -mNum);
		return subscriber[mId] -= mNum;
	}

	public int count(int sId, int eId) {
		return query(1, N, 1, sId, eId).sum;
	}

	public int calculate(int sId, int eId) {
		Node node = query(1, N, 1, sId, eId);
		return node.max - node.min;
	}
}