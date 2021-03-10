public class Main {
    public static void main(String[] args) {

    }

    private static void GOO(int groupSize) {
        int max = 0;
        int[] using = {};
        long start = System.currentTimeMillis();

        //todo all
        //generate list containing 1 to groupSize
        //find all combos that add up to group size from this list
        //multiply the combos together
        //find the max combo and the corresponding sizes
        //output results

        long end = System.currentTimeMillis();

        System.out.println("Found result in " + (end - start) + " ms.");
        System.out.println("Result: " + max);
    }
}
