package com.meraphorce.utils;

import com.meraphorce.models.User;

import java.util.Objects;

public class ValidatorRequired {

    public static void validateUser(Object user) {
        // TODO implement a best validation

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("[ User is required ] ");
        }
    }
}
