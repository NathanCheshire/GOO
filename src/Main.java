import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main {
    /** finds the maximum order possible of an element from the symmetric group S sub n
     * @author Nathan Cheshire
     * @since 3-10-21
     */
    public static void main(String[] args) {
        //GOO(20); //420
        //GOO(40); //?
        GOO(5);
        //GOO(7); //12
        //GOO(13); //60
    }

    private static void GOO(int n) {
        int max = 0;
        ArrayList<Integer> using = new ArrayList<>();

        ArrayList<Integer> productOfCycleLengths = new ArrayList<>(); //the product of the cycle lengths below
        ArrayList<ArrayList<Integer>> cycles = new ArrayList<>(); //cycles of which all add up to n

        long start = System.currentTimeMillis();

        ArrayList<Integer> ones = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            ones.add(1);
        }

        cycles.add(ones);

        while (cycles.size() < factorial(n)) {
            System.out.println(cycles.size());
            ArrayList<Integer> randNew = new ArrayList<>(cycles.get(rand(0, cycles.size() - 1)));

            if (randNew.size() < 2)
                continue;

            int one = rand(0,randNew.size() - 1);
            int two = rand(0,randNew.size() - 1);

            while (one == two)
                two = rand(0,randNew.size() - 1);

            int addBackIn = randNew.get(one) + randNew.get(two);
            randNew.remove(one);
            randNew.remove(two);
            randNew.add(addBackIn);

            if (!cycles.contains(randNew)) {
                cycles.add(randNew);
                System.out.println("advanced");
            }
        }

        //start in middle of a random list from cycles
        //between 1 and n - 1 times:
        // remove two random elements, add the two elements together, add the result back in
        // if this arraylist is not in cycles (!arrl.equals(arr2))
        //      add it in then

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

        System.out.println("max order of symmetric group S" + n + " is " + max + "\nusing: [" + sb.toString() + "]");
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

    private static int factorial(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++)
            fact = fact * i;
        return fact;
    }

    private static int rand(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}