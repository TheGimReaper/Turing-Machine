import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author  Alison Gim
 */
public class gim_p1
{
    public static void main(String[] args)
    {
        File file = new File(args[0]);
        int moves = 0; 
        try
        {
            moves = Integer.parseInt(args[2]);
        }
        catch
        (NumberFormatException e)
        {
            System.err.println("!!!");
            e.printStackTrace();
        }
        finally
        {}

        ArrayList<Integer> acceptStates = new ArrayList<>();    // List of accept states
        ArrayList<Integer> rejectStates = new ArrayList<>();    // List of reject states
        int startState = 0;     // Start state1

        ArrayList<Integer> fromState = new ArrayList<>();   // index 1
        ArrayList<Character> readVal = new ArrayList<>();   // index 2
        ArrayList<Integer> toState = new ArrayList<>();     // index 3
        ArrayList<Character> writeVal = new ArrayList<>();  // index 4
        ArrayList<Character> direction = new ArrayList<>(); // index 5

        try 
        {
            Scanner sc = new Scanner(file);

            // Go through file line by line
            while (sc.hasNextLine())
            {
                String[] currentLine = sc.nextLine().trim().split("\\t+");
                // if line begins with "state" 
                if (currentLine[0].equals("state"))
                {
                    try
                    {
                        if (currentLine[2].equals("accept"))
                        {
                            acceptStates.add(Integer.parseInt(currentLine[1]));
                        }
                        else if (currentLine[2].equals("reject"))
                        {
                            rejectStates.add(Integer.parseInt(currentLine[1]));
                        }
                        else
                        {
                            startState = Integer.parseInt(currentLine[1]);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.err.println("WAIT A MINUTE >:( File doesnt have the right format!");
                        e.printStackTrace();
                    }
                    finally
                    {}
                }
                else    // if line begins with "transition"
                {
                    try
                    {
                        fromState.add(Integer.parseInt(currentLine[1]));
                        readVal.add(currentLine[2].charAt(0));
                        toState.add(Integer.parseInt(currentLine[3]));
                        writeVal.add(currentLine[4].charAt(0));
                        direction.add(currentLine[5].charAt(0));
                    }
                    catch (NumberFormatException e)
                    {
                        System.err.println("BAD INPUT >:(((((((((((");
                        e.printStackTrace();
                    }
                    finally
                    {}
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {}

        // Process input string:
        String inputString = args[1];              // Initial conditions
        int currentState = startState;
        int whereIsArrow = moves;
        int transitionIndex = 0;

        // Made long arraylist
        ArrayList<Character> input = new ArrayList<>();
        for (int i = 0; i < moves * 2 + inputString.length(); i++)
        {
            input.add('_');
        }

        // Put inputString in the middle
        for (int i = 0; i < inputString.length(); i++)
        {
            input.set(moves + i, inputString.charAt(i));
        }

        // Do transitions
        String outputString = "";
        for (int i = 0; i < moves; i++)
        {
            if (i > 0)
            {
                outputString = outputString + "->" + Integer.toString(currentState);
            }
            else
            {
                outputString = Integer.toString(currentState);
            }

            // Check if we are in accept or reject state
            for (int j : acceptStates)
            {
                if (j == currentState)
                {
                    outputString = outputString + " accept";
                    System.out.println(outputString);
                    System.exit(0);
                }
            }
            for (int j : rejectStates)
            {
                if (j == currentState)
                {
                    outputString = outputString + " reject";
                    System.out.println(outputString);
                    System.exit(0);
                }
            }

            // Go through fromState list and match with currentState
            // Match the readVal.at(transitionIndex) with input.charAt(whereIsArrow) -> set transitionIndex
            int j = 0;
            for (; j < fromState.size(); j++)
            {
                if ((fromState.get(j) == currentState) && (readVal.get(j) == input.get(whereIsArrow)))
                {
                    transitionIndex = j;
                    break;
                }
            }

            // Cannot find matching transition
            if (j == fromState.size())
            {
                outputString = outputString + " reject";
                System.out.println(outputString);
                System.exit(0);
            }

            // Match found, set new state
            currentState = toState.get(transitionIndex);

            // Write over charAt(whereIsArrow) with writeVal
            input.set(whereIsArrow, writeVal.get(transitionIndex));
            
            // If direction(transitionIndex) is left, whereisarrow--, right is whereisarrow++
            if (direction.get(transitionIndex) == 'L')
            {
                whereIsArrow--;
            }
            else
            {
                whereIsArrow++;
            }
        }

        // Reached max moves
        outputString = outputString + " quit";
        System.out.println(outputString);
    }
}
