package org.search;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filename = getFilenameFromArgs(args);
        if (filename == null) {
            System.out.println("No filename provided!");
            return;
        }

        List<Person> people;
        Map<String, Set<Integer>> invertedIndex;
        try {
            people = readPeopleDataFromFile(filename);
            invertedIndex = buildInvertedIndex(people);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        SearchEngine searchEngine = new SearchEngine(people, invertedIndex);
        while (true) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    searchEngine.performSearchQueries(scanner);
                    break;
                case 2:
                    printAllPeople(people);
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    private static String getFilenameFromArgs(String[] args) {
        if (args.length > 1 && "--data".equals(args[0])) {
            return args[1];
        }
        return null;
    }


    private static List<Person> readPeopleDataFromFile(String filename) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filename));
        List<Person> people = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            people.add(Person.parseFromInput(fileScanner.nextLine()));
        }
        fileScanner.close();
        return people;
    }

    private static void printMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }


    private static void printAllPeople(List<Person> people) {
        System.out.println("\n=== List of people ===");
        for (Person person : people) {
            System.out.println(person);
        }
    }

    private static Map<String, Set<Integer>> buildInvertedIndex(List<Person> people) {
        Map<String, Set<Integer>> index = new HashMap<>();
        for (int i = 0; i < people.size(); i++) {
            String[] words = people.get(i).toString().toLowerCase().split("\\s+");
            for (String word : words) {
                index.computeIfAbsent(word, k -> new HashSet<>()).add(i);
            }
        }
        return index;
    }
}