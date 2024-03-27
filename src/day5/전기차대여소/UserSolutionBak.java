package day5.전기차대여소;

//*
import java.util.*;
class UserSolutionBak {

	class Spot{
	    int row;
	    int col;
	    int cost;
	    Spot(int row, int col, int cost){
	    	this.row = row;
	    	this.col = col;
	    	this.cost = cost;
	    }		
	}
	
	
	class ChargingSpot implements Comparable<ChargingSpot>{
		int mID;
		int cost;
		ChargingSpot(int mID, int cost){
			this.mID = mID;
			this.cost = cost;
		}
		@Override
		public int compareTo(ChargingSpot o) {
			return Integer.compare(cost, o.cost);
		}
	}
	
	final int SPOT_ID = 2;
	int[][] map;
	int[][] visit;
	PriorityQueue<ChargingSpot> cSpotPQ;
	ArrayList<ArrayList<ChargingSpot>> cSpotList;
	int[] dist;

	int N, maxRange, visitCnt;

	void init(int N, int mRange, int mMap[][]) {
	    this.N = N;
	    maxRange = mRange;
	    map = new int[N][N];
	    visit = new int[N][N];
	    dist = new int[200];
	    cSpotList = new ArrayList<>(200);
	    for(int i=0; i<200; ++i) {
	    	cSpotList.add(new ArrayList<>());
	    }
	    cSpotPQ = new PriorityQueue<>();
	    
	    for(int i=0;i<N; ++i) {
	    	System.arraycopy(mMap[i], 0, map[i], 0, N);
	    }
	   
	    visitCnt = 0;
	}

	void setList(int mID, int mRow, int mCol) {
	    int dRow[] = { -1, 1, 0, 0 };
	    int dCol[] = { 0, 0, -1, 1 };
	
	    Queue<Spot> spotQueue = new LinkedList<>();
	    spotQueue.offer(new Spot(mRow, mCol, 0));
	    visitCnt++;
	
	    visit[mRow][mCol] = visitCnt;
	
	    while (!spotQueue.isEmpty()) {
	        Spot cur = spotQueue.poll();
	
	        int spotId = map[cur.row][cur.col] - SPOT_ID;
	        if (map[cur.row][cur.col] >= SPOT_ID && spotId != mID) {
	        	cSpotList.get(spotId).add(new ChargingSpot(mID , cur.cost));
	        	cSpotList.get(mID).add(new ChargingSpot( spotId , cur.cost));
	        }
	
	        if (cur.cost >= maxRange) continue;
	
	        for (int i = 0; i < 4; ++i) {
	            int nextRow = cur.row + dRow[i];
	            int nextCol = cur.col + dCol[i];
	            int cost = cur.cost + 1;
//	            if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) continue;
	            if (nextRow == -1 || nextRow == N || nextCol == -1 || nextCol == N) continue;
	            if (visit[nextRow][nextCol] == visitCnt || map[nextRow][nextCol] == 1) continue;
	
	            visit[nextRow][nextCol] = visitCnt;
	            spotQueue.offer(new Spot(nextRow , nextCol , cost));
	        }
	    }
	}

	void add(int mID, int mRow, int mCol) {
	    map[mRow][mCol] = mID + SPOT_ID;	
	    setList(mID, mRow, mCol);
	}
	
	int distance(int mFrom, int mTo) {
		cSpotPQ.clear();
	    Arrays.fill(dist, Integer.MAX_VALUE);
	    dist[mFrom] = 0;
	    cSpotPQ.offer(new ChargingSpot(mFrom, 0));
//	    boolean[] visited = new boolean[200];
	    while (!cSpotPQ.isEmpty()) {
	    	ChargingSpot temp = cSpotPQ.poll();
	        int spot = temp.mID;
	        int cost = temp.cost;
	        //if(visited[spot]) continue;
	        if (spot == mTo) return cost;
	        //visited[spot] = true;
	        for (ChargingSpot charging : cSpotList.get(spot)) {
	            int val = cost + charging.cost;
	            if (dist[charging.mID] > val) {// && !visited[charging.mID]) {
	                dist[charging.mID] = val;
	                cSpotPQ.offer(new ChargingSpot(charging.mID , dist[charging.mID]));
	            }
	        }
	    }
	
	    return -1;
	}
}
//*/

/*
import java.util.*;
class UserSolution{

	class Spot{
	    int row;
	    int col;
	    int cost;
	    Spot(int row, int col, int cost){
	    	this.row = row;
	    	this.col = col;
	    	this.cost = cost;
	    }		
	}
	
	
	class ChargingSpot implements Comparable<ChargingSpot>{
		int mID;
		int cost;
		ChargingSpot(int mID, int cost){
			this.mID = mID;
			this.cost = cost;
		}
		@Override
		public int compareTo(UserSolution.ChargingSpot o) {
			return Integer.compare(cost, o.cost);
		}
	}
	
	final int SPOT_ID = 2;
	int[][] map;
	int[][] visit;
	PriorityQueue<ChargingSpot> cSpotPQ;
	ArrayList<ArrayList<ChargingSpot>> cSpotList;
	int[] dist;

	int maxRange, visitCnt;

	void init(int N, int mRange, int mMap[][]) {
		int len = N+2;
	    maxRange = mRange;
	    map = new int[len][len];
	    visit = new int[len][len];
	    dist = new int[200];
	    cSpotList = new ArrayList<>(200);
	    for(int i=0; i<200; ++i) {
	    	cSpotList.add(new ArrayList<>());
	    }
	    cSpotPQ = new PriorityQueue<>();
	    
	    for(int i=0; i<len; ++i) {
	    	Arrays.fill(map[i], 1);
	    }
	    for(int i=1;i<=N; ++i) {
	    	System.arraycopy(mMap[i-1], 0, map[i], 1, N);
	    }
	   
	    visitCnt = 0;
	}

	void setList(int mID, int mRow, int mCol) {
	    int dRow[] = { -1, 1, 0, 0 };
	    int dCol[] = { 0, 0, -1, 1 };
	
	    Queue<Spot> spotQueue = new LinkedList<>();
	    spotQueue.offer(new Spot(mRow, mCol, 0));
	    visitCnt++;
	
	    visit[mRow][mCol] = visitCnt;
	
	    while (!spotQueue.isEmpty()) {
	        Spot cur = spotQueue.poll();
	
	        int spotId = map[cur.row][cur.col] - SPOT_ID;
	        if (map[cur.row][cur.col] >= SPOT_ID && spotId != mID) {
	        	cSpotList.get(spotId).add(new ChargingSpot(mID , cur.cost));
	        	cSpotList.get(mID).add(new ChargingSpot( spotId , cur.cost));
	        }
	
	        if (cur.cost >= maxRange) continue;
	
	        for (int i = 0; i < 4; ++i) {
	            int nextRow = cur.row + dRow[i];
	            int nextCol = cur.col + dCol[i];
	            int cost = cur.cost + 1;
	            if (visit[nextRow][nextCol] == visitCnt || map[nextRow][nextCol] == 1) continue;
	
	            visit[nextRow][nextCol] = visitCnt;
	            spotQueue.offer(new Spot(nextRow , nextCol , cost));
	        }
	    }
	}

	void add(int mID, int mRow, int mCol) {
		mRow++; mCol++;
	    map[mRow][mCol] = mID + SPOT_ID;	
	    setList(mID, mRow, mCol);
	}
	
	int distance(int mFrom, int mTo) {
		cSpotPQ.clear();
	    Arrays.fill(dist, Integer.MAX_VALUE);
	    dist[mFrom] = 0;
	    cSpotPQ.offer(new ChargingSpot(mFrom, 0));
	    while (!cSpotPQ.isEmpty()) {
	    	ChargingSpot temp = cSpotPQ.poll();
	        int spot = temp.mID;
	        int cost = temp.cost;
	        if (spot == mTo) return cost;
	        for (ChargingSpot charging : cSpotList.get(spot)) {
	            int val = cost + charging.cost;
	            if (dist[charging.mID] > val) {
	                dist[charging.mID] = val;
	                cSpotPQ.offer(new ChargingSpot(charging.mID , dist[charging.mID]));
	            }
	        }
	    }
	
	    return -1;
	}
}
//*/