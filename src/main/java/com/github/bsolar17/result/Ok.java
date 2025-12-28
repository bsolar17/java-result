package com.github.bsolar17.result;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

class Ok<T, E> implements Result<T, E> {
    private final T value;

    Ok(T value) {
        this.value = value;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public E error() {
        throw new NoSuchElementException("Called error() on Ok");
    }

    @Override
    public T valueOrElse(T defaultValue) {
        return value;
    }

    @Override
    public <X extends Throwable> T valueOrElseThrow(
        Supplier<? extends X> exceptionSupplier
    ) throws X {
        return value;
    }

    @Override
    public <U> Result<U, E> map(Function<? super T, ? extends U> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public <F> Result<T, F> mapErr(Function<? super E, ? extends F> mapper) {
        return new Ok<>(value);
    }

    @Override
    public <R> R match(
        Function<? super T, ? extends R> onValue,
        Function<? super E, ? extends R> onError
    ) {
        return onValue.apply(value);
    }
}
