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

package com.expense.tracker.model.expense.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
public class CompositeCurrency implements CurrencyExchange {
    private String code;
    private final List<CurrencyExchange> subCurrencies;

    public CompositeCurrency() {
        this.subCurrencies = new ArrayList<>();
    }

    public void addCurrency(CurrencyExchange currency) {
        subCurrencies.add(currency);
    }

    public void removeCurrency(CurrencyExchange currency) {
        subCurrencies.remove(currency);
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public double exchangeRate() {
        double totalExchangeRate = 1.0;
        for (CurrencyExchange currency : subCurrencies) {
            totalExchangeRate *= currency.exchangeRate();
        }
        return totalExchangeRate;
    }
}