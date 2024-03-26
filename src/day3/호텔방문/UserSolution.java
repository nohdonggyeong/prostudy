package day3.호텔방문;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class UserSolution {
	static class Node implements Comparable<Node> {
		int end;
		int dist;
		
		Node (int end, int dist) {
			this.end = end;
			this.dist = dist;
		}
		
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.dist, o.dist);
		}
	}
	
	static int N;
	static int[] nodesBrand;
	static int[] brandCnt;
	static ArrayList<ArrayList<Node>> adjList;
	static int[] dist;
	static boolean[] visited;
	
	static final int INF = 987_654_321;
	
    void init(int N, int mBrands[]) {
    	this.N = N;
    	nodesBrand = new int[N];
    	brandCnt = new int[50];
    	
    	dist = new int[N];
    	visited = new boolean[N];
    	
    	adjList = new ArrayList<ArrayList<Node>>();
    	for (int n = 0; n < N; n++) {
    		nodesBrand[n] = mBrands[n];
    		brandCnt[mBrands[n]]++;
    		adjList.add(new ArrayList<UserSolution.Node>());
    	}
    }
    
    void connect(int mHotelA, int mHotelB, int mDistance) {
    	adjList.get(mHotelA).add(new Node(mHotelB, mDistance));
    	adjList.get(mHotelB).add(new Node(mHotelA, mDistance));
    }
    
    int merge(int mHotelA, int mHotelB) {
    	int brandA = nodesBrand[mHotelA];
    	int brandB = nodesBrand[mHotelB];
    	
    	if (brandA == brandB) {
    		return brandCnt[brandA];
    	}
    	
    	for (int n = 0; n < N; n++) {
    		if (nodesBrand[n] == brandB) {
    			nodesBrand[n] = brandA;
    		}
    	}
    	
    	brandCnt[brandA] += brandCnt[brandB];
    	brandCnt[brandB] = 0;
        
    	return brandCnt[brandA];
    }
    
    int move(int mStart, int mBrandA, int mBrandB) {
    	int answer = 0;
    	
    	Arrays.fill(dist, INF);
    	dist[mStart] = 0;
    	
    	PriorityQueue<Node> pq = new PriorityQueue<UserSolution.Node>();
    	pq.add(new Node(mStart, 0));
    	
    	Arrays.fill(visited, false);
    	
    	while (!pq.isEmpty()) {
    		Node curNode = pq.remove();
    		int cur = curNode.end;
    		int dist = curNode.dist;
    		
    		if (visited[cur]) {
    			continue;
    		}
    		
    		visited[cur] = true;
    		
    		int brand = nodesBrand[cur];
    		if (dist != 0) {
    			if (brand == mBrandA) {
    				answer += dist;
    				mBrandA = -1;
    			} else if (brand == mBrandB) {
    				answer += dist;
    				mBrandB = -1;
    			}
    			
    			if (mBrandA == -1 && mBrandB == -1) {
    				break;
    			}
    		}
    		
    		for (Node adjNode : adjList.get(cur)) {
    			if (!visited[adjNode.end]) {
    				pq.add(new Node(adjNode.end, dist + adjNode.dist));
    			}
    		}
    	}
    	
        return answer;
    }    

}