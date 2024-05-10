package etc.사진관리;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateInputMain {
    // 생성된 데이터를 파일에 쓰기
    public static void writeToFile(List<String> data, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String line : data) {
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) {
		GenerateInput generateInput = new GenerateInput();
		List<String> result = new ArrayList<>();
        List<String> randomData = generateInput.generateRandomData();
        result.add("25 100");
        for (int tc = 0; tc < 25; tc++) {
        	result.add(String.valueOf(randomData.size()));
            for (String data : randomData) {
            	result.add(data);
                System.out.println(data);
            }
        }
        // 파일로 출력
        writeToFile(result, "./input.txt");
    }
}
