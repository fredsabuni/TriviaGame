package com.trivia.FredySabuni.constants;

public enum RespCode {
    //Auth Codes and tokens
    OK(2000,200),  // No credentials or Invalid credentials.

    //Auth Codes and tokens
    UNAUTHENTICATED(4001,401),  // No credentials or Invalid credentials.
    EXCEPTION_ON_AUTHENTICATION(4002,401),
    FORBIDDEN(4003,403),  // Valid credentials but not enough privileges to perform an action on a resource

    USER_DOES_NOT_EXIST(4004,401),
    USER_BLOCKED_TEMP(4005,401),
    USER_BLOCKED_PERMANENTLY(4005,401),

    WRONG_PASSWORD(4011,401),
    INVALID_PASSWORD(4012,401),
    INVALID_TOKEN(4013,401),
    INVALID_CREDENTIALS(4014,401),
    QUESTION_ALREADY_EXISTS_ON_REGISTRATION(4015,400),
    ILLEGAL_ACCESS(4016,200),

    //User Inputs
    INVALID_INPUT_VALUE(4000,400),

    //Server
    UNKNOWN_ERROR(5000,500),
    INVALID_SERVER_CONFIGURATION(5001,500),

    //Database Operations
    DATABASE_EXCEPTION(8000,500),
    DATABASE_READ_EXCEPTION(8001,500),
    DATABASE_WRITE_EXCEPTION(8002,500),
    RECORD_DOES_NOT_EXIST(8004,400),
    DUPLICATION_REFERENCE(8005,400);

    public final int code;
    public final int httpCode;

    RespCode(int code, int httpCode) {
        this.code = code;
        this.httpCode = httpCode;
    }

    public int getCode() {
        return this.code;
    }

    public int getHttpCode() {
        return this.httpCode;
    }
}
