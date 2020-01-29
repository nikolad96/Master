package com.master.demo;

public class Test {
    private Long id;
    private String x;

    public Test(Long id, String x) {
        this.id = id;
        this.x = x;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public Test() {
    }
}
