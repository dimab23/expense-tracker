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

package com.expense.tracker.repository.currency;

import com.expense.tracker.exception.BadRequestException;
import com.expense.tracker.exception.NotFoundException;
import com.expense.tracker.model.Tables;
import com.expense.tracker.model.tables.pojos.Currency;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {
    private final DSLContext dslContext;

    @Override
    public Currency findById(Long id) {
        Record record = dslContext.select(Tables.CURRENCY)
                .from(Tables.CURRENCY)
                .where(Tables.CURRENCY.ID.equal(id))
                .fetchOne();
        if (record == null) {
            throw new NotFoundException(String.format("Not found currency with id %s", id));
        }

        return record.into(Currency.class);
    }

    @Override
    public Currency findByName(String name) {
        Record record = dslContext.select(Tables.CURRENCY)
                .from(Tables.CURRENCY)
                .where(Tables.CURRENCY.NAME.equal(name))
                .fetchOne();
        if (record == null) {
            throw new BadRequestException("This currency is not supported");
        }

        return record.into(Currency.class);
    }

    @Override
    public List<Currency> findByIdIn(Set<Long> ids) {
        return dslContext.select(Tables.CURRENCY)
                .from(Tables.CURRENCY)
                .where(Tables.CURRENCY.ID.in(ids))
                .fetch()
                .into(Currency.class);
    }

    @Override
    public List<Currency> findNameIn(Set<String> names) {
        return dslContext.select(Tables.CURRENCY)
                .from(Tables.CURRENCY)
                .where(Tables.CURRENCY.NAME.in(names))
                .fetch()
                .into(Currency.class);
    }
}