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
public class TestsForMethodOutputAfter4 {
    private int[] in;
    private int[] out;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{4, 1, 2}, new int[]{1, 2}},
                {new int[]{1, 2}, new int[]{1, 2}},
                {null, new int[]{}}
        });
    }

    private Main main;

    @Before
    public void init() {
        main = new Main();
    }


    public TestsForMethodOutputAfter4(int[] in, int[] out) {
        this.in = in;
        this.out = out;
    }

    @Test
    public void massTestAdd() {
        Assert.assertArrayEquals(out, main.outpurAfter4(in));
    }

    @Test
    public void testObjectNull(){
        Assert.assertNull(in);
    }
    @Test
    public void testObjectNotNull(){
        Assert.assertNotNull(in);
    }
}


