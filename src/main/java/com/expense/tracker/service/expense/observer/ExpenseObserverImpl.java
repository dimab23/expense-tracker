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

package com.expense.tracker.service.expense.observer;

import com.expense.tracker.model.expense.ExpenseDTO;
import com.expense.tracker.service.currency.CurrencyService;
import com.expense.tracker.service.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Service
@RequiredArgsConstructor
public class ExpenseObserverImpl implements ExpenseObserver {
    private final ExchangeService exchangeService;
    private final CurrencyService currencyService;

    @Override
    public void update(ExpenseDTO expenseDTO) {
        if (expenseDTO.getCurrency().equals(currencyService.defaultCurrency())) return;
        exchangeService.attach(expenseDTO.getPaymentDate());
        exchangeService.refresh();
    }
}