package com.trivia.FredySabuni.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.trivia.FredySabuni.constants.RespCode;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonPropertyOrder({"RespStatus", "respCode", "message", "respBody"})
public class ApiResponse<T> {

    @NotNull
    @NotEmpty
    private int respCode;

    @NotEmpty
    private T respBody = null;

    private String message = "";


    @JsonIgnore
    public static <T> ApiResponse<T> success(String message) {
        return success(null, message);
    }

    public static <T> ApiResponse<T> success(T responseBody, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setRespCode(RespCode.OK.getCode());
        apiResponse.setRespBody(responseBody);
        apiResponse.setMessage(message);
        return apiResponse;
    }


    @JsonIgnore
    public static <T> ApiResponse<T> failure(String message) {
        return failure(null, message);
    }

    @JsonIgnore
    public static <T> ApiResponse<T> failure(T responseBody, String message) {

        ApiResponse<T> ApiResponse = new ApiResponse<>();
        ApiResponse.setRespCode(RespCode.QUESTION_ALREADY_EXISTS_ON_REGISTRATION.getCode());
        ApiResponse.setRespBody(responseBody);
        ApiResponse.setMessage(message);
        return ApiResponse;
    }


    @JsonIgnore
    public static <T> ApiResponse<T> failure(String message, RespCode errorCode) {
        ApiResponse<T> ApiResponse = new ApiResponse<>();
        ApiResponse.setRespCode(errorCode.getCode());
        ApiResponse.setMessage(message);
        return ApiResponse;
    }


    @JsonIgnore
    public static ResponseEntity<ApiResponse<?>> ok(Object responseBody, String message) {
        return new ResponseEntity<>(ApiResponse.success(responseBody, message), HttpStatus.OK);
    }

    @JsonIgnore
    public static ResponseEntity<ApiResponse<?>> httpOk(Object responseBody, String message) {
        return new ResponseEntity<>(ApiResponse.success(responseBody, message), HttpStatus.OK);
    }

    @JsonIgnore
    public static ResponseEntity<?> httpOk(String message) {
        return new ResponseEntity<>(ApiResponse.success(message), HttpStatus.OK);
    }


    @JsonIgnore
    public static ResponseEntity<?> failed(String message, RespCode respCode) {
        return new ResponseEntity<>(ApiResponse.failure(message, respCode), null, respCode.getHttpCode());
    }


    @JsonIgnore
    public boolean hasFailed() {
        return !(isSuccess());
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.getRespCode() == RespCode.OK.getCode();
    }

    @NotNull
    @NotEmpty
    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(@NotNull @NotEmpty int respCode) {
        this.respCode = respCode;
    }

    @NotEmpty
    public T getRespBody() {
        return respBody;
    }

    public void setRespBody(@NotEmpty T respBody) {
        this.respBody = respBody;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
