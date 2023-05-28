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

package com.expense.tracker.model.user;

import com.expense.tracker.model.tables.pojos.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 27.05.2023
 */
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(getUsername());
        user.setPassword(getEncryptedPassword());
        user.setToken(generateToken());

        return user;
    }

    public boolean isCorrectPassword(User user) {
        return user.getPassword().equals(getEncryptedPassword());
    }

    private String generateToken() {
        return DigestUtils.sha256Hex(String.format("%s-%s", getUsername(), getEncryptedPassword()));
    }

    private String getEncryptedPassword() {
        return DigestUtils.sha256Hex(getPassword());
    }
}