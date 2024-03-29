package day5.기지국설치;

import java.util.HashMap;
import java.util.TreeSet;

class UserSolution {
	static class Node implements Comparable<Node> {
		int id;
		int loc;
		
		Node (int id, int loc) {
			this.id = id;
			this.loc = loc;
		}
		
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.loc, o.loc);
		}
	}
	
	static HashMap<Integer, Node> nodeMap = new HashMap<>();
	static TreeSet<Node> nodeSet = new TreeSet<>();
	
	public void init(int N, int mId[], int mLocation[]) {
		nodeMap.clear();
		nodeSet.clear();
		
		Node node;
		for (int i = 0; i < N; i++) {
			node = new Node(mId[i], mLocation[i]);
			nodeMap.put(mId[i], node);
			nodeSet.add(node);
		}
		
		return;
	}

	public int add(int mId, int mLocation) {
		if (nodeMap.containsKey(mId)) {
			nodeSet.remove(nodeMap.get(mId));
		}
		Node node = new Node(mId, mLocation);
		nodeMap.put(mId, node);
		nodeSet.add(node);
		
		return nodeMap.size();
	}

	public int remove(int mStart, int mEnd) {
		Node node = new Node(0, mStart);
		Node temp;
		while (true) {
			temp = nodeSet.higher(node);
			if (temp == null || temp.loc > mEnd) {
				break;
			}
			nodeSet.remove(temp);
			nodeMap.remove(temp.id);
		}
		
		return nodeMap.size();
	}

	int install(int M) {
		int start = 1;
		int end = nodeSet.last().loc;
		
		int answer = 0;
		int mid, first, cur, installCnt;
		while (start <= end) {
			mid = (start + end) / 2;
			
			installCnt = 1;
			first = nodeSet.first().loc;
			for (Node node : nodeSet) {
				cur = node.loc;
				if (cur - first >= mid) {
					++installCnt;
					first = cur;
				}
			}
			
			if (installCnt < M) {
				end = mid - 1;
			} else {
				answer = Math.max(answer, mid);
				start = mid + 1;
			}
		}
		
		return answer;
	}
}