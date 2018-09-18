import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class TuringMachine
{

    public static void main(String args[]) throws Exception
    {
        File file = new File(args[1]);
        Scanner sc = new Scanner(file);
        String currentLine;
        ArrayList<String> words = new ArrayList<String>();
        while (sc.hasNextLine())
        {
            currentLine = sc.nextLine().trim();
            int begin = 0;
            for (int i = 0; i < currentLine.length(); i++)
            {
                
            }
        }
    }
}