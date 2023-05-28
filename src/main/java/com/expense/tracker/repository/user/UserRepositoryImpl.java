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

package com.expense.tracker.repository.user;

import com.expense.tracker.model.Tables;
import com.expense.tracker.model.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

/**
 * @author dimab
 * @version expense-tracker
 * @apiNote 27.05.2023
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext context;

    @Override
    public User insert(User user) {
        Record record = context.insertInto(Tables.USER, Tables.USER.USERNAME, Tables.USER.PASSWORD, Tables.USER.TOKEN)
                .values(user.getUsername(), user.getPassword(), user.getToken())
                .returning()
                .fetchOne();
        if (record == null) {
            throw new IllegalStateException("The user was not created, something went wrong");
        }

        return record.into(User.class);
    }

    @Override
    public User findByToken(String token) {
        Record record = context.select()
                .from(Tables.USER)
                .where(Tables.USER.TOKEN.equal(token))
                .fetchOne();
        if (record == null) return null;

        return record.into(User.class);
    }

    @Override
    public User findByUsername(String username) {
        Record record = context.select()
                .from(Tables.USER)
                .where(Tables.USER.USERNAME.equal(username))
                .fetchOne();
        if (record == null) return null;

        return record.into(User.class);
    }
}