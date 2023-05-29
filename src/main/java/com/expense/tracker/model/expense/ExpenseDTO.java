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

package com.expense.tracker.model.expense;

import com.expense.tracker.model.tables.pojos.Currency;
import com.expense.tracker.model.tables.pojos.Expense;
import com.expense.tracker.model.tables.pojos.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Getter
@Setter
public class ExpenseDTO {
    @DecimalMin(value = "0.0", inclusive = false)
    private double payment;
    @NotBlank
    private String currency;
    @NotNull
    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    public Expense toExpense(Currency currency, User user) {
        Expense expense = new Expense();
        expense.setCurrencyId(currency.getId());
        expense.setPayment(getPayment());
        expense.setPaymentDate(getPaymentDate());
        expense.setUserId(user.getId());

        return expense;
    }

    public Expense toExpense(Expense expense, Currency currency) {
        expense.setCurrencyId(currency.getId());
        expense.setPayment(getPayment());
        expense.setPaymentDate(getPaymentDate());

        return expense;
    }
}