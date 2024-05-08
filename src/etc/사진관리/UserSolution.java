package etc.사진관리;

import java.time.LocalDateTime;
import java.util.*;
 
class UserSolution {
 
    class MetaData implements Comparable<MetaData>{
        int id;
        LocalDateTime dateTime;
        String location;
        String[] people;
 
        public MetaData(int id, LocalDateTime dateTime, String location, String[] people) {
            this.id = id;
            this.dateTime = dateTime;
            this.location = location;
            this.people = people;
        }
 
        @Override
        public int compareTo(MetaData o) {
        	return this.dateTime.compareTo(o.dateTime);
        }
    }

    ArrayList<MetaData> totalList = new ArrayList<>(); // 전체 메타데이터를 담는 list
    HashMap<String, ArrayList<MetaData>> locationMap = new HashMap<>(); // location 필터링을 위한 map
    HashMap<String, ArrayList<MetaData>> peopleMap = new HashMap<>(); // people 필터링을 위한 map
    
    // 초기화하고 데이터 저장
    void init(int N, String[] mPictureList) {
    	totalList.clear();
        locationMap.clear();
        peopleMap.clear();
        
        savePicture(N, mPictureList);
    }
 
    // 저장된 사진들보다 최신임을 보장하므로 tempList로 최신 데이터를 우선 정렬한 뒤 list, map의 뒤로 계속 추가
    void savePicture(int M, String[] mPictureList) {
    	List<MetaData> tempList = new ArrayList<>();
    	for (String el : mPictureList) {
    		tempList.add(getMetaData(el));
    	}
    	Collections.sort(tempList);
    	
    	totalList.addAll(tempList);
    	
    	for (MetaData el : tempList) {
    		if (!locationMap.containsKey(el.location)) {
    			locationMap.put(el.location, new ArrayList<MetaData>());
    		}
    		locationMap.get(el.location).add(el);
    		
    		for (String person : el.people) {
    			if (!peopleMap.containsKey(person)) {
    				peopleMap.put(person, new ArrayList<MetaData>());
    			}
    			peopleMap.get(person).add(el);
    		}
    	}
    }
 
    // MetaData로 파싱
    MetaData getMetaData(String picture) {
    	int timeIndex = picture.indexOf("],TIME:[");
    	int locationIndex = picture.indexOf("],LOC:[");
    	int peopleIndex = picture.indexOf("],PEOPLE:[");
    	
    	int id = Integer.parseInt(picture.substring(4, timeIndex));
    	
    	String[] dateTimeArr = picture.substring(timeIndex + 8, locationIndex).split(",");
    	String[] dateArr = dateTimeArr[0].split("/");
    	String[] timeArr = dateTimeArr[1].split(":");
    	LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(dateArr[0]),
    			Integer.parseInt(dateArr[1]),
    			Integer.parseInt(dateArr[2]),
    			Integer.parseInt(timeArr[0]),
    			Integer.parseInt(timeArr[1]),
    			Integer.parseInt(timeArr[2]));

    	String location = picture.substring(locationIndex + 7, peopleIndex);
    	String[] people = picture.substring(peopleIndex + 10, picture.length()).split(",");
    	
    	return new MetaData(id, dateTime, location, people);
    }
    
    // map에서 해당하는 필터의 K번째 id 가져오기
    int filterPicture(String mFilter, int K) {
    	int id = -1;
    	if (mFilter.contains("LOC:[")) {
    		String location = mFilter.substring(5, mFilter.length() - 1);
    		id = locationMap.get(location).get(locationMap.size() - K).id;
    	} else {
    		String people = mFilter.substring(8, mFilter.length() - 1);
    		id = peopleMap.get(people).get(peopleMap.size() - K).id;
    	}
    	return id;
    }
 
    // 가장 오래된 데이터는 0번째이므로 list, map에서 삭제 처리
    int deleteOldest() {
    	MetaData metaData = totalList.remove(0);
    	locationMap.get(metaData.location).remove(metaData);
    	for (String person : metaData.people) {
    		peopleMap.get(person).remove(metaData);
    	}
        return metaData.id;
    }
 
}