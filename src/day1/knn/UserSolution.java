package day1.knn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

class UserSolution
{
	static int K, L;
	static Map<Integer, Point> hm = new HashMap<Integer, UserSolution.Point>();
	static List<List<List<Point>>> pointList = new ArrayList<List<List<Point>>>();
	static Queue<Point> pq = new PriorityQueue<UserSolution.Point>();
	static int minX, maxX, minY, maxY;
	static int[] categoryCount = new int[11];
	
	static class Point implements Comparable<Point> {
		int x;
		int y;
		int category;
		int distance;
		
		Point(int x, int y, int category) {
			this.x = x;
			this.y = y;
			this.category = category;
		}
		
		void setDistance(int X, int Y) {
			this.distance = Math.abs(X - x) + Math.abs(Y - y);
		}
		
		@Override
		public int compareTo(Point o) {
			if (this.distance == o.distance) {
				return this.x == o.x ? Integer.compare(this.y, o.y) : Integer.compare(this.x, o.x);
			}
			return Integer.compare(this.distance, o.distance);
		}
	}
	
	public void init(int K, int L)
	{
		this.K = K;
		this.L = L;
		
		hm.clear();
		pointList.clear();
		for (int i = 0; i < 41; i++) { // 1부터 4000까지의 X, Y 범위를 L의 최대 크기인 100배 만큼 압축하면 변수에 저장된 데이터를 불러오는 시간을 100배 절약 가능
			pointList.add(new ArrayList<List<Point>>());
			for (int j = 0; j < 41; j++) {
				pointList.get(i).add(new ArrayList<Point>());
			}
		}
		
		return;
	}

	public void addSample(int mID, int mX, int mY, int mC)
	{
		Point point = new Point(mX, mY, mC);
		hm.put(mID, point);
		pointList.get(mX / 100).get(mY / 100).add(point);
		
		return;
	}
	
	public void deleteSample(int mID)
	{
		Point point = hm.remove(mID);
		pointList.get(point.x / 100).get(point.y / 100).remove(point);
		
		return;
	}

	public int predict(int mX, int mY)
	{
		pq.clear();
		minX = (mX - L) / 100;
		maxX = (mX + L) / 100;
		minY = (mY - L) / 100;
		maxY = (mY + L) / 100;
		
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				for (Point point : pointList.get(i).get(j)) {
					point.setDistance(mX, mY);
					
					if (point.distance <= L) {
						pq.add(point);
					}
				}
			}
		}
		
		if (pq.size() < K) {
			return -1;
		}
		
		Arrays.fill(categoryCount, 0);
		for (int k = 0; k < K; k++) {
			Point point = pq.remove();
			++categoryCount[point.category];
		}
		
		int index = 0;
		for (int i = 1; i < 11; i++) {
			if (categoryCount[index] < categoryCount[i]) {
				index = i;
			}
		}
		
		return index;
	}
}