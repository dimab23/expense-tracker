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

package com.expense.tracker.service.expense;

import com.expense.tracker.exception.UnauthorizedException;
import com.expense.tracker.model.expense.ExpenseDTO;
import com.expense.tracker.model.expense.ExpenseResult;
import com.expense.tracker.model.tables.pojos.Currency;
import com.expense.tracker.model.tables.pojos.Expense;
import com.expense.tracker.model.tables.pojos.User;
import com.expense.tracker.repository.expense.ExpenseRepository;
import com.expense.tracker.service.currency.CurrencyService;
import com.expense.tracker.service.expense.observer.ExpenseObserver;
import com.expense.tracker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final UserService userService;
    private final ExpenseObserver expenseObserver;
    private final CurrencyService currencyService;
    private final ExpenseRepository expenseRepository;

    @Override
    @Transactional
    public ExpenseResult create(ExpenseDTO expenseDTO, String token) {
        User user = userService.findByToken(token);
        Currency currency = currencyService.findByName(expenseDTO.getCurrency());
        expenseObserver.update(expenseDTO);
        Expense expense = expenseRepository.insert(expenseDTO.toExpense(currency, user));

        return ExpenseResult.toExpenseResult(expense, currency);
    }

    @Override
    @Transactional
    public ExpenseResult update(ExpenseDTO expenseDTO, String token, long id) {
        Expense expense = findById(id);
        validatePermission(token, expense);
        expenseObserver.update(expenseDTO);
        Currency currency = currencyService.findByName(expenseDTO.getCurrency());
        Expense updated = expenseRepository.update(expenseDTO.toExpense(expense, currency));

        return ExpenseResult.toExpenseResult(updated, currency);
    }

    @Override
    public Expense findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    @Transactional
    public ExpenseResult delete(String token, long id) {
        Expense expense = findById(id);
        validatePermission(token, expense);
        expenseRepository.deleteById(id);
        Currency currency = currencyService.findById(expense.getCurrencyId());

        return ExpenseResult.toExpenseResult(expense, currency);
    }

    @Override
    public boolean validatePermission(String token, Expense expense) {
        User user = userService.findById(expense.getUserId());
        if (user.getToken().equals(token)) return true;
        throw new UnauthorizedException();
    }
}