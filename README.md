# Java Matching Engine Demo

A **high-performance order matching engine** implemented in **Java** with **Spring Boot**. Designed to demonstrate real-time matching of buy and sell orders with minimal latency, using a concurrent, thread-safe order book.

---

## Features

- **Buy and Sell Order Matching:** Supports limit orders for BUY and SELL types.
- **Concurrent Matching:** Daemon thread continuously matches orders in real-time.
- **Benchmark Ready:** Allows measuring enqueue and match performance without blocking threads.
- **Spring Boot Integration:** `MatchingEngine` is a fully injectable Spring component.
- **Flexible Order Book:** Uses price-priority queues: descending for buyers, ascending for sellers.
- **In-Trade Status:** Tracks whether matching is currently in progress.
- **Thread-Safe:** Uses `ReentrantLock` to prevent race conditions.
- **Testable:** Provides benchmark and integration tests for verifying performance and correctness.

---

## Technology Stack

- **Java 21+**
- **Spring Boot 3.5**
- **JUnit 5** for testing
- **ReentrantLock** for thread-safe order book operations
- **Maven** for build and dependency management

---

## Project Structure

```
src/main/java
└── com.alireza.engine
    ├── controller        # REST APIs for order operations
    ├── domain            # Order and OrderType classes
    ├── dto               # Data Transfer Objects
    ├── mapper            # Mapping between DTOs and entities
    ├── matching          # MatchingEngine component and logic
    ├── repository        # Repository interfaces (if DB used)
    └── service           # Service layer for business logic
```

---

## Benchmark Example

The engine can enqueue and match orders very quickly. Example performance:

```
Time to enqueue 100000 orders: 93 ms
```

This demonstrates the high throughput of the engine under sequential order submission.

---

## Running Tests

- **Integration tests:** Test `MatchingEngine` behavior as a Spring component.
- **Benchmark test:** Measures performance without blocking the matching thread.

Run with:

```
./mvnw test
```

---

## Usage

1. **Inject **`` into any Spring component or service.
2. **Add orders** using `addOrder(Order order)`.
3. **Monitor** `isInTrade()` for active matching.
4. **Use benchmark tests** to measure performance for large order volumes.

---

## License

This project is licensed under the **MIT License**. You are free to use, modify, and distribute this software under the following conditions:

- Include the original license and copyright notice in all copies or substantial portions of the software.
- Provided "as-is" without warranty of any kind.

For full license text, see `LICENSE` file.

---

## Notes

- The matching engine runs a background thread for continuous matching.
- The `inTrade` flag allows checking if matching is actively processing orders.
- Suitable as a reference for building real trading systems or learning concurrent order matching logic.
- Designed for educational and benchmark purposes, not production trading.

---

## Contact

Created By: **Alireza Dolatabadi**\
Email: `alireza.dolatabadi.jc@gmail.com`\
GitHub: `https://github.com/AlirezaDa-jc/Java-Matching-Engine-Demo`

