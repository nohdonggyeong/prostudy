package day1.공지방송;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

class UserSolution {	
	static Map<Integer, Member> hm = new HashMap<Integer, UserSolution.Member>();
	static Set<Member> ts = new TreeSet<UserSolution.Member>();
	static Queue<Member> pq = new PriorityQueue<UserSolution.Member>((a, b) -> a.end == b.end ? Integer.compare(a.id, b.id) : Integer.compare(a.end, b.end));
	
	static class Member implements Comparable<Member> {
		int id;
		int start;
		int end;
		
		Member(int id, int start, int end) {
			this.id = id;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int compareTo(Member o) {
			return this.start == o.start ? Integer.compare(this.id, o.id) : Integer.compare(this.start, o.start);
		}
	}
	
    public void init() {
    	hm.clear();
    	ts.clear();
    	
        return;
    }

    public int add(int mId, int mStart, int mEnd) {
    	if (hm.containsKey(mId)) {	
    		ts.remove(hm.get(mId));
    	}
    	
		Member updated = new Member(mId, mStart, mEnd);
		hm.put(mId, updated);
		ts.add(updated);
    	
        return ts.size();
    }

    public int remove(int mId) {
    	if (hm.containsKey(mId)) {
    		ts.remove(hm.get(mId));
    		hm.remove(mId);
    	}
    	
        return ts.size();
    }

    int announce(int mDuration, int M) {
    	pq.clear();
    	for (Member member : ts) {
    		int endTime = member.start + mDuration - 1;
    		pq.add(member);
    		while (!pq.isEmpty()) {
    			if (pq.peek().end < endTime) {
    				pq.remove();
    			} else {
    				break;
    			}
    		}
    		
    		if (pq.size() == M) {
    			return member.start;
    		}
    	}
    	
        return -1;
    }
}