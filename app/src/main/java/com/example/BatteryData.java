package com.example;

public class BatteryData {
    private double soc;
    private double voltage;
    private double current;
    private double temperature;
    private String systemStatus;
    private String warningMessage;
    private boolean securityLock;

    public BatteryData() {
    }

    public BatteryData(double soc, double voltage, double current, double temperature, 
                       String systemStatus, String warningMessage, boolean securityLock) {
        this.soc = soc;
        this.voltage = voltage;
        this.current = current;
        this.temperature = temperature;
        this.systemStatus = systemStatus;
        this.warningMessage = warningMessage;
        this.securityLock = securityLock;
    }

    public double getSoc() {
        return soc;
    }

    public void setSoc(double soc) {
        this.soc = soc;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public boolean isSecurityLock() {
        return securityLock;
    }

    public void setSecurityLock(boolean securityLock) {
        this.securityLock = securityLock;
    }
}
