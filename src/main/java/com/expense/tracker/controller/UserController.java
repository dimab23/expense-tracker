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

import com.expense.tracker.model.ApiKey;
import com.expense.tracker.model.user.UserDTO;
import com.expense.tracker.service.user.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 27.05.2023
 */
@RestController
@Tag(name = "Users")
@RequiredArgsConstructor
@RequestMapping("users")
@SecurityRequirement(name = "basicAuth")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiKey create(@Valid @RequestBody UserDTO userDTO,
                         @Parameter(hidden = true)
                         @RequestHeader(value = "Authorization", defaultValue = "") String token) {
        return userService.create(userDTO, token);
    }

    @PostMapping("auth")
    @ResponseStatus(HttpStatus.OK)
    public ApiKey auth(@Valid @RequestBody UserDTO userDTO,
                       @Parameter(hidden = true)
                       @RequestHeader(value = "Authorization", defaultValue = "") String token) {
        return userService.auth(userDTO, token);
    }
}