package etc.상품권배분;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class UserSolutionStudy {
	static class Part {
		int id;
		int total;
		Part parent;
		ArrayList<Part> childs = new ArrayList<>();
		
		Part(int id, int total) {
			this.id = id;
			this.total = total;
		}
	}
	
	static int N;
	static ArrayList<Part> groupList;
	static HashMap<Integer, Part> map;
	
	public void init(int N, int mId[], int mNum[]) {
		this.N = N;
		map = new HashMap<>();
		groupList = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			Part root = new Part(mId[i], mNum[i]);
			map.put(mId[i], root);
			groupList.add(root);
		}
		
		return;
	}
	
	public int add(int mId, int mNum, int mParent) {
		Part parent = map.get(mParent);
		if (parent.childs.size() == 3) {
			return -1;
		}
		
		Part newPart = new Part(mId, mNum);
		newPart.parent = parent;
		parent.childs.add(newPart);
		map.put(mId, newPart);
		
		Part cur = parent;
		while (cur != null) {
			cur.total += mNum;
			cur = cur.parent;
		}
		
		return parent.total;
	}
	
	void removePartDFS(Part p) {
		for (Part c : p.childs) {
			removePartDFS(c);
		}
		map.remove(p.id);
	}
	
	public int remove(int mId) {
		Part removePart = map.get(mId);
		if (removePart == null) {
			return -1;
		}
		
		removePart.parent.childs.remove(removePart);
		
		Part cur = removePart.parent;
		while (cur != null) {
			cur.total -= removePart.total;
			cur = cur.parent;
		}
		
		removePartDFS(removePart);
		
		return removePart.total;
	}
	
	public int distribute(int K) {
		Part maxPart = Collections.max(groupList, (a, b) -> a.total - b.total);
		int max = maxPart.total;
		int mid;
		int low = 0;
		
		while (low <= max) {
			mid = (low + max) / 2;
			
			int sum = 0;
			for (Part part : groupList) {
				sum += Math.min(mid, part.total);
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
