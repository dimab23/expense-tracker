/*
    MIT License
    
    Copyright (c) 2023 Beșelea Dumitru & Șaptefrați Victor

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

package com.expense.tracker.repository.exchange;


import com.expense.tracker.model.Tables;
import com.expense.tracker.model.tables.pojos.Exchange;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class ExchangeRepositoryImpl implements ExchangeRepository {
    private final DSLContext dslContext;

    @Override
    public List<Exchange> insertAll(List<Exchange> exchanges) {
        List<Query> queries = new ArrayList<>();
        for (Exchange exchange : exchanges) {
            Query query = dslContext.insertInto(Tables.EXCHANGE, Tables.EXCHANGE.CURRENCY_ID, Tables.EXCHANGE.VALUE,
                    Tables.EXCHANGE.EXCHANGE_DATE
            ).values(exchange.getCurrencyId(), exchange.getValue(), exchange.getExchangeDate());
            queries.add(query);
        }

        dslContext.batch(queries).execute();
        return exchanges;
    }

    @Override
    public boolean existsByExchangeDate(LocalDate exchangeDate) {
        Record record = dslContext.select(Tables.EXCHANGE)
                .from(Tables.EXCHANGE)
                .where(Tables.EXCHANGE.EXCHANGE_DATE.equal(exchangeDate))
                .limit(1)
                .fetchOne();
        return record != null;
    }

    @Override
    public List<Exchange> findByExchangeDateIn(Set<LocalDate> dates) {
        return dslContext.select(Tables.EXCHANGE)
                .from(Tables.EXCHANGE)
                .where(Tables.EXCHANGE.EXCHANGE_DATE.in(dates))
                .fetch()
                .into(Exchange.class);
    }
}