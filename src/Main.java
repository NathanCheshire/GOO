import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main {
    /** finds the maximum order possible of an element from the symmetric group S sub n
     * @author Nathan Cheshire
     * @since 3-10-21
     */
    private static ArrayList<ArrayList<Integer>> cycles;
    private static int currentN;
    private static int secondsPerGOO = 2;
    //how many seconds should the generate function run
    private static int[] GOOS = {5,7,10,20};
    //your goo values

    public static void main(String[] args) {
        for (int GOO : GOOS)
            GOO(GOO);
    }

    private static void GOO(int n) {
        int max = 0;
        currentN = n;
        ArrayList<Integer> using = new ArrayList<>();
        cycles = new ArrayList<>();
        ArrayList<Integer> productOfCycleLengths = new ArrayList<>();

        ArrayList<Integer> ones = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            ones.add(1);
        }
        Collections.sort(ones);
        cycles.add(ones);
        ArrayList<Integer> nList = new ArrayList<>();
        nList.add(n);
        Collections.sort(nList);
        cycles.add(nList);

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

        while (System.currentTimeMillis() != start + secondsPerGOO * 1000) {
            cycleGenerator(cycles.get(rand(cycles.size() - 1)));
        }

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

    private static int rand(int max) {
        return (int) ((Math.random() * (max)) + 0);
    }

    private static void cycleGenerator(ArrayList<Integer> cycle){
        if (cycle.size() < 3)
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