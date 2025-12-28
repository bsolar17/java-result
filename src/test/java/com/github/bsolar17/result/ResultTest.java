package com.github.bsolar17.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    void okResultBehavesCorrectly() {
        final Result<Integer, String> result = Result.ok(42);
        assertTrue(result.isOk());
        assertFalse(result.isErr());
        assertEquals(42, result.value());
        assertEquals(42, result.valueOrElse(99));
        assertThrows(NoSuchElementException.class, result::error);
        assertEquals(42, result.valueOrElseThrow(() -> new RuntimeException("should not throw")));
    }

    @Test
    void matchMethodWorks() {
        final Result<Integer, String> ok = Result.ok(123);
        final Result<Integer, String> err = Result.err("fail");
        final String okResult = ok
            .match(
                v -> "ok:" + v,
                e -> "err:" + e
            );
        assertEquals("ok:123", okResult);
        final String errResult = err
            .match(
                v -> "ok:" + v,
                e -> "err:" + e
            );
        assertEquals("err:fail", errResult);
    }

    @Test
    void errResultBehavesCorrectly() {
        final Result<Integer, String> result = Result.err("fail");
        assertFalse(result.isOk());
        assertTrue(result.isErr());
        assertEquals("fail", result.error());
        assertEquals(99, result.valueOrElse(99));
        assertThrows(NoSuchElementException.class, result::value);
        final RuntimeException ex = new RuntimeException("custom");
        final RuntimeException thrown = assertThrows(
            RuntimeException.class,
            () -> result.valueOrElseThrow(() -> ex)
        );
        assertEquals(ex, thrown);
    }

    @Test
    void mapAndMapErrWork() {
        final Result<Integer, String> ok = Result.ok(10);
        final Result<String, String> mapped = ok.map(Object::toString);
        assertEquals("10", mapped.value());
        final Result<Integer, String> err = Result.err("fail");
        final Result<Integer, Integer> mappedErr = err.mapErr(String::length);
        assertEquals(4, mappedErr.error());
    }

    @Test
    void clientUsageExample() {
        // Example method
        class Divider {
            Result<Integer, String> divide(int a, int b) {
                if (b == 0) {
                    return Result.err("division by zero");
                }
                return Result.ok(a / b);
            }
        }
        final Divider divider = new Divider();
        final Result<Integer, String> result = divider.divide(10, 2);
        final String okResult = result.match(v -> "ok:" + v, e -> "err:" + e);
        assertEquals("ok:5", okResult);

        final Result<Integer, String> errorResult = divider.divide(10, 0);
        final String errResult = errorResult.match(v -> "ok:" + v, e -> "err:" + e);
        assertEquals("err:division by zero", errResult);
    }
}
