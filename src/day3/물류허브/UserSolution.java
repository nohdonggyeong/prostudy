package day3.물류허브;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

class UserSolution {
	
	class Node implements Comparable<Node> {
		int end;
		int weight;
		
		Node(int end, int weight) {
			this.end = end;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.weight, o.weight);
		}
	}
	
	static HashMap<Integer, ArrayList<Node>> goHm;
	static HashMap<Integer, ArrayList<Node>> backHm;
	
	public int init(int N, int sCity[], int eCity[], int mCost[]) {
		goHm = new HashMap<Integer, ArrayList<Node>>();
		backHm = new HashMap<Integer, ArrayList<Node>>();
		
		for (int i = 0; i < N; i++) {
			add(sCity[i], eCity[i], mCost[i]);
		}
		
		return goHm.size();
	}

	public void add(int sCity, int eCity, int mCost) {
		if (!goHm.containsKey(sCity)) {
			goHm.put(sCity, new ArrayList<>());
		}
		goHm.get(sCity).add(new Node(eCity, mCost));
		
		if (!backHm.containsKey(eCity)) {
			backHm.put(eCity, new ArrayList<>());
		}
		backHm.get(eCity).add(new Node(sCity, mCost));
		
		return;
	}

	public int cost(int mHub) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(mHub, 0));
		
		HashMap<Integer, Boolean> visited = new HashMap<>();

		int totalCost = 0;
		int cnt = 0;
		int goHmSize = goHm.size();
		
		while (!pq.isEmpty()) {
			Node curNode = pq.poll();
			int cur = curNode.end;
			
			if (visited.get(cur) != null) {
				continue;
			}
			
			visited.put(cur, true);
			
			totalCost += curNode.weight;
			cnt++;
			
			if (cnt == goHmSize) {
				break;
			}
			
			for (Node adjNode : goHm.get(cur)) {
				if (visited.get(adjNode.end) != null) {
					continue;
				}
				
				pq.add(new Node(adjNode.end, curNode.weight + adjNode.weight));
			}
		}
		
		pq.clear();
		visited.clear();
		cnt = 0;
		
		pq.add(new Node(mHub, 0));
		
		while (!pq.isEmpty()) {
			Node curNode = pq.remove();
			int cur = curNode.end;
			
			if (visited.get(cur) != null) {
				continue;
			}
			
			visited.put(cur, true);
			
			totalCost += curNode.weight;
			cnt++;
			
			if (cnt == goHmSize) {
				break;
			}
			
			for (Node adjNode : backHm.get(cur)) {
				if (visited.get(adjNode.end) != null) {
					continue;
				}
				
				pq.add(new Node(adjNode.end, curNode.weight + adjNode.weight));
			}
		}
		
		return totalCost;
	}
}