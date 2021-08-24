package ru.ibs.dpr.crud.spring.services;

import java.util.function.Supplier;

public interface TransactionHandler {

    <T> T runInTransaction(Supplier<T> supplier);
}
