import java.util.*;

class PlagiarismDetector {

    HashMap<String, Set<String>> index = new HashMap<>();
    int N = 3; // using 3-gram for easier testing

    // Generate n-grams
    private List<String> generateNgrams(String text) {

        List<String> result = new ArrayList<>();

        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {

            String gram = "";

            for (int j = 0; j < N; j++) {
                gram += words[i + j] + " ";
            }

            result.add(gram.trim());
        }

        return result;
    }

    // Add document to database
    public void addDocument(String docId, String text) {

        List<String> ngrams = generateNgrams(text);

        for (String gram : ngrams) {

            index.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }

    // Analyze new document
    public void analyzeDocument(String docId, String text) {

        List<String> ngrams = generateNgrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            if (index.containsKey(gram)) {

                for (String doc : index.get(gram)) {

                    matchCount.put(doc,
                            matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        System.out.println("Extracted " + ngrams.size() + " n-grams");

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);

            double similarity = (matches * 100.0) / ngrams.size();

            String status;

            if(similarity > 50)
                status = "PLAGIARISM DETECTED";
            else if(similarity > 20)
                status = "Suspicious";
            else
                status = "Normal";

            System.out.println(
                    "Found " + matches +
                            " matching n-grams with " + doc +
                            " → Similarity: " +
                            String.format("%.2f", similarity) +
                            "% (" + status + ")"
            );
        }
    }
}

class TransactionMain {

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        // Existing documents
        detector.addDocument("essay_089",
                "data structures improve program efficiency and memory management");

        detector.addDocument("essay_092",
                "data structures improve program efficiency in modern computing");

        // New document to analyze
        detector.analyzeDocument("essay_123",
                "data structures improve program efficiency and performance");
    }
}