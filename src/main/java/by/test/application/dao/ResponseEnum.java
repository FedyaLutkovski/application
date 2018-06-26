package by.test.application.dao;

public enum ResponseEnum {
    CODE0(0,"OK"),
    CODE1(1,"Operation not performed"),
    CODE2(2,"An entry with this name exists"),
    CODE3(3,"Arguments are more than two"),
    CODE4(4,"One or more items are missing in the database");

    private final int code;
    private final String description;

    ResponseEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
