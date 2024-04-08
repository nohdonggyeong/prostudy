package day4.onlinestreamer;

class UserSolutionBak2 {
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
	static int[] subscribers;
	static Node[] tree;
	
	static void setTree(int start, int end, int node) {
		if (start == end) {
			tree[node] = new Node(subscribers[start], subscribers[start], subscribers[start]);
			return;
		}
		
		int mid = (start + end) / 2;
		setTree(start, mid, node * 2);
		setTree(mid + 1, end, node * 2 + 1);
		
		Node leftChild = tree[node * 2];
		Node rightChild = tree[node * 2 + 1];
		tree[node] = new Node(leftChild.sum + rightChild.sum,
				Math.min(leftChild.min, rightChild.min),
				Math.max(leftChild.max, rightChild.max));
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
		update(start, mid , node * 2, index, diff);
		update(mid + 1, end, node * 2 + 1, index, diff);
		
		Node leftChild = tree[node * 2];
		Node rightChild = tree[node * 2 + 1];
		tree[node].sum = leftChild.sum + rightChild.sum;
		tree[node].min = Math.min(leftChild.min, rightChild.min);
		tree[node].max = Math.max(leftChild.max, rightChild.max);
		return;
	}
	
	static Node query(int start, int end, int node, int left, int right) {
		if (right < start || end < left) {
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
		subscribers = new int[N + 1];
		System.arraycopy(mSubscriber, 0, subscribers, 1, N);
		
		tree = new Node[N * 4];
		setTree(1, N, 1);
		
		return;
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
		return query(1, N, 1, sId, eId).sum;
	}

	public int calculate(int sId, int eId) {
		Node node = query(1, N, 1, sId, eId);
		return node.max - node.min;
	}
}