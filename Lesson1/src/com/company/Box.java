package com.company;

import java.util.ArrayList;

public class Box {
    private ArrayList<Fruit> container;
    private int weight;

    public Box(Fruit fruit) {
        container = new ArrayList<>();
        container.add(fruit);
        weight += fruit.getWeight();
    }

    public void addFruitToBox(Fruit fruit) {
        if (container.get(0).getType().equals(fruit.getType())) {
            container.add(fruit);
            weight += fruit.getWeight();
        }
    }

    public int getWeiht() {
        int sum = 0;
        for (Fruit fruit : container) {
            sum += fruit.getWeight();
        }
        return sum;
    }

    public boolean compare(Box box) {
        return this.weight == box.weight;
    }

    public void pour(Box box) {
        if (this.container.get(0).getType().equals(box.container.get(0).getType())) {
            box.container.addAll(container);
            container.clear();
            weight = 0;
        }
    }
}
