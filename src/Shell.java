import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
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

        String[] input = args;

        while(execute){
            System.out.print("[" + filePath + "]" + ": ");
            String Arg = scanner.next();

            if(Arg.equals("exit")){
                execute = false;
            }

            try {
                String[] validCommand = {"ls", "-l", "cd..", "ptime", "cd", "exit"};
                splitCommand(Arg);

                if(Arg.equals("ls")){
                    File currentDir = new File(System.getProperty("user.dir"));
                    File[] fList = currentDir.listFiles();
                    System.out.println("\n");
                    for(File f : fList){
                        System.out.println(f.getName());

                    }

                }
                else if(Arg.equals("cd..")){
                    File currentDir = new File(System.getProperty("user.dir"));
                    String f = currentDir.getParent();
                    System.setProperty("user.dir", f);
                    File newDir = new File(f);

                    filePath = newDir;

                }
                else if (Arg.equals("cd")){
                    File currentDir = new File(System.getProperty("user.dir"));
                    File[] fList = currentDir.listFiles();

                    for(int i = 0; i < fList.length; i++){
                        if(fList[i].equals(input[1])){
                            String f = System.getProperty("user.dir");
                            System.setProperty("user.dir", f);
                            String dirF = f + "/" + fList[i];
                            File newDir = new File(dirF);
                            filePath = newDir;
                        }
                    }
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



