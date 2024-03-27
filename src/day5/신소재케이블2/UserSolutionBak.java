package day5.신소재케이블2;

//*
import java.util.*;

class UserSolutionBak {
	class Node implements Comparable<Node>{
		int id;
		int cost;
		Node(int id, int cost){
			this.id = id;
			this.cost = cost;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(cost, o.cost);
		}
	}
	HashMap<Integer, ArrayList<Node>> g;
	int maxLatency, time;
	public void init(int mDevice) {
		g = new HashMap<>();
		g.put(mDevice, new ArrayList<>());
      maxLatency = 0;
      time = 0;
      return;
  }
	public void connect(int mOldDevice, int mNewDevice, int mLatency) {
      g.get(mOldDevice).add(new Node(mNewDevice, mLatency));
      g.put(mNewDevice, new ArrayList<>());
      g.get(mNewDevice).add(new Node(mOldDevice, mLatency));
      return;
  }
	public int measure(int mDevice1, int mDevice2) {
		HashMap<Integer, Boolean> visited = new HashMap<>();
      PriorityQueue<Node> pq = new PriorityQueue<>();
      pq.offer(new Node(mDevice1, 0));
      while(!pq.isEmpty()) {
      	Node t = pq.poll();
      	if(visited.containsKey(t.id)) { continue; }
      	
       	if(t.id == mDevice2) {
      		return t.cost;
      	}
      	visited.put(t.id, true);
      	ArrayList<Node> t2 = g.get(t.id);
      	for( Node t3 : t2) {
      		if(visited.containsKey(t3.id)) continue;
      		
      		pq.offer(new Node(t3.id, t.cost+t3.cost));
      	}
      }
      return -1;
	 }
	 
  public int test(int mDevice) {
  	ArrayList<Node> d = g.get(mDevice);
      PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
      for (Node t : d) {
          maxLatency = 0;
          dfs(mDevice, t.id, 0);
          q.add(maxLatency+t.cost);
      }
      return q.size() == 1? q.poll() : q.poll() + q.poll();
  }
   
  public void dfs(int prv, int s, int sum) {
  	
       
      if (maxLatency < sum) maxLatency = sum;
       
      for (Node next : g.get(s)) {
          
          if (next.id != prv) {
              dfs(s, next.id, sum + next.cost);
          }
      }
  }
}
//*/

/*
import java.util.*;

class UserSolution {
  class Cable implements Comparable<Cable>{
      int id;
      int cost;
       
      Cable(int id, int cost) {
          this.id = id; this.cost = cost;
      }

		@Override
		public int compareTo(UserSolution.Cable o) {
			// TODO Auto-generated method stub
			return Integer.compare(cost, o.cost);
		}
  }
   
  class Device {
      int id;
      ArrayList<Cable> child;
       
      Device(int id) {
          this.id = id;
          child = new ArrayList<>();
      }
  }

  HashMap<Integer,  Device> devList;
  int maxLatency;
  int time;
   
  public void init(int mDevice) {
  	devList = new HashMap<>();
      devList.put(mDevice, new Device(mDevice));
      maxLatency = 0;
      time = 0;
      return;
  }
   
  public void connect(int mOldDevice, int mNewDevice, int mLatency) {
      Device oldDev = devList.get(mOldDevice);
      Device newDev = new Device(mNewDevice);
      devList.put(mNewDevice, newDev);
      oldDev.child.add(new Cable(mNewDevice, mLatency));
      newDev.child.add(new Cable(mOldDevice, mLatency));
      return;
  }
   
  public int measure(int mDevice1, int mDevice2) {
      Device d1 = devList.get(mDevice1);
      Device d2 = devList.get(mDevice2);
       
      dfs(null, d1, d2, 0);
      return time;
  }
   
  public int test(int mDevice) {
      Device d = devList.get(mDevice);
      PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
      for (Cable c : d.child) {
          maxLatency = 0;
          Device next = devList.get(c.id);
          dfs(d, next, null, 0);
          q.add(maxLatency+c.cost);
      }
     
      return q.size() == 1 ? q.poll() : q.poll() + q.poll();
  }
   
  public void dfs(Device prv, Device s, Device e, int sum) {
      if (s == e) {
          time = sum;
          return;
      }
       
      if (maxLatency < sum) maxLatency = sum;
       
      for (Cable next : s.child) {
          Device d = devList.get(next.id);
          if (d != prv) {
              dfs(s, d, e, sum + next.cost);
          }
      }
  }     
}
//*/