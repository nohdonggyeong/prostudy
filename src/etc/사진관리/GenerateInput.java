package etc.사진관리;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class GenerateInput {

    static final int LINES = 1000;
    private static int previousId = 0; // Start from 1581
    private static long previousTimeMillis = -2469737100000L; // July 30, 1909
    private static long endMillis = -2229298800000L; // September 13, 1909

    
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
    	String[] people = picture.substring(peopleIndex + 10, picture.length() - 1).split(",");
    	
    	return new MetaData(id, dateTime, location, people);
    }
    
    // map에서 해당하는 필터의 K번째 id 가져오기
    int filterPicture(String mFilter, int K) {
    	int id = -1;
    	if (mFilter.contains("LOC:[")) {
    		String location = mFilter.substring(5, mFilter.length() - 1);
    		id = locationMap.get(location).get(locationMap.get(location).size() - K).id;
    	} else {
    		String people = mFilter.substring(8, mFilter.length() - 1);
    		id = peopleMap.get(people).get(peopleMap.get(people).size() - K).id;
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
 
    // 새로운 ID 생성
    private int generateNextID() {
        previousId++;
        return previousId;
    }

    // 새로운 시간 생성
    private String generateNextTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd,HH:mm:ss");
        long randomMillisSincePrevious = (long) (Math.random() * 500000000); // Random interval in milliseconds
        previousTimeMillis += randomMillisSincePrevious;
        if (previousTimeMillis > endMillis) {
            previousTimeMillis = endMillis;
        }
        return dateFormat.format(new Date(previousTimeMillis));
    }

    // 무작위 위치 생성
    private String getRandomLocation() {
        String[] locations = {"home", "school", "park", "office", "store", "restaurant", "hospital", "library", "gym"};
        Random random = new Random();
        return locations[random.nextInt(locations.length)];
    }

    // 무작위 사람 생성
    private String[] people = {"me", "mom", "dad", "sister", "brother", "grandma", "grandpa", "uncle", "aunt"};
    private List<String> getRandomPeople() {
        List<String> selectedPeople = new ArrayList<>();
        Random random = new Random();
        int numPeople = random.nextInt(5) + 1; // 최소 1명부터 최대 5명까지
        for (int i = 0; i < numPeople; i++) {
            String person;
            do {
                person = people[random.nextInt(people.length)];
            } while (selectedPeople.contains(person));
            selectedPeople.add(person);
        }
        return selectedPeople;
    }

    // 데이터 생성
    public List<String> generateRandomData() {
        List<String> data = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int remainingLines = LINES;
        while (remainingLines > 0) {
        	int linesInBundle = 0;
        	if (remainingLines == LINES) {
        		linesInBundle = 50;
        	} else {
        		linesInBundle = Math.min(new Random().nextInt(100) + 1, remainingLines);
        	}
            for (int i = 0; i < linesInBundle; i++) {
                int id = generateNextID();
                String time = generateNextTime();
                String loc = getRandomLocation();
                List<String> people = getRandomPeople();
                String peopleString = String.join(",", people);
                String dataString = String.format("ID:[%d],TIME:[%s],LOC:[%s],PEOPLE:[%s]", id, time, loc, peopleString);
                sb.append(dataString);
                if (i < linesInBundle - 1) {
                    sb.append(" ");
                }
            }
            if (remainingLines == LINES) {
            	data.add(String.format("1 %d %s", linesInBundle, sb.toString())); // Adding '1 ' as prefix
            	StringTokenizer st = new StringTokenizer(sb.toString());
				String[] mPictureList = new String[st.countTokens()];
				int index = 0;
				while (st.hasMoreTokens()) {
					mPictureList[index++] = st.nextToken();
				}
				init(linesInBundle, mPictureList);
            } else {
            	data.add(String.format("2 %d %s", linesInBundle, sb.toString())); // Adding '2 ' as prefix
            	StringTokenizer st = new StringTokenizer(sb.toString());
				String[] mPictureList = new String[st.countTokens()];
				int index = 0;
				while (st.hasMoreTokens()) {
					mPictureList[index++] = st.nextToken();
				}
				savePicture(linesInBundle, mPictureList);
            }
            remainingLines -= linesInBundle;
            sb.setLength(0);
            // Add a random single location line
            if (Math.random() <= 0.2) { // Randomly decide whether to add a location line
                String randomLocation = getRandomLocation();
                while (!locationMap.containsKey(randomLocation)) {
                	randomLocation = getRandomLocation();
                }
                int K = new Random().nextInt(locationMap.get(randomLocation).size()) + 1;
                int res = filterPicture("LOC:[" + randomLocation + "]", K);
                data.add(String.format("3 LOC:[%s] %d %d", randomLocation, K, res));
                remainingLines--; // Account for the additional location line
            }
            sb.setLength(0);
            // Add a random single people line
            if (Math.random() <= 0.2) { // Randomly decide whether to add a location line
                String randomPeople = getRandomPeople().get(0);
                while (!peopleMap.containsKey(randomPeople)) {
                	randomPeople = getRandomPeople().get(0);
                }
                int K = new Random().nextInt(peopleMap.get(randomPeople).size()) + 1;
                int res = filterPicture("PEOPLE:[" + randomPeople + "]", K);
                data.add(String.format("3 PEOPLE:[%s] %d %d", randomPeople, new Random().nextInt(10) + 1, res));
                remainingLines--; // Account for the additional location line
            }
            sb.setLength(0);
            // Add a random delete command line
            if (Math.random() <= 0.1) { // Randomly decide whether to add a location line
            	int res = deleteOldest();
                data.add(String.format("4 %d", res));
                remainingLines--; // Account for the additional location line
                
            }
            sb.setLength(0);
        }
        return data;
    }
}
