package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {



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
    public void testGetTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String expectedDate = dateFormat.format(new Date());
        String actualDate = Utils.getTodayDate();
        assertEquals(expectedDate, actualDate);
    }

}