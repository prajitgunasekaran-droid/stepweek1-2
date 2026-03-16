import java.util.*;

class AnalyticsDashboard {

    HashMap<String, Integer> pageViews = new HashMap<>();
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    HashMap<String, Integer> sourceCount = new HashMap<>();

    // Process incoming page view event
    public void processEvent(String url, String userId, String source) {

        // Update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Track unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // Update traffic source count
        sourceCount.put(source, sourceCount.getOrDefault(source, 0) + 1);
    }

    // Display dashboard
    public void getDashboard() {

        System.out.println("\nTop Pages:");

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        int rank = 1;

        for (Map.Entry<String, Integer> entry : list) {

            if (rank > 10) break;

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(rank + ". " + url +
                    " - " + views +
                    " views (" + unique + " unique)");

            rank++;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;

        for (int count : sourceCount.values())
            total += count;

        for (String source : sourceCount.keySet()) {

            int count = sourceCount.get(source);

            double percent = (count * 100.0) / total;

            System.out.println(source + ": " +
                    String.format("%.1f", percent) + "%");
        }
    }
}
class AnalyticsMain {

    public static void main(String[] args) {

        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        dashboard.processEvent("/article/breaking-news", "user_123", "google");
        dashboard.processEvent("/article/breaking-news", "user_456", "facebook");
        dashboard.processEvent("/sports/championship", "user_111", "direct");
        dashboard.processEvent("/sports/championship", "user_222", "google");
        dashboard.processEvent("/sports/championship", "user_333", "google");
        dashboard.processEvent("/article/breaking-news", "user_123", "google");

        dashboard.getDashboard();
    }
}