package etc.조직개편;

import java.util.HashMap;

class UserSolution {
	class Node {
		int id;
		int num;
		int totalNum;
		Node parent; // 부모 노드
		Node leftChild; // 왼쪽 자식 노드
		Node rightChild; // 오른쪽 자식 노드
		
		public Node(int id, int num) {
			this.id = id;
			this.num = num;
			this.totalNum = num;
		}
	}
	
	HashMap<Integer, Node> map = new HashMap<>(); // 노드 id 관리하는 map
	Node root;
	int group, M, K;
	
    public void init(int mId, int mNum) {
    	map.clear();
    	Node newNode = new Node(mId, mNum); // 루트 노드 생성
    	map.put(mId, newNode);
    	root = newNode;
        return;
    }
 
    public int add(int mId, int mNum, int mParent) {
    	Node parentNode = map.get(mParent); // mParent 부서에 이미 2개의 하위 부서가 존재한다면, 추가에 실패하고 -1을 반환
    	if (parentNode.leftChild != null && parentNode.rightChild != null) {
    		return -1;
    	}
    	
    	Node newNode = new Node(mId, mNum);
    	map.put(mId, newNode);
    	newNode.parent = parentNode;
    	
    	if (parentNode.leftChild == null) { // 왼쪽 자식으로 우선 추가
    		parentNode.leftChild = newNode;
    	} else {
    		parentNode.rightChild = newNode;
    	}
    	
    	Node cur = parentNode;
    	while (cur != null) { // 부모 노드로 거슬러 올라가며 totalNum을 갱신
    		cur.totalNum += mNum;
    		cur = cur.parent;
    	}
    	
    	return parentNode.totalNum; // mParent 부서를 포함하여 그 아래 모든 부서의 인원 수 합을 반환
    }
 
    public int remove(int mId) {
    	Node curNode = map.remove(mId);
    	if (curNode == null) {
    		return -1;
    	}
    	
    	if (curNode.parent.leftChild == curNode) {
    		curNode.parent.leftChild = null;
    	} else {
    		curNode.parent.rightChild = null;
    	}
    	
    	removeChild(curNode);
    	
    	Node parentNode = curNode.parent;
    	while (parentNode != null) {
    		parentNode.totalNum -= curNode.totalNum;
    		parentNode = parentNode.parent;
    	}
    	
        return curNode.totalNum;
    }
    
    public void removeChild(Node child) {
    	if (child == null) {
    		return;
    	}
    	
    	map.remove(child.id);
    	
    	if (child.leftChild != null) {
    		removeChild(child.leftChild);
    	}
    	if (child.rightChild != null) {
    		removeChild(child.rightChild);
    	}
    }
 
    public int reorganize(int M, int K) {
        if (map.size() < M) {
        	return 0;
        }
        
        this.M = M;
        this.K = K;
        
        group = 1;
        dfs(root);
        
        return group <= M ? 1 : 0;
    }
    
    public int dfs(Node node) {
    	if (node == null) {
    		return 0;
    	}
    	
    	if (node.num > K || group > M) {
    		group = M + 1;
        	return 0;
    	}
    	
    	int l = dfs(node.leftChild);
    	int r = dfs(node.rightChild);
    	
    	if (l + r + node.num <= K) {
    		return l + r + node.num;
    	}
    	if (l + node.num <= K || r + node.num <= K) {
    		++group;
    		return Math.min(l + node.num, r + node.num);
    	}
    	
    	group += 2;
    	return node.num;
    }
}