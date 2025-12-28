package com.github.bsolar17.result;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

class Err<T, E> implements Result<T, E> {
    private final E error;

    Err(E error) {
        this.error = error;
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public T value() {
        throw new NoSuchElementException("Called value() on Err");
    }

    @Override
    public E error() {
        return error;
    }

    @Override
    public T valueOrElse(T defaultValue) {
        return defaultValue;
    }

    @Override
    public <X extends Throwable> T valueOrElseThrow(Supplier<? extends X> exceptionSupplier)
        throws X {
        throw exceptionSupplier.get();
    }

    @Override
    public <U> Result<U, E> map(Function<? super T, ? extends U> mapper) {
        return new Err<>(error);
    }

    @Override
    public <F> Result<T, F> mapErr(Function<? super E, ? extends F> mapper) {
        return new Err<>(mapper.apply(error));
    }

    @Override
    public <R> R match(
        Function<? super T, ? extends R> onValue,
        Function<? super E, ? extends R> onError
    ) {
        return onError.apply(error);
    }
}
