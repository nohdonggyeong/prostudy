package day2.파일저장소;

import java.util.*;

class UserSolutionBak {
    class Node implements Comparable<Node>{
    	int start;
    	int size;
    	
    	Node(int start, int size){
    		this.start = start;
    		this.size = size;
    	}
    	
		@Override
		public int compareTo(Node o) {
			return Integer.compare(start, o.start);
		}
    }
    
    Map<Integer, ArrayList<Node>> hm = new HashMap<>();
    List<Node> emptyList = new ArrayList<>();
    int remainSize;
    
    public void init(int N) {
        hm.clear();
        
        emptyList.clear();
        emptyList.add(new Node(1, N));
        
        remainSize = N;
    }
 
    public int add(int mId, int mSize) {
    	if(mSize > remainSize) {
    		return -1;
    	}
    	
    	remainSize -= mSize;
    	
    	int emptyStartIndex = emptyList.get(0).start;
    	
    	while(true) {
    		Node emptyStartNode = emptyList.remove(0);
    		if(mSize <= emptyStartNode.size) {
    			if(mSize != emptyStartNode.size) {
    				emptyList.add(0, new Node(emptyStartNode.start + mSize, emptyStartNode.size - mSize));
    			}
    			
    			if(!hm.containsKey(mId)) {
    				hm.put(mId, new ArrayList<>());
    			}
    			hm.get(mId).add(new Node(emptyStartNode.start, mSize));
    			
    			break;
    		}else {
    			mSize -= emptyStartNode.size;
    			
    			if(!hm.containsKey(mId)) {
    				hm.put(mId, new ArrayList<>());
    			}
    			hm.get(mId).add(new Node(emptyStartNode.start, emptyStartNode.size));
    		}
    	}
    	
        return emptyStartIndex;
    }
    
    void addFragmentToEmptyList(int fragmentStart, int fragmentSize) {
        
    	int nextToFragmentIndex = Math.abs(Collections.binarySearch(emptyList, new Node(fragmentStart, fragmentSize)) + 1);
    	
    	Node nextEmptyNode = emptyList.get(nextToFragmentIndex);
    	
    	int nextEmptyNodeStart, nextEmptyNodeSize, prevEmptyNodeStart, prevEmptyNodeSize;
        if (nextToFragmentIndex == 0) {
			nextEmptyNodeStart = nextEmptyNode.start;
			nextEmptyNodeSize = nextEmptyNode.size;
			
			if (fragmentStart + fragmentSize == nextEmptyNodeStart) {
            	nextEmptyNode.start = fragmentStart;
            	nextEmptyNode.size += fragmentSize;
            } else {
                emptyList.add(0, new Node(fragmentStart, fragmentSize));
            }
        } else if (nextToFragmentIndex == emptyList.size()) {
        	nextEmptyNodeStart = emptyList.get(emptyList.size() - 1).start;
            nextEmptyNodeSize = emptyList.get(emptyList.size() - 1).size;
            
            if (nextEmptyNodeStart + nextEmptyNodeSize == fragmentStart) {
            	nextEmptyNode.size += fragmentSize;
            } else {
            	emptyList.add(new Node(fragmentStart, fragmentSize));
            }
        } else {
            nextEmptyNodeStart = nextEmptyNode.start;
            nextEmptyNodeSize = nextEmptyNode.size;
            
        	Node prevNode = emptyList.get(nextToFragmentIndex - 1);
            prevEmptyNodeStart = prevNode.start;
            prevEmptyNodeSize = prevNode.size;

            if (prevEmptyNodeStart + prevEmptyNodeSize == fragmentStart && fragmentStart + fragmentSize == nextEmptyNodeStart) {
            	prevNode.size += (fragmentSize + nextEmptyNodeSize);
                emptyList.remove(nextToFragmentIndex);
            }
            else if (prevEmptyNodeStart + prevEmptyNodeSize == fragmentStart && fragmentStart + fragmentSize != nextEmptyNodeStart) {
            	prevNode.size += fragmentSize;
            }
            else if (prevEmptyNodeStart + prevEmptyNodeSize != fragmentStart && fragmentStart + fragmentSize == nextEmptyNodeStart) {
                nextEmptyNode.start = fragmentStart;
                nextEmptyNode.size += fragmentSize;
            }
            else {
                emptyList.add(nextToFragmentIndex, new Node(fragmentStart, fragmentSize));
            }
        }
    }

    public int remove(int mId) {
    	 int fragmentCnt =  hm.get(mId).size();
    	 
    	for(Node fragment : hm.get(mId)) {
    		remainSize += fragment.size;
    		addFragmentToEmptyList(fragment.start, fragment.size);
    	}
    	
    	hm.remove(mId);
    	
        return fragmentCnt;
    }
 
    public int count(int mStart, int mEnd) { 
    	int cnt = 0;
    	
        int startIdx, endIdx;
        for (List<Node> file : hm.values()) {
            for (Node fragment : file) {
                startIdx = fragment.start;
                endIdx = fragment.start + fragment.size - 1;
                
                if (!(endIdx < mStart || startIdx > mEnd)) {
                    cnt++;
                    
                    break;
                }
            }
        }
        
        return cnt;
    }
}