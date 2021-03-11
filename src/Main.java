import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    /** finds the maximum order possible of an element from the symmetric group S sub n
     * @author Nathan Cheshire
     * @since 3-10-21
     */
    public static void main(String[] args) {
        GOO(20); //should be 420 I believe
        GOO(40); //no idea personally
        GOO(7); //12
        GOO(13); //60
    }

    private static void GOO(int n) {
        int max = 0;
        ArrayList<Integer> using = null;

        ArrayList<Integer> productOfCycleLengths = null; //the product of the cycle lengths below
        ArrayList<ArrayList<Integer>> cycles = null; //cycles of which all add up to n

        long start = System.currentTimeMillis();

        ArrayList<Integer> oneToNum = null;
        for (int i = 1 ; i < n ; i++)
            oneToNum.add(i);

        //fill cycles up now

        //

        for (ArrayList<Integer> cycle : cycles) {
            productOfCycleLengths.add(LCM(cycle));
        }

        for (int i = 0 ; i < productOfCycleLengths.size() ;i++) {
            if (productOfCycleLengths.get(i) > max) {
                max = productOfCycleLengths.get(i);
                using = cycles.get(i);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Found result in " + (end - start) + " ms.");
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < using.size() ; i++)
            if (i != using.size() - 1)
                sb.append(using.get(i)).append(",");
            else
                sb.append(using.get(i));

        System.out.println("Results:\nmax order: " + max + "\nusing: [" + sb.toString() + "]");
    }

    boolean prime(int a) {
        if (a <= 1)
            return false;

        for (int i = 2; i < a; i++)
            if (a % i == 0)
                return false;

        return true;
    }

    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();

        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    private static int LCM(ArrayList<Integer> arr) {
        int min;
        int max;
        int x;
        int lcm = 0;
        int[] myArray = convertIntegers(arr);

        for (int i = 0 ; i < myArray.length ; i++) {
            for (int j = i + 1; j < myArray.length - 1 ; j++) {
                if (myArray[i] > myArray[j]) {
                    min = myArray[j];
                    max = myArray[i];
                }

                else {
                    min = myArray[i];
                    max = myArray[j];
                }

                for (int k = 0 ; k < myArray.length ; k++) {
                    x = k * max;

                    if (x % min == 0) {
                        lcm = x;
                    }
                }
            }
        }
        return lcm;
    }
}