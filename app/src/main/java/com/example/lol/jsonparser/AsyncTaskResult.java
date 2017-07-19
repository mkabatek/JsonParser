package com.example.lol.jsonparser;

/**
 * Created by lol on 7/18/17.
 * AsyncTaskResult handler. Takes a generic
 * Stores the result or error during doInBackground
 * Results/errors be used when onPostExecute is called.
 */

public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    public AsyncTaskResult(T result) {
        this.result = result;
    }

    public AsyncTaskResult(Exception error) {
        this.error = error;
    }
}