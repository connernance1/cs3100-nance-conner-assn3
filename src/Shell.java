import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static javafx.application.Platform.exit;


public class Shell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fP = System.getProperty("user.dir");
        File filePath = new File(fP);
        boolean execute = true;
        List<String> history = new ArrayList<String>();
        int ct = 1;

        String[] input = args;
        System.out.println("\n" +
                " _______                           _________.__           .__  .__   \n" +
                " \\      \\ _____    ____   ____    /   _____/|  |__   ____ |  | |  |  \n" +
                " /   |   \\\\__  \\  /    \\ /  _ \\   \\_____  \\ |  |  \\_/ __ \\|  | |  |  \n" +
                "/    |    \\/ __ \\|   |  (  <_> )  /        \\|   Y  \\  ___/|  |_|  |__\n" +
                "\\____|__  (____  /___|  /\\____/  /_______  /|___|  /\\___  >____/____/\n" +
                "        \\/     \\/     \\/                 \\/      \\/     \\/           \n" +
                "                                                                     \n" +
                "                                                                     \n" +
                "                                                                     \n" +
                "                                                                     \n" +
                "                                                                     \n" +
                "                                                                     \n");
        while(execute){


            System.out.print("[" + filePath + "]" + " : " + System.getProperty("user.name") + "@Desktop" + " $ ");
            String Arg = scanner.nextLine();
            System.out.println("\n");

            if(Arg.equals("exit")){
                execute = false;
            }

            try {
                String[] validCommand = {"ls", "-l", "cd..", "ptime", "cd", "exit"};
                String[] A = splitCommand(Arg);

//                for(String item : A){
//                    System.out.println(item + "\n");
//                }


                if(A[0].equals("ls")){
                    File currentDir = new File(System.getProperty("user.dir"));
                    File[] fList = currentDir.listFiles();

                    if(A.length > 1){
                        if(A[1].equals("-l")){
                            System.out.println("Total: " + fList.length);
                        }
                        history.add(ct + " : " + "ls -l");
                        ct++;
                        System.out.println("\n");
                    }
                    else if(A.length == 1){
                        System.out.println("\n");
                        for(File f : fList){
                            System.out.println(f.getName());
                        }
                        history.add(ct + " : " + "ls");
                        ct++;
                        System.out.println("\n");
                    }
                    else {
                        System.out.println("Please enter a valid Argument: " + A[1] + "\n");
                    }
                }
                else if(A[0].equals("cd..")){
                    File currentDir = new File(System.getProperty("user.dir"));
                    String f = currentDir.getParent();
                    System.setProperty("user.dir", f);
                    File newDir = new File(f);
                    filePath = newDir;

                    history.add(ct + " : " + "cd.. ");
                    ct++;
                }
                else if (A[0].equals("cd")){
                    File currentDir = new File(System.getProperty("user.dir"));
                    File[] fList = currentDir.listFiles();

                    for(File item : fList){

                        if(item.getName().equals(A[1])){
                            String dirF = "/" + item;
                            System.setProperty("user.dir", dirF);
                            File newDir = new File(dirF);
                            filePath = newDir;
                        }
                    }
                    history.add(ct + " : " + "cd " + A[1]);
                    ct++;
                }
                else if(A[0].equals("history")){
                    System.out.println("-- Command History --");
                    for(String item : history){
                        System.out.println(item);
                    }
                    history.add(ct + " : " + "history");
                    ct++;
                }
                else if(A[0].equals("^")){


                }
                else {
                    System.out.println("Please Enter a valid Command. B*tch.");
                }


            }
            catch (Exception e){
                System.out.println("Illegueelll Command .... Ribbet: " + Arg);
            }

        }
    }


    /**
     * Split the user command by spaces, but preserving them when inside double-quotes.
     * Code Adapted from: https://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double
     */
    public static String[] splitCommand(String command) {
        java.util.List<String> matchList = new java.util.ArrayList<>();

        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(command);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null) {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            } else {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }

        return matchList.toArray(new String[matchList.size()]);
    }
}



