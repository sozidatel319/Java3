package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main<T extends Object> {
    public static void main(String[] args) {
        // write your code here
        Fruit apple = new Fruit("apple", 1);
        Fruit orange = new Fruit("orange", 2);
        Box b = new Box(apple);
        Box c = new Box(orange);
        Box d = new Box(apple);
        b.addFruitToBox(new Fruit("apple", 1));
        b.pour(d);
        System.out.println(b.getWeiht());
        System.out.println(d.getWeiht());
    }

    //Задание №1
    public void change(T[] arr, int number1, int number2) {
        T memory;
        memory = arr[number1];
        arr[number1] = arr[number2];
        arr[number2] = memory;
    }

    //Задание №2
    public ArrayList<T> arrayToList(T[] arr) {
        return new ArrayList<T>(Arrays.asList(arr));
    }

}
