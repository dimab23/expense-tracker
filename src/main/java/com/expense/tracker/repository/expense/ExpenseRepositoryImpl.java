/*
    MIT License
    
    Copyright (c) 2023 Beșelea Dumitru

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
 */

package com.expense.tracker.repository.expense;

import com.expense.tracker.exception.NotFoundException;
import com.expense.tracker.model.Tables;
import com.expense.tracker.model.tables.pojos.Expense;
import com.expense.tracker.model.tables.records.ExpenseRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final DSLContext dslContext;

    @Override
    public Expense findById(Long id) {
        Record record = dslContext.select(Tables.EXPENSE)
                .from(Tables.EXPENSE)
                .where(Tables.EXPENSE.ID.equal(id))
                .fetchOne();
        if (record == null) {
            throw new NotFoundException(String.format("The expense with id %s was not found", id));
        }

        return record.into(Expense.class);
    }

    @Override
    public Expense insert(Expense expense) {
        ExpenseRecord record = dslContext.insertInto(
                        Tables.EXPENSE, Tables.EXPENSE.PAYMENT,
                        Tables.EXPENSE.PAYMENT_DATE,
                        Tables.EXPENSE.CURRENCY_ID,
                        Tables.EXPENSE.USER_ID
                ).values(expense.getPayment(), expense.getPaymentDate(), expense.getCurrencyId(), expense.getUserId())
                .returning()
                .fetchOne();
        if (record == null) return null;

        return record.into(Expense.class);
    }

    @Override
    public Expense update(Expense expense) {
        Record record = dslContext.update(Tables.EXPENSE)
                .set(Tables.EXPENSE.CURRENCY_ID, expense.getCurrencyId())
                .set(Tables.EXPENSE.PAYMENT, expense.getPayment())
                .set(Tables.EXPENSE.PAYMENT_DATE, expense.getPaymentDate())
                .where(Tables.EXPENSE.ID.equal(expense.getId()))
                .returning()
                .fetchOne();
        if (record == null) {
            throw new IllegalStateException("Something went wrong");
        }
        return record.into(Expense.class);
    }
}