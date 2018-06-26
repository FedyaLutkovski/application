package by.test.application.dao;

public class ResponseSum extends Response {
    private Integer sum;

    public ResponseSum(Integer code, String description, Integer sum) {
        super(code, description);
        this.sum = sum;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
