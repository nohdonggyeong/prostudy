package etc.상품권배분;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class UserSolution {
	static class Part {
		int id; // id
		int total; // add, remove 반환 데이터
		Part parent;
		ArrayList<Part> childs = new ArrayList<>();
		
		Part(int id, int total) {
			this.id = id;
			this.total = total;
		}
	}
	
	static HashMap<Integer, Part> map = new HashMap<>();
	static ArrayList<Part> groupList = new ArrayList<>();
	
	public void init(int N, int mId[], int mNum[]) {
		map.clear();
		groupList.clear();
		
		Part part;
		for (int n = 0; n < N; n++) {
			part = new Part(mId[n], mNum[n]);
			map.put(mId[n], part);
			groupList.add(part);
		}
		
		return;
	}

	public int add(int mId, int mNum, int mParent) {
		Part parent = map.get(mParent);
		if (parent.childs.size() >= 3) {
			return -1;
		}
		
		Part newPart = new Part(mId, mNum);
		newPart.parent = parent;
		parent.childs.add(newPart);
		
		Part cur = newPart.parent;
		while (cur != null) {
			cur.total += mNum;
			cur = cur.parent;
		}

		map.put(mId, newPart);
		
		return parent.total;
	}

	static void removeDFS(Part cur) {
		for (Part child : cur.childs) {
			removeDFS(child);
		}
		
		map.remove(cur.id);
	}
	
	public int remove(int mId) {
		Part removed = map.remove(mId);
		if (removed == null) {
			return -1;
		}
		
		removed.parent.childs.remove(removed);
		
		Part cur = removed.parent;
		while (cur != null) {
			cur.total -= removed.total;
			cur = cur.parent;
		}
		
		removeDFS(removed);
		
		return removed.total;
	}

	public int distribute(int K) {
		Part maxPart = Collections.max(groupList, (a, b) -> a.total - b.total);
		int max = maxPart.total;
		int mid;
		int low = 0;
		
		while (low <= max) {
			mid = (low + max) / 2;
			
			int sum = 0;
			for (Part root : groupList) {
				sum += Math.min(mid, root.total);
			}
			
			if (sum <= K) {
				low = mid + 1;
			} else {
				max = mid - 1;
			}
		}
		
		return max;
	}
}