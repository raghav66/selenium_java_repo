package com.rb.framework;

public class GlobalParams {

    private static final ThreadLocal<String> browserName = new ThreadLocal<>();
    private static final ThreadLocal<String> clientName = new ThreadLocal<>();
    private static final ThreadLocal<String> executionEnv = new ThreadLocal<>();
    private static final ThreadLocal<String> tagName = new ThreadLocal<>();


    public String getBrowserName() {
        return browserName.get();
    }
    public void setBrowserName(String browserName2) {
        browserName.set(browserName2);
    }
    public String getClientName() {
        return clientName.get();
    }
    public void setClientName(String clientName1) {
        clientName.set(clientName1);
    }

    public String getExecutionEnv() {
        return executionEnv.get();
    }
    public void setExecutionEnv(String executionEnv1) {
        executionEnv.set(executionEnv1);
    }

    public String getTagName() {
        return tagName.get();
    }

    public void setTagName(String executionTag) {
        tagName.set(executionTag);
    }

}
