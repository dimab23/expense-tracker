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

package com.expense.tracker.service.user;

import com.expense.tracker.exception.BadRequestException;
import com.expense.tracker.exception.UnauthorizedException;
import com.expense.tracker.model.ApiKey;
import com.expense.tracker.model.tables.pojos.User;
import com.expense.tracker.model.user.UserDTO;
import com.expense.tracker.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 27.05.2023
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public ApiKey auth(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) throw new UnauthorizedException();
        if (userDTO.isCorrectPassword(user)) return ApiKey.builder().apiKey(user.getToken()).build();
        throw new UnauthorizedException();
    }

    @Override
    @Transactional
    public ApiKey create(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new BadRequestException("This username is already taken");
        }
        user = userRepository.insert(userDTO.toUser());

        return ApiKey.builder().apiKey(user.getToken()).build();
    }

    @Override
    public User findByToken(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) throw new UnauthorizedException();

        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
