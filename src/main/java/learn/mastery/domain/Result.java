package learn.mastery.domain;

//    -- inherits from Response
public class Result<T> extends Response {
    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
