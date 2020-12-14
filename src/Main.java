import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

    public class Main {
        private static Map<String, List<String>> users = new HashMap<>();
        public static void main(String[] args) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean exit = true;
            System.out.println("Enter command");
            System.out.println("To exit the process please type 'EXIT'");
            while (exit) {
                try {
                    String input = reader.readLine().trim(); //reads the console input and trims excess whitespace
                    String[] values = input.split("\\s", 3);
                    int length=values.length;
                    switch (values[0]) {
                        case "EXIT":
                            //we need a command to exit the loop and terminate the application
                            exit = true;
                            break;
                        case "C":
                            users.putIfAbsent(values[1], new LinkedList<>()); //user will be added to the users map, but only if he doesn't exist already
                            break;
                        case "S":
                            users.putIfAbsent(values[1], new LinkedList<>()); //user will be added to the users map, but only if he doesn't exist already
                            users.compute(values[1], (k, v) -> { //we add the song to his liked songs, but only if it is not already present
                                if (!v.contains(values[2])) {
                                    v.add(values[2]);
                                }

                                return v;
                            })
                            ;
                            break;
                        case "E":
                            if (users.get(values[1]) != null) { //just a null pointer check if the player wouldn't exist yet
                                users.get(values[1]).remove(values[2]);

                            }
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
                                    .flatMap(songs -> songs.stream())
                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))//we collect all the songs from all users and count them.
                                    .entrySet()
                                    .forEach(entry -> System.out.println(entry));
                            break;
                        case "R":
                            System.out.println("The most popular 3 songs");
                            users.values()
                                    .stream()
                                    .flatMap(songs -> songs.stream())
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

