### Question 1: Three Pillars of Observability
#### What are the three pillars of observability, and what is the primary purpose of each?
* The three pillars that work together to provide full system visibility are:
1. Metrics ("What is happening?"): These are numeric measurements collected at intervals. Their primary purpose is to show trends and overall system health (e.g., "Is CPU usage high?", "Are error rates spiking?").
2. Logs ("What happened?"): These are immutable, timestamped records of discrete events. Their purpose is to provide detailed context about specific events or errors (e.g., "Why did this specific payment fail?", "What was the error message?").
3. Traces ("How did it happen?"): These track the path of a request as it flows through a distributed system. Their purpose is to help pinpoint exactly where latency or failures occur across multiple microservices (e.g., "Which service slowed down the user login request?").

### Question 2: Metrics Data Types
#### Explain the four common metric types and give an example use case for each.
* There are four primary metric types used in Prometheus and similar systems:
1. Counter:
* Explanation: A value that only increases (monotonically) or stays the same; it never goes down until a restart.
* Use Case: Counting total HTTP requests (`http_requests_total`) or total errors.
2. Gauge:
* Explanation: A value that can go up and down, representing a snapshot of the current state.
* Use Case: Memory usage (`memory_usage_bytes`) or the number of currently active connections.
3. Histogram:
* Explanation: Samples observations, like request durations, and counts them into configurable "buckets." It is used to calculate quantiles, like p99, on the server side.
* Use Case: Tracking request latency (`http_request_duration_seconds`) or request sizes.
4. Summary:
* Explanation: Similar to a Histogram but calculates exact percentiles on the client side (application side) rather than the server side.
* Use Case: When you need exact percentiles for something like payload sizes, but it is more expensive to compute than a histogram.



