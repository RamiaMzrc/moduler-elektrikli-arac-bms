package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DecisionEngineTest {

    @Test
    public void testNormalState() {
        // Prepare normal battery telemetry
        BatteryData data = new BatteryData(75.0, 370.0, 15.0, 25.0, "", "", false);

        // Process through engine
        BatteryData result = DecisionEngine.process(data);

        // Verify state is NORMAL
        assertEquals("NORMAL", result.getSystemStatus());
        assertFalse(result.isSecurityLock());
        assertEquals(15.0, result.getCurrent(), 0.001);
    }

    @Test
    public void testWarningStateHighTemp() {
        // Temperature of 55°C is in the warning band (50°C to 60°C)
        BatteryData data = new BatteryData(75.0, 370.0, 15.0, 55.0, "", "", false);

        BatteryData result = DecisionEngine.process(data);

        assertEquals("WARNING", result.getSystemStatus());
        assertFalse(result.isSecurityLock());
    }

    @Test
    public void testFaultStateExtremeTemp() {
        // Direct thermal runaway condition (> 60°C)
        BatteryData data = new BatteryData(75.0, 370.0, 15.0, 62.0, "", "", false);

        BatteryData result = DecisionEngine.process(data);

        assertEquals("FAULT", result.getSystemStatus());
        assertTrue(result.isSecurityLock());
        // Current must be set to 0.0 in FAULT
        assertEquals(0.0, result.getCurrent(), 0.001);
    }

    @Test
    public void testFaultStateLowSoc() {
        // State of Charge under 10%
        BatteryData data = new BatteryData(8.0, 360.0, 10.0, 25.0, "", "", false);

        BatteryData result = DecisionEngine.process(data);

        assertEquals("FAULT", result.getSystemStatus());
        assertTrue(result.isSecurityLock());
        assertEquals(0.0, result.getCurrent(), 0.001);
    }
}
