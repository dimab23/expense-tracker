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

package com.expense.tracker.service.exchange.adapter;

import com.expense.tracker.client.ExchangeProxy;
import com.expense.tracker.model.CommandInvoker;
import com.expense.tracker.model.client.ExchangeHistory;
import com.expense.tracker.model.tables.pojos.Currency;
import com.expense.tracker.model.tables.pojos.Exchange;
import com.expense.tracker.service.currency.CurrencyService;
import com.expense.tracker.service.exchange.ExchangeService;
import com.expense.tracker.service.exchange.command.AddExchangeCommand;
import com.expense.tracker.service.exchange.command.Command;
import com.expense.tracker.service.iterator.ExchangeHistoryIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Service
@RequiredArgsConstructor
public class ExchangeProxyAdapter implements ExchangeAdapter {
    private final ExchangeProxy exchangeProxy;
    private final ExchangeService exchangeService;
    private final CurrencyService currencyService;

    @Async
    @Override
    public void refresh() {
        ExchangeHistoryIterator historyIterator = new ExchangeHistoryIterator(exchangeService.getDates());
        while (historyIterator.hasNext()) {
            LocalDate date = historyIterator.next();
            if (Boolean.FALSE.equals(exchangeService.existsByExchangeDate(date))) {
                ExchangeHistory exchangeHistory = exchangeProxy.history(date);
                Command command = new AddExchangeCommand(mapper(exchangeHistory, date), exchangeService);
                new CommandInvoker(command).command().execute();
            }
            exchangeService.detach(date);
        }
    }

    private List<Exchange> mapper(ExchangeHistory exchangeHistory, LocalDate date) {
        Map<String, Long> currency = currencyService.findNameIn(exchangeHistory.getRates().keySet()).stream()
                .collect(Collectors.toMap(Currency::getName, Currency::getId));
        List<Exchange> exchanges = new ArrayList<>();
        for (Map.Entry<String, Double> rate : exchangeHistory.getRates().entrySet()) {
            Long currencyId = currency.get(rate.getKey());
            Exchange exchange = new Exchange();
            exchange.setCurrencyId(currencyId);
            exchange.setValue(rate.getValue());
            exchange.setExchangeDate(date);
            exchanges.add(exchange);
        }

        return exchanges;
    }
}