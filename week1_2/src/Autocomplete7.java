import java.util.*;

class TrieNode {

    HashMap<Character, TrieNode> children = new HashMap<>();
    List<String> queries = new ArrayList<>();
}

class AutocompleteSystem {

    TrieNode root = new TrieNode();

    HashMap<String, Integer> frequency = new HashMap<>();


    // Add query to system
    public void addQuery(String query) {

        frequency.put(query, frequency.getOrDefault(query, 0) + 1);

        TrieNode node = root;

        for (char c : query.toCharArray()) {

            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);

            node.queries.add(query);
        }
    }

    // Search suggestions
    public void search(String prefix) {

        TrieNode node = root;

        for (char c : prefix.toCharArray()) {

            if (!node.children.containsKey(c)) {
                System.out.println("No suggestions found");
                return;
            }

            node = node.children.get(c);
        }

        List<String> results = node.queries;

        PriorityQueue<String> pq =
                new PriorityQueue<>((a, b) ->
                        frequency.get(b) - frequency.get(a));

        pq.addAll(results);

        System.out.println("\nSuggestions for \"" + prefix + "\":");

        int count = 0;

        while (!pq.isEmpty() && count < 10) {

            String q = pq.poll();

            System.out.println(
                    (count + 1) + ". " +
                            q +
                            " (" + frequency.get(q) + " searches)"
            );

            count++;
        }
    }
}
class AutocompleteMain {

    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        system.addQuery("java tutorial");
        system.addQuery("java tutorial");
        system.addQuery("javascript");
        system.addQuery("java download");
        system.addQuery("java 21 features");

        system.search("jav");

        system.addQuery("java 21 features");

        system.search("java");
    }
}