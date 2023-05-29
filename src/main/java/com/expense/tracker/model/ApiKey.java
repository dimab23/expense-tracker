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

package com.expense.tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dimab
 * @author vixeven
 * @version expense-tracker
 * @apiNote 28.05.2023
 */
public record ApiKey(@JsonProperty("api_key") String apiKey) {
    public ApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String apiKey() {
        return apiKey;
    }

    public static ApiKeyBuilder builder() {
        return new ApiKeyBuilder();
    }

    public static class ApiKeyBuilder {
        private String apiKey;

        ApiKeyBuilder() {
        }

        @JsonProperty("api_key")
        public ApiKeyBuilder apiKey(final String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public ApiKey build() {
            return new ApiKey(this.apiKey);
        }

        public String toString() {
            return "ApiKey.ApiKeyBuilder(apiKey=" + this.apiKey + ")";
        }
    }
}