package hkube.algo;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class APIExecutionFuture implements Future<Map> {
    Map result = null;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new RuntimeException("Method cancel in " + this.getClass() + " is not supported");
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public Map get() throws InterruptedException, ExecutionException {
        Map result = this.result;
        return result;
    }

    @Override
    public Map get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long startTime = System.nanoTime();
        timeout = unit.toNanos(timeout);
        while (!isDone() && System.nanoTime() < (startTime + timeout)) {
            Thread.sleep(100);
        }
        if(result == null){
            throw new TimeoutException();
        }
        return result;
    }

    public void setResult(Map result) {
            this.result = result;
    }
}
