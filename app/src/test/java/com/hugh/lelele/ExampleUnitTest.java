package com.hugh.lelele;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String TAG = "ExampleUnitTest";

    ArrayList<Integer> mFibonacciArray;
    @Test
    public void addition_isCorrect() {
        assertEquals(2, fibonacci(2));
    }

    @Test
    public void fibonacciTest() {
        assertEquals(2, fibonacciArraySolving(9));
    }

    private int plusXandY(int x, int y) {
        return x + y;
    }

    private int fibonacci(int n) {

        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    private int fibonacciArraySolving(int n) {

        int result_1;
        int result_2;
//        if (mFibonacciArray == null) {
//            mFibonacciArray = new ArrayList<>();
//            mFibonacciArray.add(0,1);
//            mFibonacciArray.add(1,1);
//        }

        if (n == 0) {
            return 1;
        } else if (n ==1) {
            return 1;
        } else {
            result_1 = 1;
            result_2 = 1;
            int result = 1;
            for (int i = 2; i <= n; i++) {
//                int result = mFibonacciArray.get(i - 1) + mFibonacciArray.get(i - 2);
                result_2 = result_1;
                result_1 = result;
                result = result_1 + result_2;
//                mFibonacciArray.add(i, result);
            }
            return result;
        }
    }
}