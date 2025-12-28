package com.github.bsolar17.result;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Java idiomatic Result interface, similar to Rust's Result<T, E>.
 */
public interface Result<T, E> {
    static <T, E> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    static <T, E> Result<T, E> err(E error) {
        return new Err<>(error);
    }

    boolean isOk();

    boolean isErr();

    T value() throws NoSuchElementException;

    E error() throws NoSuchElementException;

    T valueOrElse(T defaultValue);

    <X extends Throwable> T valueOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    <U> Result<U, E> map(Function<? super T, ? extends U> mapper);

    <F> Result<T, F> mapErr(Function<? super E, ? extends F> mapper);

    <R> R match(
        Function<? super T, ? extends R> onValue,
        Function<? super E, ? extends R> onError
    );
}
