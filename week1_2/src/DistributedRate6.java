import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    double refillRate;
    long lastRefillTime;

    public TokenBucket(int maxTokens, double refillRate) {
        this.tokens = maxTokens;
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // Refill tokens based on time
    private void refill() {

        long now = System.currentTimeMillis();

        double tokensToAdd =
                (now - lastRefillTime) / 1000.0 * refillRate;

        if (tokensToAdd > 0) {

            tokens = Math.min(maxTokens,
                    tokens + (int) tokensToAdd);

            lastRefillTime = now;
        }
    }

    // Check request
    public synchronized boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    public int getRemainingTokens() {
        refill();
        return tokens;
    }
}

class RateLimiter {

    HashMap<String, TokenBucket> clients = new HashMap<>();

    int MAX_REQUESTS = 1000;
    int WINDOW_SECONDS = 3600;

    public boolean checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId,
                new TokenBucket(MAX_REQUESTS,
                        (double) MAX_REQUESTS / WINDOW_SECONDS));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {

            System.out.println("Allowed (" +
                    bucket.getRemainingTokens() +
                    " requests remaining)");

            return true;
        }

        else {

            System.out.println(
                    "Denied (Rate limit exceeded)");

            return false;
        }
    }

    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        int used = MAX_REQUESTS - bucket.getRemainingTokens();

        System.out.println("Status → used: " +
                used +
                ", limit: " +
                MAX_REQUESTS);
    }
}

class RateLimiterMain {

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        for (int i = 0; i < 5; i++) {

            limiter.checkRateLimit("abc123");
        }

        limiter.getRateLimitStatus("abc123");
    }
}