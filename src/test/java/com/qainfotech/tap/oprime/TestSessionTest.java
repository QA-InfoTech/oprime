package com.qainfotech.tap.oprime;

import java.util.HashMap;
import org.testng.annotations.*;

/**
 * @author prashant
 */
public class TestSessionTest {

    public TestSessionTest() {
    }

    @Test
    public void NewTestSessionwithConfigMap() {
        HashMap<Object, Object> config = new HashMap<>();
        TestSession session = new TestSession(config);
    }

    @Test
    public void NewTestSessionWithConfigFile() {

    }

    @Test
    public void NewTestSessionwithDefaults() {

    }

}
