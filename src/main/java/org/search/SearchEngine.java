package org.search;

import java.util.*;

public class SearchEngine {
    private final List<Person> people;
    private final Map<String, Set<Integer>> invertedIndex;

    public SearchEngine(List<Person> people, Map<String, Set<Integer>> invertedIndex) {
        this.people = people;
        this.invertedIndex = invertedIndex;
    }

    public void performSearchQueries(Scanner scanner) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine().toUpperCase();
        System.out.println("Enter a name or email to search all suitable people.");
        String query = scanner.nextLine().toLowerCase();
        Set<String> queryWords = new HashSet<>(Arrays.asList(query.split("\\s+")));

        Set<Integer> resultIndexes;
        switch (strategy) {
            case "ALL":
                resultIndexes = searchAll(queryWords);
                break;
            case "ANY":
                resultIndexes = searchAny(queryWords);
                break;
            case "NONE":
                resultIndexes = searchNone(queryWords);
                break;
            default:
                System.out.println("Unknown strategy: " + strategy);
                return;
        }

        printSearchResults(resultIndexes);
    }

    private Set<Integer> searchAll(Set<String> queryWords) {
        Set<Integer> indexes = new HashSet<>();
        for (String word : queryWords) {
            if (invertedIndex.containsKey(word)) {
                if (indexes.isEmpty()) {
                    indexes.addAll(invertedIndex.get(word));
                } else {
                    indexes.retainAll(invertedIndex.get(word));
                }
            } else {
                return Collections.emptySet();
            }
        }
        return indexes;
    }

    private Set<Integer> searchAny(Set<String> queryWords) {
        Set<Integer> indexes = new HashSet<>();
        for (String word : queryWords) {
            if (invertedIndex.containsKey(word)) {
                indexes.addAll(invertedIndex.get(word));
            }
        }
        return indexes;
    }

    private Set<Integer> searchNone(Set<String> queryWords) {
        Set<Integer> indexes = new HashSet<>();
        for (int i = 0; i < people.size(); i++) {
            indexes.add(i);
        }
        for (String word : queryWords) {
            if (invertedIndex.containsKey(word)) {
                indexes.removeAll(invertedIndex.get(word));
            }
        }
        return indexes;
    }

    private void printSearchResults(Set<Integer> indexes) {
        if (indexes.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(indexes.size() + " persons found:");
            for (Integer index : indexes) {
                System.out.println(people.get(index));
            }
        }
    }
}

