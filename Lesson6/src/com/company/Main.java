package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] arr = new int[]{1, 0, 0, 4, 1};
        Main main = new Main();
        // System.out.println(Arrays.toString(main.outpurAfter4(arr)));
        System.out.println(main.have1And4(arr));
    }

    public int[] outpurAfter4(int[] arr) {
        int[] resultarr;
        if (arr.length > 0) {
            int memory = -1;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 4) memory = i;
            }
            if (memory == -1) throw new RuntimeException();
            if (memory == arr.length - 1) {
                resultarr = new int[1];
            } else {
                resultarr = new int[arr.length - (memory + 1)];
            }
            if (arr.length - memory + 1 >= 0) System.arraycopy(arr, memory + 1, resultarr, 0, resultarr.length);
            return resultarr;
        }
        return null;
    }

    public boolean have1And4(int[] arr) {
        int count = 0;
        int[] cont = new int[]{1, 4};

        for (int a : cont)
            for (int b : arr)
                if (a == b) {
                    count++;
                    break;
                }
        return count == cont.length;
    }
}
