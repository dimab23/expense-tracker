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

package com.expense.tracker.client.engine;

import com.expense.tracker.client.ExchangeClient;
import com.expense.tracker.model.client.ExchangeHistory;
import com.expense.tracker.service.currency.CurrencyService;
import com.expense.tracker.util.RestTemplateSingleton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
@Component
@RequiredArgsConstructor
public class FrankfurterAppClient implements ExchangeClient {
    private static final String API_URL = "https://api.frankfurter.app";

    private final CurrencyService currencyService;

    @Override
    public ExchangeHistory history(LocalDate localDate) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(API_URL)
                .path(localDate.toString())
                .queryParam("from", currencyService.defaultCurrency());
        return RestTemplateSingleton.getInstance().getForEntity(uriBuilder.toUriString(), ExchangeHistory.class).getBody();
    }
}