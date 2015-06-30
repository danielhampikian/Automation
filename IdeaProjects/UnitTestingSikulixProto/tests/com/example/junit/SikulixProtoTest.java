package com.example.junit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by danielhampikian on 6/18/15.
 */
public class SikulixProtoTest {
    private SikulixProto proto;

    @Before
    public void setUp() throws Exception {
        proto = new SikulixProto();
    }

    @Test
    public void testSayHello() throws Exception {
        assertEquals("Hello", proto.sayHello());
    }

    @Test
    public void testOpenChanalyzer() throws Exception {
        try {
            assertEquals(1, proto.openChanalyzer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}