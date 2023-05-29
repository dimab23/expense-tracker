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

package com.expense.tracker.model.expense;

import com.expense.tracker.model.tables.pojos.Currency;
import com.expense.tracker.model.tables.pojos.Expense;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Getter
@Setter
public class ExpenseResultData extends ExpenseResult {
    @JsonProperty("exchange_rate")
    private Double exchangeRate;

    public ExpenseResultData(ExpenseResult expenseResult, Double exchangeRate) {
        super(expenseResult.getId(), expenseResult.getCurrency(), expenseResult.getPayment(),
                expenseResult.getPaymentDate()
        );
        this.exchangeRate = exchangeRate;
    }

    public static ExpenseResultData toExpenseResultData(Expense expense, Double exchangeRate, Currency currency) {
        return new ExpenseResultData(ExpenseResult.toExpenseResult(expense, currency).clone(), exchangeRate);
    }
}