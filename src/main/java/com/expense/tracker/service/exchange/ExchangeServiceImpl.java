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

package com.expense.tracker.service.exchange;

import com.expense.tracker.client.ExchangeProxy;
import com.expense.tracker.model.CommandInvoker;
import com.expense.tracker.model.client.ExchangeHistory;
import com.expense.tracker.repository.exchange.ExchangeRepository;
import com.expense.tracker.service.exchange.command.AddExchangeCommand;
import com.expense.tracker.service.exchange.command.Command;
import com.expense.tracker.service.iterator.ExchangeHistoryIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final Set<LocalDate> dates = new HashSet<>();

    private final ExchangeRepository exchangeRepository;
    private final ExchangeProxy exchangeProxy;

    @Override
    @Scheduled(fixedDelayString = "PT1M")
    public void refresh() {
        ExchangeHistoryIterator historyIterator = new ExchangeHistoryIterator(dates);
        while (historyIterator.hasNext()) {
            LocalDate date = historyIterator.next();
            if (Boolean.FALSE.equals(findByExchangeDate(date))) {
                ExchangeHistory exchangeHistory = exchangeProxy.history(date);
                Command command = new AddExchangeCommand(new ArrayList<>(), exchangeRepository);
                new CommandInvoker(command).command().execute();
            }
            detach(date);
        }
    }

    @Override
    public void detach(LocalDate date) {
        dates.remove(date);
    }

    @Override
    public void attach(LocalDate date) {
        dates.add(date);
    }

    @Override
    public boolean findByExchangeDate(LocalDate localDate) {
        return exchangeRepository.existsByExchangeDate(localDate);
    }
}