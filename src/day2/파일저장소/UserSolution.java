package day2.파일저장소;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

class UserSolution {
	static class Fragment implements Comparable<Fragment> {
		int start;
		int size;
		
		Fragment(int start, int size) {
			this.start = start;
			this.size = size;
		}
		
		@Override
		public int compareTo(Fragment o) {
			if (this.start == o.start) {
				return Integer.compare(this.size, o.size);
			}
			return  Integer.compare(this.start, o.start);
		}
	}
	
	static int totalSize;
	static TreeSet<Fragment> emptySpaces = new TreeSet<>();
	static HashMap<Integer, TreeSet<Fragment>> files = new HashMap<>();
	
	public void init(int N) {
		totalSize = N;
		emptySpaces.clear();
		emptySpaces.add(new Fragment(1, totalSize));
		
		files.clear();
		
		return;
	}

	public int add(int mId, int mSize) {
		if (mSize > totalSize) {
			return -1;
		}
		
		TreeSet<Fragment> fragments = new TreeSet<UserSolution.Fragment>();
		
		Fragment curEmptySpace;
		int curEmptySpaceStart, curEmptySpaceSize;
		while (mSize > 0) {
			curEmptySpace = emptySpaces.pollFirst();
			curEmptySpaceStart = curEmptySpace.start;
			curEmptySpaceSize = curEmptySpace.size;
			
			if (curEmptySpaceSize > mSize) { // 빈 공간 > 필요한 사이즈
				fragments.add(new Fragment(curEmptySpaceStart, mSize));
				emptySpaces.add(new Fragment(curEmptySpaceStart + mSize, curEmptySpaceSize - mSize));
				totalSize -= mSize;
				mSize = 0;
			} else if (curEmptySpaceSize <= mSize) { // 빈 공간 <= 필요한 사이즈
				fragments.add(new Fragment(curEmptySpaceStart, curEmptySpaceSize));
				totalSize -= curEmptySpaceSize;
				mSize -= curEmptySpaceSize;
			}
		}
		
		files.put(mId, fragments);
		
		int result = fragments.first().start; // 저장된 공간에서 가장 앞 서는 주소
//		if (result != ans) {
//			System.out.println("!!!!!!!!!!!!!!!!!! add result: " + result);
//		} else {
//			System.out.println("add result: " + result);
//		}
		return result;
	}

	public int remove(int mId) {
		TreeSet<Fragment> file = files.remove(mId);
		int result = file.size(); // mId 파일이 저장되어 있던 파일 조각의 개수
		
		for (Fragment fragment : file) {
			emptySpaces.add(fragment);
			totalSize += fragment.size;
		}
		
		PriorityQueue<Fragment> tempPq = new PriorityQueue<UserSolution.Fragment>();
		Fragment curFragment = emptySpaces.pollFirst();
		for (Fragment nextFragment : emptySpaces) {
			if (curFragment.start + curFragment.size == nextFragment.start) { // 인접 빈 공간
				curFragment = new Fragment(curFragment.start, curFragment.size + nextFragment.size);
			} else {
				tempPq.add(curFragment);
				curFragment = nextFragment;
			}
		}
		tempPq.add(curFragment);
		
		emptySpaces.clear();
		while (!tempPq.isEmpty()) {
			emptySpaces.add(tempPq.remove());
		}
		
//		if (result != ans) {
//			System.out.println("!!!!!!!!!!!!!!!!!! remove result: " + result + ", answer: " + ans);
//			for (Fragment fragment : file) {
//				System.out.println("start: " + fragment.start + ", end: " + (fragment.start + fragment.size - 1));
//			}
//		} else {
//			System.out.println("remove result: " + result);
//		}
		return result;
	}

	public int count(int mStart, int mEnd) {
		int result = 0; // 주어진 주소 영역에 저장되어 있는 파일의 개수
		
		int fragmentStart, fragmentEnd;
		boolean flag;
		for (TreeSet<Fragment> file : files.values()) {
			flag = false;
			for (Fragment fragment : file) {
				fragmentStart = fragment.start;
				fragmentEnd = fragment.start + fragment.size - 1;
				if (fragmentEnd < mStart) {
					continue;
				} else if (fragmentStart > mEnd) {
					break;
				} else {
					flag = true;
					break;
				}
			}

			if (flag) {
//				System.out.println("[" + fragmentStart + ", " + fragmentEnd + "]");
				result += 1;	
			}
		}
		
//		if (result != ans) {
//			System.out.println("!!!!!!!!!!!!!!!!!! count result: " + result);
//		} else {
//			System.out.println("count result: " + result);
//		}
		return result;
	}
}