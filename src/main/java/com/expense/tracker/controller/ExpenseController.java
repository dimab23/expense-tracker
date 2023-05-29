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

package com.expense.tracker.controller;

import com.expense.tracker.model.Pagination;
import com.expense.tracker.model.expense.ExpenseDTO;
import com.expense.tracker.model.expense.ExpenseResult;
import com.expense.tracker.service.expense.ExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Validated
@RestController
@Tag(name = "Expenses")
@RequiredArgsConstructor
@RequestMapping("expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResult add(@Valid @RequestBody ExpenseDTO expenseDTO,
                             @RequestHeader(value = "api_key") String apiKey) {
        return expenseService.create(expenseDTO, apiKey);
    }

    @PutMapping("{expense_id}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseResult update(@PathVariable("expense_id") long id,
                                @Valid @RequestBody ExpenseDTO expenseDTO,
                                @RequestHeader(value = "api_key") String apiKey) {
        return expenseService.update(expenseDTO, apiKey, id);
    }

    @DeleteMapping("{expense_id}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseResult delete(@PathVariable("expense_id") long id,
                                @RequestHeader(value = "api_key") String apiKey) {
        return expenseService.delete(apiKey, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Pagination findAll(@RequestHeader(value = "api_key") String apiKey,
                              @RequestParam("page") @Min(0) int page,
                              @RequestParam("page_size") @Min(1) @Max(50) int pageSize) {
        return expenseService.findAll(page, pageSize, apiKey);
    }
}