package etc.던전탈출;

import java.util.*;

class UserSolutionBak {
	class Gate {
        int row;
        int col;
        int distance;
 
        Gate(int mRow, int mCol, int mDistance) {
            row = mRow;
            col = mCol;
            distance = mDistance;
        }
    }
 
    
    class Node implements Comparable<Node>{
        int gateId;
        int distance;
 
        Node(int gateId, int distance) {
            this.gateId = gateId;
            this.distance = distance;
        }

		@Override
		public int compareTo(Node o) {
			return distance - o.distance;
		}
    }
	
    int N, maxStamina;
    int[][] map;
    ArrayList<ArrayList<Node>> g;
    HashMap<Integer, Gate> gateMap = new HashMap<>();
    final int MARK = 1;
    int[][] visited;
    final int[] ROW = { 1, -1, 0, 0 };
    final int[] COL = { 0, 0, 1, -1 };
 
    int visitCnt;
 
    void init(int N, int mMaxStamina, int mMap[][]) {
        this.N = N;
        visited = new int[N][N];
        gateMap.clear();
        maxStamina = mMaxStamina;
        map = mMap;
        visitCnt=0;
        // 1<=mGateID<=200
        g = new ArrayList<>(201);
        for (int i = 0; i < 201; ++i) {
            g.add(new ArrayList<>());
        }
 
        return;
    }
 
    
    
    void addGate(int mGateID, int mRow, int mCol) {

    	map[mRow][mCol] = mGateID + MARK;
    	gateMap.put(mGateID, new Gate(mRow, mCol, 0));

        setPath(mGateID, mRow, mCol);

        return;
    }
 
    void setPath(int gateId, int row, int col) {
 
        Queue<Gate> gateQueue = new ArrayDeque<>();
        gateQueue.add(new Gate(row, col, 0));
        
        visited[row][col] = visitCnt++;
        while (!gateQueue.isEmpty()) {
        	Gate curGate = gateQueue.poll();
            if (map[curGate.row][curGate.col] > 1) {
                int mId = map[curGate.row][curGate.col];
                g.get(gateId).add(new Node(mId - MARK, curGate.distance));
                g.get(mId-MARK).add(new Node(gateId, curGate.distance));
            }
 
            if (curGate.distance + 1 > maxStamina) continue;
 
            for (int i = 0; i < 4; i++) {
                int nRow = ROW[i] + curGate.row;
                int nCol = COL[i] + curGate.col;
     
                if(visited[nRow][nCol] == visitCnt || map[nRow][nCol] == 1)
                	continue;

                visited[nRow][nCol] = visitCnt;
                gateQueue.add(new Gate(nRow, nCol, curGate.distance + 1));
            }
        }
    }
 
    void removeGate(int mGateID) {
    	Gate cell = gateMap.remove(mGateID);
    	map[cell.row][cell.col] = 0;
        g.get(mGateID).clear();
        return;
    }
 
    int getMinTime(int mStartGateID, int mEndGateID) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(mStartGateID, 0));
 
        
        boolean[] visited = new boolean[201];
        
        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            if (visited[curNode.gateId]) continue;
            
            if (curNode.gateId == mEndGateID) {
                return curNode.distance;
            }
            visited[curNode.gateId] = true;
            for (Node nextNode : g.get(curNode.gateId)) {
                
                if (!visited[nextNode.gateId]) {
                    pq.add(new Node(nextNode.gateId, curNode.distance+nextNode.distance));
                }
            }
        }
 
        return -1;
    }
}