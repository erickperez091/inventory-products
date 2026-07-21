package com.example.products.configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String []args){
        // AABDBAA
        System.out.println(isPalindrome("AABDBA"));
    }

    private static boolean isPalindrome(String input){
        List<String> list = new ArrayList<>(5);
        boolean isPalindrome = true;
        int i = 0;
        int j = input.length() - 1;
        while(i < j){
            if(input.charAt(i) != input.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return isPalindrome;
    }
}
