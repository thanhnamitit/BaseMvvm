package com.vti.base.data.param.response.wrapper;

import androidx.annotation.Nullable;
import com.vti.base.data.Constant;
import retrofit2.HttpException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class Result<T> {

    private T data;
    private Failure failure;

    //For test
    private int status = -1;
    private String message;

    public Result() {
    }

    public Result(@Nullable T data, @Nullable Failure failure) {
        this.data = data;
        this.failure = failure;
    }

    public Result(int status, @Nullable T data, @Nullable Failure failure) {
        this.data = data;
        this.failure = failure;
        this.status = status;
    }

    public Result(int status, @Nullable T data, @Nullable Failure failure, String message) {
        this.data = data;
        this.failure = failure;
        this.status = status;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Failure getFailure() {
        return failure;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public void setFailure(Failure failure) {
        this.failure = failure;
    }

    public static <T> Result<T> fromData(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> fromData(int status, T data) {
        return new Result<>(status, data, null);
    }

    public static <T> Result<T> fromData(int status, T data, String message) {
        return new Result<>(status, data, null, message);
    }

    public static <T> Result<T> fromError(Throwable error) {
        return new Result<>(null, getFailureReason(error));
    }

    public static <T> Result<T> fromFailure(Failure failure) {
        return new Result<>(null, failure);
    }

    public static <T> Result<T> loading() {
        return new Result<>();
    }

    public boolean isError() {
        return failure != null && !(failure instanceof Failure.Empty);
    }

    public boolean isSuccess() {
        return data != null || Constant.ApiResponseCode.SUCCESS == status;
    }

    public boolean isEmpty() {
        return failure instanceof Failure.Empty;
    }

    public boolean isLoading() {
        return (failure == null && data == null) && Constant.ApiResponseCode.SUCCESS != status;
    }

    //TODO: Handle more case for request error
    private static Failure getFailureReason(Throwable error) {
        if (error == null) return null;
        if (error instanceof SocketTimeoutException || error instanceof UnknownHostException) {
            return new Failure.NetworkConnection();
        } else if (error instanceof HttpException
                && ((HttpException) error).code() == Constant.ResponseCode.SESSION_TIMEOUT) {
            return new Failure.SessionTimeout();
        }
        return new Failure.ServerError();
    }

}
