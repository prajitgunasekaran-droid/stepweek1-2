import java.util.*;

class DNSEntry {

    String domain;
    String ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttl * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {

    private int capacity;

    private LinkedHashMap<String, DNSEntry> cache;

    private int hits = 0;
    private int misses = 0;

    DNSCache(int capacity) {

        this.capacity = capacity;

        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {

            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > capacity;
            }
        };
    }

    // Resolve domain
    public synchronized String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null) {

            if (!entry.isExpired()) {

                hits++;
                System.out.println("Cache HIT");
                return entry.ipAddress;
            }

            else {

                System.out.println("Cache EXPIRED");
                cache.remove(domain);
            }
        }

        misses++;

        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(domain, ip, 300));

        System.out.println("Cache MISS → Querying upstream");

        return ip;
    }

    // Simulate upstream DNS lookup
    private String queryUpstreamDNS(String domain) {

        Random r = new Random();

        return "172.217.14." + r.nextInt(255);
    }

    // Cache statistics
    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }
}

class DnsMain {

    public static void main(String[] args) throws Exception {

        DNSCache dns = new DNSCache(5);

        System.out.println(dns.resolve("google.com"));

        System.out.println(dns.resolve("google.com"));

        Thread.sleep(2000);

        System.out.println(dns.resolve("youtube.com"));

        dns.getCacheStats();
    }
}