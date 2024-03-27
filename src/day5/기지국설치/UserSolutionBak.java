package day5.기지국설치;

import java.util.*;
class UserSolutionBak {
    class Node implements Comparable<Node>{
        int id, pos;
        public Node(int id, int pos){
            this.id=id; this.pos=pos;
        }
		@Override
		public int compareTo(Node o) {
			return Integer.compare(pos, o.pos);
		}
    }
    HashMap<Integer, Node> nodeMap = new HashMap<>();
    TreeSet<Node> nodeSet = new TreeSet<>();
    
    int N;
    
    public void init(int N, int mId[], int mLocation[]) {
    	nodeMap.clear(); 
    	nodeSet.clear();
        this.N = N;
        for(int i=0; i<N;i++){
            Node node = new Node(mId[i], mLocation[i]);
            nodeMap.put(mId[i], node);
            nodeSet.add(node);
        }
        return;
    }
 
    public int add(int mId, int mLocation) {
        if(nodeMap.containsKey(mId)){
            Node node = nodeMap.get(mId);
            nodeSet.remove(node);
            node.pos = mLocation;
            nodeSet.add(node);
        }else{
            Node node = new Node(mId, mLocation);
            nodeMap.put(mId, node);
            nodeSet.add(node);
            N++;
        }
        return N;
    }
 
    public int remove(int mStart, int mEnd) {
        Node node = new Node(0, mStart);
        while(true){
            Node target = nodeSet.higher(node);
            if(target == null || target.pos > mEnd) break;
            
            nodeSet.remove(target);
            nodeMap.remove(target.id);
            N--;
        }
        return N;
    }
     
    int install(int M) {
        int s = 1;
        int e = nodeSet.last().pos;

        int ans = 0;
        while(s <= e) {
            int mid = (s + e) / 2;
            
            int first = nodeSet.first().pos;
            int installedCnt = 1;
            for(Node t : nodeSet) {
                if (t.pos - first >= mid) {
                    installedCnt++;
                    first = t.pos;
                }
            }
              
            if (installedCnt < M) {
                e = mid - 1;
            } else {
                ans = Math.max(ans, mid);
                s = mid + 1;
            }
            

        }
        return ans;
    }
}