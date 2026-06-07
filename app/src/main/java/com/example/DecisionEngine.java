package com.example;

public class DecisionEngine {

    public static BatteryData process(BatteryData data) {
        double temp = data.getTemperature();
        double volt = data.getVoltage();
        double soc = data.getSoc();

        // 1. FAULT check
        if (temp > 60.0 || temp < -10.0 || volt > 400.0 || volt < 300.0 || soc < 10.0) {
            data.setSystemStatus("FAULT");
            data.setSecurityLock(true);
            data.setCurrent(0.0); // Simülasyonda akımı 0.0 A'ya sabitle
            data.setWarningMessage("Kritik hata! Güvenlik kilidi aktif.");
        }
        // 2. WARNING check
        else if ((temp >= 50.0 && temp <= 60.0) || 
                 (temp >= 5.0 && temp <= 10.0) || 
                 (volt >= 300.0 && volt <= 350.0) || 
                 (soc >= 10.0 && soc < 20.0)) {
            data.setSystemStatus("WARNING");
            data.setSecurityLock(false);
            data.setWarningMessage("Değerler sınıra yaklaşıyor. Dikkatli olun.");
        }
        // 3. NORMAL check
        else {
            data.setSystemStatus("NORMAL");
            data.setSecurityLock(false);
            data.setWarningMessage("Sistem stabil. Tüm değerler güvenli aralıkta.");
        }

        return data;
    }
}
