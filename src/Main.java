import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main {
    /** finds the maximum order possible of an element from the symmetric group of degree n
     * I couldn't figure out a proper algorithm to generate all possible subgroups (NOT subsets)
     * For Sn so I just decided to brute force it and force a timeout, turned out to work pretty well
     * I suppose. Maybe I'll have a better understanding of this in the future and come back to it
     * @author Nathan Cheshire
     * @since 3-12-21
     */

    //global vars, do not mess with
    private static ArrayList<ArrayList<Integer>> cycles;
    private static int currentN;

    //how many seconds should the generate function run (for large values of n, you might want to increase this to
    // be sure that it gets all possible groups, if you get multiple 1s in your using array, increase this)
    private static int msPerGoo = 20;
    //your goo values, change these your degrees (you can compute multiple which is why it's an array)
    private static int[] GOOS = {5,7,10,20};

    //NOTE: 10ms is plenty to find S20's max order is 420 using [1,3,4,5,7]

    public static void main(String[] args) {
        for (int GOO : GOOS) {
            String[] parts = GOO(GOO);
            System.out.println("max order of symmetric group S" + GOO + " is " + parts[0] + " using: " + parts[1]);
        }
    }

    //drive code, copy this and below methods if you're stealing my code for your own application
    //(Please credit me in your code if you do <3)
    private static String[] GOO(int n) {
        //max order var
        int max = 0;
        //holds cycles that we randomly generate as of now
        ArrayList<Integer> using = new ArrayList<>();
        //re-init cycles for this symmetric group
        cycles = new ArrayList<>();
        //corresponding cycle lengths for cycles
        ArrayList<Integer> productOfCycleLengths = new ArrayList<>();

        //init array of ones to start with
        ArrayList<Integer> ones = new ArrayList<>();
        for (int i = 0 ; i < n ; i++)
            ones.add(1);

        //always sort
        Collections.sort(ones);
        cycles.add(ones);

        ArrayList<Integer> nList = new ArrayList<>();
        nList.add(n);
        Collections.sort(nList);
        cycles.add(nList);

        //add the following cycles {(n,1,1,1,1...,1),{n-1,1,1,1,1},....}
        for (int i = n - 1 ; i > 0 ; i--) {
            ArrayList<Integer> cycle = new ArrayList<>();
            cycle.add(i);

            for (int j = 0 ; j < n - i ; j++) {
                cycle.add(1);
            }

            Collections.sort(cycle);
            cycles.add(cycle);
        }

        ArrayList<Integer> currentCycle;
        long start = System.currentTimeMillis();

        //generate cycles for msPerGoo ms
        while (System.currentTimeMillis() != start + msPerGoo) {
            cycleGenerator(cycles.get(rand(cycles.size() - 1)));
        }

        //calcualte lcm of cycle lengths
        for (ArrayList<Integer> cycle : cycles) {
            productOfCycleLengths.add(lcmArray(cycle));
        }

        //find max and corresponding cycle
        for (int i = 0 ; i < productOfCycleLengths.size() ;i++) {
            if (productOfCycleLengths.get(i) > max) {
                max = productOfCycleLengths.get(i);
                using = cycles.get(i);
            }
        }

        //todo, keep going for another msPerGOO if our using doesn't have
        // ALL relatively prime elements OR has multiple 1s

        return new String[] {max + "", using.toString()};
    }

    //List of ints to array of ints
    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();

        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    //lcm of list of ints
    private static int lcmArray(ArrayList<Integer> cycle) {
        return lcmofarray(convertIntegers(cycle), 0, cycle.size());
    }

    //lcm of array of ints
    private static int lcmofarray(int[] arr, int start, int end) {
        if ((end - start) == 1)
            return lcm(arr[start], arr[end - 1]);
        else
            return lcm(arr[start], lcmofarray(arr, start + 1, end));
    }

    //least common multiple of two ints
    private static int lcm(int a, int b){
        return ((a * b) / gcd(a, b));
    }

    //greatest common divisor of a and b
    private static int gcd(int a, int b){
        if (a < b)
            return gcd(b, a);
        if (a % b == 0)
            return b;
        else
            return gcd(b, a % b);
    }

    //random number between 0 and max
    private static int rand(int max) {
        return (int) ((Math.random() * (max)) + 0);
    }

    //this generates our cycles currently
    //If I find an actual algorithm I'll return to this code and change this method
    private static void cycleGenerator(ArrayList<Integer> cycle){
        if (cycle.size() < 3) //if the size is 2, then adding the 2 will leave us 1 cycle which is not desireable
            return;

        ArrayList<Integer> newCycle = new ArrayList<>(cycle);
        int r1 = rand(newCycle.size() - 1);
        int r2 = rand(newCycle.size() - 1);
        int sum = newCycle.get(r1) + newCycle.get(r2);
        newCycle.set(r1,sum);
        newCycle.remove(r2);
        Collections.sort(newCycle);
        if (!cycles.contains(newCycle)) {
            cycles.add(newCycle);
        }
    }

    private static int factorial(int n) {
        if (n == 0)
            return 1;
        else return n * factorial(n - 1);
    }
}