import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//or can just import everything like import java.util.*;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        //lol thanks parth
        Set<Integer> seensofar= new HashSet<>();
        for (int i: values){
            seensofar.add(i);
            //lineartime
        }
        //what not present
        for (int x=0; x<=values.length;x++){
            if(!seensofar.contains(x)){
                return x;
            }
        } //lineartime
        return -1;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */
    public static boolean sumTo(int[] values, int n) {
        // TODO
        //shout out to geeks for geeks
        //get sum minus the int n and if there is any number in the array that matches yes
        Set<Integer> sum = new HashSet<>();
        for (int counter=0; counter<values.length; counter++) {
            if (sum.contains(n - values[counter])) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        // TODO
        //thanks to stack overflow
        Map<Character, Integer> perm = new HashMap<>();
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        for (char i : str1) {
            if (!perm.containsKey(i)) {
                perm.put(i, 1);
            } else {
                int a = perm.get(i);
                perm.put(i, a + 1);
            }
        }
        for (char k : str2) {
            if (! perm.containsKey(k)) {
                return false;
            } else {
                int counter = perm.get(k);
                perm.put(k, counter-1);
            }
        }
        for (char j: perm.keySet()) {
            if (perm.get(j) != 0) {
                return false;
            }
        }
        return true;
    }
}