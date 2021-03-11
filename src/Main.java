import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    /** finds the maximum order possible of an element from the symmetric group S sub n
     * @author Nathan Cheshire
     * @since 3-10-21
     */
    private static ArrayList<ArrayList<Integer>> cycles;
    private static ArrayList<ArrayList<Integer>> starterCycles;

    public static void main(String[] args) {
        GOO(20); //420
        GOO(5); //6
        GOO(7); //12
        GOO(13); //60
    }

    private static void GOO(int n) {
        int max = 0;
        ArrayList<Integer> using = new ArrayList<>();
        starterCycles = new ArrayList<>();

        ArrayList<Integer> productOfCycleLengths = new ArrayList<>(); //the product of the cycle lengths below
        cycles = new ArrayList<>(); //cycles of which all add up to n

        ArrayList<Integer> ones = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            ones.add(1);
        }

        starterCycles.add(ones);
        ArrayList<Integer> nList = new ArrayList<>();
        nList.add(n);
        starterCycles.add(nList);

        for (int i = n - 1 ; i > 0 ; i--) {
            ArrayList<Integer> cycle = new ArrayList<>();
            cycle.add(i);

            for (int j = 0 ; j < n - i ; j++) {
                cycle.add(1);
            }

            if (!cycles.contains(cycle))
                starterCycles.add(cycle);
        }

        for (ArrayList<Integer> cycle : starterCycles)
            cycleGenerator(cycle);

        for (ArrayList<Integer> cycle : cycles) {
            productOfCycleLengths.add(lcmArray(cycle));
        }

        for (int i = 0 ; i < productOfCycleLengths.size() ;i++) {
            if (productOfCycleLengths.get(i) > max) {
                max = productOfCycleLengths.get(i);
                using = cycles.get(i);
            }
        }

        System.out.println("max order of symmetric group S" + n + " is " + max + " using: " + using.toString());
    }

    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();

        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    private static int lcmArray(ArrayList<Integer> cycle) {
        return lcmofarray(convertIntegers(cycle), 0, cycle.size());
    }

    private static int lcmofarray(int[] arr, int start, int end) {
        if ((end-start)==1) return lcm(arr[start],arr[end-1]);
        else return (lcm (arr[start], lcmofarray(arr, start+1, end)));
    }

    private static int lcm(int a, int b){
        return ((a*b)/gcd(a,b));
    }

    private static int gcd(int a, int b){
        if (a<b) return gcd(b,a);
        if (a%b==0) return b;
        else return gcd(b, a%b);
    }

    private static int rand(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    //cycle given is already in cycles so don't change
    private static void cycleGenerator(ArrayList<Integer> cycle) {
        if (cycle.size() < 3)
            return;

        int middleIndex = (int) Math.floor(cycle.size() / 2);
        int middleValue = cycle.get(middleIndex);

        if (middleIndex - 1 >= 0) {
            int leftMidSum = middleValue + cycle.get(middleIndex - 1);
            ArrayList<Integer> leftCopy = new ArrayList<>(cycle);
            leftCopy.set(middleIndex,leftMidSum);
            leftCopy.remove(middleIndex - 1);
            cycles.add(leftCopy);
            cycleGenerator(leftCopy);
        }

        if (middleIndex + 1 < cycle.size() - 1) {
            int rightMidSum = middleValue + cycle.get(middleIndex + 1);
            ArrayList<Integer> rightCopy = new ArrayList<>(cycle);
            rightCopy.set(middleIndex,rightMidSum);
            rightCopy.remove(middleIndex + 1);
            cycles.add(rightCopy);
            cycleGenerator(rightCopy);
        }
    }
}