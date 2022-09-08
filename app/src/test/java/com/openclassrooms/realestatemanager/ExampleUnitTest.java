package com.openclassrooms.realestatemanager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void convertEuroToDollar(){
        long dollar = Utils.convertEuroToDollar(100);
        assertEquals(125, dollar);
    }

    @Test
    public void getTodayDateCorrectly(){
        String todayDate = Utils.getTodayDate();
        assertEquals("08/09/2022", todayDate);
    }

}