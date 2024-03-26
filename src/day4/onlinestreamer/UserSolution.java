package day4.onlinestreamer;

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
	
	static int N;
	static int[] subscribers;
	static Node[] tree;
	
	static void create(int start, int end, int node) {
		if (start == end) {
			tree[node] = new Node(subscribers[start], subscribers[start], subscribers[start]);
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
	
	static void update(int start, int end, int node, int index, int diff) {
		if (index < start || index > end) {
			return;
		}
		
		if (start == end) {
			tree[node].sum += diff;
			tree[node].max += diff;
			tree[node].min += diff;
			return;
		}
		
		int mid = (start + end) / 2;
		update(start, mid, node * 2, index, diff);
		update(mid + 1, end, node * 2 + 1, index, diff);
		
		Node leftNode = tree[node * 2];
		Node rightNode = tree[node * 2 + 1];
		tree[node].sum = leftNode.sum + rightNode.sum;
		tree[node].max = Math.max(leftNode.max, rightNode.max);
		tree[node].min = Math.min(leftNode.min, rightNode.min);
		return;
	}
	
	static Node query(int start, int end, int node, int left, int right) {
		if (end < left || start > right) {
			return new Node(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		}
		
		if (start >= left && end <= right) {
			return tree[node];
		}
		
		int mid = (start + end) / 2;
		Node leftNode = query(start, mid, node * 2, left, right);
		Node rightNode = query(mid + 1, end, node * 2 + 1, left, right);
		
		Node answer = new Node(leftNode.sum + rightNode.sum,
				Math.max(leftNode.max, rightNode.max),
				Math.min(leftNode.min, rightNode.min));
		return answer;
	}
	
	public void init(int N, int mSubscriber[]) {
		this.N = N;
		
		subscribers = new int[N + 1];
		System.arraycopy(mSubscriber, 0, subscribers, 1, N);
		
		tree = new Node[N * 4];
		create(1, N, 1);
		
		return;
	}

	public int subscribe(int mId, int mNum) {
		update(1, N, 1, mId, mNum);
		subscribers[mId] += mNum;

		return subscribers[mId];
	}

	public int unsubscribe(int mId, int mNum) {
		update(1, N, 1, mId, -mNum);
		subscribers[mId] -= mNum;
		
		return subscribers[mId];
	}

	public int count(int sId, int eId) {
		Node answer = query(1, N, 1, sId, eId);
		
		return answer.sum;
	}

	public int calculate(int sId, int eId) {
		Node answer = query(1, N, 1, sId, eId);
		
		return answer.max - answer.min;
	}
}