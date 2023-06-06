package ru.daniyar.idrisov.testservice.exceptions.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionConstants {

    public static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account not found";
    public static final String ACCOUNT_WITH_THIS_EMAIL_NOT_FOUND_MESSAGE = "Account with this email [%s] is not found";
    public static final String ACCOUNT_WITH_THIS_EMAIL_EXISTS_MESSAGE = "Account with this email [%s] already exists";
    public static final String INVALID_LOGIN_OR_PASSWORD_MESSAGE = "Invalid login or password";
    public static final String INVALID_REFRESH_TOKEN_MESSAGE = "Invalid refresh token";
    public static final String EMPTY_FIELD_MESSAGE = "Field [%s] is empty";
    public static final String PRODUCER_NOT_FOUNT_MESSAGE = "Producer not found";
    public static final String PRODUCT_WITH_THIS_SERIAL_NUMBER_AND_TYPE_ALREADY_EXISTS_MESSAGE
            = "Product with this serial number [%s] and type [%s] already exists";
    public static final String PRODUCT_NOT_FOUNT_MESSAGE = "Product not found";
    public static final String INCORRECT_PRODUCT_TYPE_MESSAGE = "Incorrect product type [%s]";

}
