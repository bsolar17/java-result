# Java-Result

A small Java library providing a Result type, inspired by Rust's `Result<T, E>`.

## Usage Example

```java
import com.github.bsolar17.Result;

Result<Integer, String> divide(int a, int b) {
    if (b == 0) {
        return Result.err("division by zero");
    } else {
        return Result.ok(a / b);
    }
}
```

**Functional style:**
```java
Result<Integer, String> result = divide(10, 2);
String message = result.match(
    value -> "Success: " + value,
    error -> "Failure: " + error
);
System.out.println(message);
```

**Classic if/else:**
```java
if (result.isOk()) {
    System.out.println("Result: " + result.value());
} else {
    System.out.println("Error: " + result.error());
}
```

**Map the value if present, leaving error unchanged:**
```java
Result<String, String> mapped = result.map(Object::toString);
```

**Map the error if present, leaveing value unchanged:**
```java
Result<Integer, Integer> mappedErr = result.mapErr(String::length);
```

**Chaining map and mapErr:**
```java
Result<String, Integer> result = Result.ok(10)
.map(Object::toString)
.mapErr(i -> i + 1);
```

**Exceptions**

**Calling `error()` on an Ok throws `NoSuchElementException`:**
```java
Result<Integer, String> ok = Result.ok(42);
ok.error(); // Throws NoSuchElementException
```

**Calling `value()` on an Err throws `NoSuchElementException`:**
```java
Result<Integer, String> err = Result.err("fail");
err.value(); // Throws NoSuchElementException
```

**Using `valueOrElseThrow` to throw a custom exception:**
```java
Result<Integer, String> err = Result.err("fail");
Integer value = err.valueOrElseThrow(error -> new IllegalArgumentException(error));
// Throws IllegalArgumentException with message "fail"
```

