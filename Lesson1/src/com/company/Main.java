package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main<T extends Object,M extends Object> {
    private T object[];
    private M object2[];
    public static void main(String[] args) {
	// write your code here
        Fruit apple = new Fruit("apple",1);
        Fruit orange = new Fruit("orange",2);
        Box b = new Box(apple);
        Box c = new Box(orange);
        Box d = new Box(apple);
        b.addFruitToBox(new Fruit("apple",1));
        b.pour(d);
    }

//Задание №1
    public void change(T []arr1, M []arr2){
        Object memory;
        for (int i = 0; i < arr2.length; i++) {
            memory = arr1[i];
            arr1[i] = (T) arr2[i];
            arr2[i] = (M) memory;
        }
    }

    //Задание №2
    public ArrayList<T> arrayToList(T [] arr){
        return new ArrayList<T>(Arrays.asList(arr));
    }

}
