package com.mobile.apppartner.ApiOffice365;

interface OperationCallback<T> {
    /**
     * The method to call in case of success.
     * @param result The result of the operation.
     */
    void onSuccess(T result);

    /**
     * The method to call in case of failure.
     * @param e The exception or reason of failure.
     */
    void onError(Exception e);
}
