package com.company.tests;

import com.company.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestsForMethodHave1And4 {
    private int[] in;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{4, 1, 2}},
                {new int[]{1, 2}},
                {new int[]{1}},
                {null}
        });
    }

    private Main main;

    @Before
    public void init() {
        main = new Main();
    }


    public TestsForMethodHave1And4 (int[] in) {
        this.in = in;
    }
    @Test
    public void isTrue(){
        Assert.assertTrue(main.have1And4(in));
    }
    @Test
    public void isFalse(){
        Assert.assertFalse(main.have1And4(in));
    }
}
