package day2.계통수;

import java.util.HashMap;
import java.util.Map;

class UserSolution
{
	static class Node {
		String speciesName;
		int depth;
		Node parent;
		int[] countByDistance = new int[5];
		
		Node(String speciesName) {
			this.speciesName = speciesName;
			this.depth = 0;
		}
	}
	
	Map<String, Node> hm = new HashMap<String, UserSolution.Node>();
	
	public void init(char[] mRootSpecies)
	{
		hm.clear();
		hm.put(new String(mRootSpecies), new Node(new String(mRootSpecies)));
	}

	public void add(char[] mSpecies, char[] mParentSpecies)
	{
		Node parent = hm.get(new String(mParentSpecies));
		Node child = new Node(new String(mSpecies));
		
		child.parent = parent;
		child.depth = parent.depth + 1;
		child.parent.countByDistance[1]++;
		
		hm.put(new String(mSpecies), child);
		
		if (child.parent.parent != null) {
			child.parent.parent.countByDistance[2]++;
			
			if (child.parent.parent.parent != null) {
				child.parent.parent.parent.countByDistance[3]++;
				
				if (child.parent.parent.parent.parent != null) {
					child.parent.parent.parent.parent.countByDistance[4]++;
				}
			}
		}
	}
	
	public int getDistance(char[] mSpecies1, char[] mSpecies2)
	{
		Node node1 = hm.get(new String(mSpecies1));
		Node node2 = hm.get(new String(mSpecies2));
		
		int count = 0;
		while (node1 != node2) {
			if (node1.depth > node2.depth) {
				node1 = node1.parent;
				count++;
			} else {
				node2 = node2.parent;
				count++;
			}
		}
		
		return count;
	}
	
	public int getCount(char[] mSpecies, int mDistance)
	{
		Node node = hm.get(new String(mSpecies));
		
		int count = 0;
		if (mDistance == 1) {
			count = node.countByDistance[1];
			
			if (node.parent != null) {
				count++;
			}
		} else if (mDistance == 2) {
			count = node.countByDistance[2];
			
			if (node.parent != null) {
				count += (node.parent.countByDistance[1] - 1);
				
				if (node.parent.parent != null) {
					count++;
				}
			}
		} else if (mDistance == 3) {
			count = node.countByDistance[3];
			
			if (node.parent != null) {
				count += (node.parent.countByDistance[2] - node.countByDistance[1]);
				
				if (node.parent.parent != null) {
					count += (node.parent.parent.countByDistance[1] - 1);
					
					if (node.parent.parent.parent != null) {
						count++;
					}
				}
			}
		} else {
			count = node.countByDistance[4];
			
			if (node.parent != null) {
				count += (node.parent.countByDistance[3] - node.countByDistance[2]);
				
				if (node.parent.parent != null) {
					count += (node.parent.parent.countByDistance[2] - node.parent.countByDistance[1]);
					
					if (node.parent.parent.parent != null) {
						count += (node.parent.parent.parent.countByDistance[1] - 1);
						
						if (node.parent.parent.parent.parent != null) {
							count++;
						}
					}
				}
			}
		}
		
		return count;
	}
}