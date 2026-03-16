import java.util.*;

class VideoData {

    String videoId;
    String title;

    VideoData(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
    }
}

class MultiLevelCache {

    int L1_CAPACITY = 3;
    int L2_CAPACITY = 5;

    // L1 Cache (LRU)
    LinkedHashMap<String, VideoData> L1 =
            new LinkedHashMap<String, VideoData>(16, 0.75f, true) {

                protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                    return size() > L1_CAPACITY;
                }
            };

    // L2 Cache
    HashMap<String, VideoData> L2 = new HashMap<>();

    // Database (L3)
    HashMap<String, VideoData> database = new HashMap<>();

    int L1Hits = 0;
    int L2Hits = 0;
    int L3Hits = 0;

    public MultiLevelCache() {

        // Simulated database
        database.put("video1", new VideoData("video1","Movie A"));
        database.put("video2", new VideoData("video2","Movie B"));
        database.put("video3", new VideoData("video3","Movie C"));
        database.put("video4", new VideoData("video4","Movie D"));
        database.put("video5", new VideoData("video5","Movie E"));
    }

    public VideoData getVideo(String videoId) {

        // L1 Cache
        if (L1.containsKey(videoId)) {

            L1Hits++;
            System.out.println("L1 Cache HIT");

            return L1.get(videoId);
        }

        System.out.println("L1 Cache MISS");

        // L2 Cache
        if (L2.containsKey(videoId)) {

            L2Hits++;
            System.out.println("L2 Cache HIT → Promoted to L1");

            VideoData v = L2.get(videoId);
            L1.put(videoId, v);

            return v;
        }

        System.out.println("L2 Cache MISS");

        // Database
        if (database.containsKey(videoId)) {

            L3Hits++;
            System.out.println("Database HIT → Added to L2");

            VideoData v = database.get(videoId);

            if (L2.size() >= L2_CAPACITY) {
                Iterator<String> it = L2.keySet().iterator();
                L2.remove(it.next());
            }

            L2.put(videoId, v);

            return v;
        }

        return null;
    }

    public void printStats() {

        int total = L1Hits + L2Hits + L3Hits;

        System.out.println("\nCache Statistics");

        System.out.println("L1 Hits: " + L1Hits);
        System.out.println("L2 Hits: " + L2Hits);
        System.out.println("L3 Hits: " + L3Hits);

        if (total > 0) {
            System.out.println("Overall Hit Rate: "
                    + (100.0 * (L1Hits + L2Hits) / total) + "%");
        }
    }
}

class VideodataMain {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video1");
        cache.getVideo("video1");
        cache.getVideo("video2");
        cache.getVideo("video3");
        cache.getVideo("video1");

        cache.printStats();
    }
}