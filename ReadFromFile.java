import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {

    public static String[] run(String FILENAME) throws IOException {

        int count = CountLines.run(FILENAME);
//        System.out.println("Count:"+count);
        String[] lines = new String[count+1];
        int i=0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
//                System.out.println("Line "+i+"\n"+sCurrentLine);
                lines[i]=sCurrentLine;
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;

    }

}
