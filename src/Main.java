import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    /** finds the maximum order possible of an element from the symmetric group S sub n
     * @author Nathan Cheshire
     * @since 3-10-21
     */
    private static ArrayList<ArrayList<Integer>> openCycles;
    private static ArrayList<ArrayList<Integer>> closedCycles;

    public static void main(String[] args) {
        //GOO(20); //420
        GOO(5); //6
        GOO(7); //12
        //GOO(13); //60
    }

    private static void GOO(int n) {
        int max = 0;
        ArrayList<Integer> using = new ArrayList<>();
        openCycles = new ArrayList<>();
        closedCycles = new ArrayList<>();
        ArrayList<Integer> productOfCycleLengths = new ArrayList<>();

        ArrayList<Integer> ones = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            ones.add(1);
        }

        openCycles.add(ones);
        ArrayList<Integer> nList = new ArrayList<>();
        nList.add(n);
        openCycles.add(nList);

        for (int i = n - 1 ; i > 0 ; i--) {
            ArrayList<Integer> cycle = new ArrayList<>();
            cycle.add(i);

            for (int j = 0 ; j < n - i ; j++) {
                cycle.add(1);
            }

            if (!openCycles.contains(cycle))
                openCycles.add(cycle);
        }

        ArrayList<Integer> currentCycle;

        while (!openCycles.isEmpty()) {
            currentCycle = new ArrayList<>(openCycles.get(0));

            closedCycles.add(currentCycle);
            openCycles.remove(currentCycle);
            cycleGenerator(currentCycle);
        }

        for (ArrayList<Integer> cycle : closedCycles) {
            productOfCycleLengths.add(lcmArray(cycle));
        }

        for (int i = 0 ; i < productOfCycleLengths.size() ;i++) {
            if (productOfCycleLengths.get(i) > max) {
                max = productOfCycleLengths.get(i);
                using = closedCycles.get(i);
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
    private static void cycleGenerator(ArrayList<Integer> cycle){
        if (cycle.size() < 3) //len 2 right now so we don't do anything
            return;

        int r1 = rand(0, cycle.size() - 1);
        int r2 = rand(0, cycle.size() - 1);

        while (r2 == r1)
             r2 = rand(0, cycle.size() - 1);

        ArrayList<Integer> r1r2Copy = new ArrayList<>(cycle);
        int sum = r1r2Copy.get(r1) + r1r2Copy.get(r2);
        r1r2Copy.set(r1, sum);
        r1r2Copy.remove(r2);
        openCycles.add(r1r2Copy); //if not in here
    }
}