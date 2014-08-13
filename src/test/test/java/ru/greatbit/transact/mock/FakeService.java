package ru.greatbit.transact.mock;

import org.springframework.stereotype.Service;
import ru.greatbit.transact.Transaction;
import ru.greatbit.transact.Transactionable;

/**
 * Created by azee on 13.08.14.
 */
@Transactionable(transactions = {
        @Transaction(methodName = "doSmt", parameter = 1, lockIdPath = ""),
        @Transaction(methodName = "doSmt1", parameter = 1, lockIdPath = ""),
        @Transaction(methodName = "doSmt2", parameter = 1, lockIdPath = "id")
})
@Service
public interface FakeService {
    public void doSmt(String valueToUpdate, String id);
    public void doSmt1(FakeObject valueToUpdate, String id);
    public void doSmt2(FakeObject valueToUpdate, FakeObject id);
}
