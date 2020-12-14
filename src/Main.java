import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

    public class Main {
        private static final Map<String, List<String>> users = new HashMap<>();
        public static void main(String[] args) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean exit = true;
            System.out.println("To exit the process please type 'EXIT'");
            System.out.println("-------------------------------------------");
            System.out.println("To create a person please type C name");
            System.out.println("To add a song to that person type S name song (in order)");
            System.out.println("To delete a song from the person type E name and the song that you want to delete");
            System.out.println("To display the songs of a person please type L name ");
            System.out.println("To show all the created people please type N");
            System.out.println("To show all the liked song please type M");
            System.out.println("To show the most popular 3 songs type R");
            System.out.println("------------------------------------------");
            System.out.println("ENTER YOUR COMMANDS");
            while (exit) {
                try {
                    String input = reader.readLine().trim(); //reads the console input and trims excess whitespace
                    String[] values = input.split("\\s", 3);
                    switch (values[0]) {
                        case "EXIT":
                            //we need a command to exit the loop and terminate the application
                            exit = true;
                            break;
                        case "C":
                            users.putIfAbsent(values[1], new LinkedList<>()); //user will be added to the users map, but only if he doesn't exist already
                            System.out.println("User "+values[1]+" successfully added.");
                            break;
                        case "S":
                            users.putIfAbsent(values[1], new LinkedList<>()); //user will be added to the users map, but only if he doesn't exist already
                            users.computeIfPresent(values[1], (k, v) -> { //we add the song to his liked songs, but only if it is not already present
                                if (!v.contains(values[2])) {
                                    v.add(values[2]);
                                }

                                return v;
                            });
                            System.out.println(values[2]+" added to "+ values[1]+"'s list.");

                            break;
                        case "E":
                            if (users.get(values[1]) != null) { //just a null pointer check if the player wouldn't exist yet
                                users.get(values[1]).remove(values[2]);

                            }
                            System.out.println("The song "+values[2]+" successfully removed from "+values[1]+ " song list.");
                            break;
                        case "L":
                            System.out.println("User " + values[1] + " likes these songs:");
                            if (users.get(values[1]) != null) { //just a null pointer check if the player wouldn't exist yet
                                users.get(values[1]).forEach(System.out::println);
                            }
                            break;
                        case "N":
                            System.out.println("Existing users are:");
                            users.keySet().forEach(System.out::println);
                            break;
                        case "M":
                            System.out.println("All liked songs: ");
                            users.values()
                                    .stream()
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))//we collect all the songs from all users and count them.
                                    .entrySet()
                                    .forEach(System.out::println);
                            break;
                        case "R":
                            System.out.println("The most popular 3 songs");
                            users.values()
                                    .stream()
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))//we collect all the songs from all users and count them.
                                    .entrySet()
                                    .stream()
                                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) //we sort the songs by the number of entries
                                    .limit(3) //limit the output to 3 entries
                                    .forEach(System.out::println);
                            break;
                        default:
                            System.out.println("Unsupported command " + values[0]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

