package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class Tester {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        // write your code here
        start(SuperTests.class);
    }


    public static void start(Class testClass) throws InvocationTargetException, IllegalAccessException {
        Method beforeMethod = null;
        Method afterMethod = null;
        ArrayList<Method> testMethods = new ArrayList<>();

        Object obj = null;

        try {
            obj = testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Method m : testClass.getDeclaredMethods()
        ) {
            if (m.isAnnotationPresent(Test.class)) {
                testMethods.add(m);
            } else if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod == null) {
                    beforeMethod = m;
                } else {
                    throw new RuntimeException("Не должно быть более одного метода с аннотацией @BeforeSuite");
                }
            } else if (m.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) {
                    afterMethod = m;
                } else {
                    throw new RuntimeException("Не должно быть более одного метода с аннотацией @AfterSuite");
                }
            }
        }
        if (beforeMethod != null) {
            beforeMethod.invoke(obj);
        }
        testMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));

        for (Method testMethod : testMethods) {
            testMethod.invoke(obj);
        }

        if (afterMethod != null) {
            afterMethod.invoke(obj);
        }


    }

}
