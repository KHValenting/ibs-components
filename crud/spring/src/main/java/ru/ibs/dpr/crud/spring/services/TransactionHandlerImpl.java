package ru.ibs.dpr.crud.spring.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class TransactionHandlerImpl implements TransactionHandler {

    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T runInTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}
