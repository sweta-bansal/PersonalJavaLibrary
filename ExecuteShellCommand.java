import java.io.*;
public class ExecuteShellCommand {

    public static String run(String command) {

        StringBuffer output = new StringBuffer();
        Process p;

        try {
            p = Runtime.getRuntime().exec(command);
            //p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
                System.out.println(line.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("\n"+output.toString()+"\n");
        return output.toString();
    }

}
