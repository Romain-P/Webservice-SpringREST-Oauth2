package com.ortec.gta.shared;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author: romain.pillot
 * @Date: 12/10/2017
 */
public interface Try<T> {

    @FunctionalInterface
    interface ThrowableSupplier<U> {
        U get() throws Exception, Error;
    }

    @FunctionalInterface
    interface ThrowableFunction<I, O> {
        O apply(I input) throws Exception, Error;
    }

    /**
     * @param supplier the supplier that contain your evaluation
     * @param <U> the value to get on success
     *
     * @return a new Try with synchronous method calls
     */
    static <U> Try<U> of(ThrowableSupplier<U> supplier) {
        try {
            return new TrySuccess<>(supplier.get());
        } catch(Throwable throwable) {
            return new TryFailure<>(throwable);
        }
    }

    <U> Try<U> map(ThrowableFunction<? super T, ? extends U> mapper);
    <U> Try<U> flatMap(ThrowableFunction<? super T, ? extends Try<U>> mapper);
    Try<T> mayRescue(ThrowableFunction<Throwable, T> rescue);

    Optional<T> get();
    T getOrElse(T instead);
    T getOrElse(Supplier<T> lazy);
    <X extends Throwable> T getOrThrow(X exception) throws X;
    <X extends Throwable> T getOrThrow() throws X;

    boolean isFailure();
    boolean isSuccess();

    void onFailure(Consumer<Throwable> consumer);
    void onSuccess(Consumer<T> consumer);

    final class TrySuccess<T> implements Try<T> {
        private final T result;

        private TrySuccess(T result) {
            this.result = result;
        }

        @Override
        public <U> Try<U> map(ThrowableFunction<? super T, ? extends U> mapper) {
            return Try.of(() -> mapper.apply(result));
        }

        @Override
        public <U> Try<U> flatMap(ThrowableFunction<? super T, ? extends Try<U>> mapper) {
            return Try.of(() -> mapper.apply(result).getOrThrow());
        }

        @Override
        public Try<T> mayRescue(ThrowableFunction<Throwable, T> rescue) {
            return this;
        }

        @Override
        public Optional<T> get() {
            return Optional.ofNullable(result);
        }

        @Override
        public T getOrElse(T instead) {
            return result;
        }

        @Override
        public T getOrElse(Supplier<T> lazy) {
            return result;
        }

        @Override
        public <THROW extends Throwable> T getOrThrow(THROW exception) throws THROW {
            return result;
        }

        @Override
        public T getOrThrow() {
            return result;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public void onFailure(Consumer<Throwable> consumer) {}

        @Override
        public void onSuccess(Consumer<T> consumer) {
            consumer.accept(result);
        }
    }

    final class TryFailure<T> implements Try<T> {
        private final RuntimeException exception;

        private TryFailure(Throwable throwable) {
            this.exception = new RuntimeException(throwable);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> map(ThrowableFunction<? super T, ? extends U> mapper) {
            return (Try<U>) this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> flatMap(ThrowableFunction<? super T, ? extends Try<U>> mapper) {
            return (Try<U>) this;
        }

        @Override
        public Try<T> mayRescue(ThrowableFunction<Throwable, T> rescue) {
            return Try.of(() -> rescue.apply(exception));
        }

        @Override
        public Optional<T> get() {
            return Optional.empty();
        }

        @Override
        public T getOrElse(T instead) {
            return instead;
        }

        @Override
        public T getOrElse(Supplier<T> lazy) {
            return lazy.get();
        }

        @Override
        public <X extends Throwable> T getOrThrow(X exception) throws X {
            throw exception;
        }

        @Override
        public T getOrThrow() throws RuntimeException {
            throw exception;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public void onFailure(Consumer<Throwable> consumer) {
            consumer.accept(exception);
        }

        @Override
        public void onSuccess(Consumer<T> consumer) {}
    }
}