import java.util.Arrays;

class MaximumContiguousSubsequenceSum {
    public static void main(String[] args) {
        int[] input = {1,-3,2,5,-8};
        assert mcss(input) == 7;
    }

    static class Data {
        int max;
        int prefix;
        int suffix;
        int total;
        Data(int max, int prefix, int suffix, int total) {
            this.max = max;
            this.prefix = prefix;
            this.suffix = suffix;
            this.total = total;
        }
    };

    static int mcss(int[] a) {
        Data d = mcss_(a);
        return d.max;
    }

    static Data mcss_(int[] a) {
        if (a.length == 0) {
            return new Data(0, 0, 0, 0);
        } else if (a.length == 1) {
            int v = Math.max(a[0], 0);
            return new Data(v, v, v, a[0]);
        } else {
            // split array in half
            int k = a.length / 2;
            int[] a1 = Arrays.copyOf(a, k);
            int[] a2 = Arrays.copyOfRange(a, k, a.length);

            // recurse on each half
            Data b1 = mcss_(a1);
            Data b2 = mcss_(a2);

            // combine solutions of each half
            Data d = new Data(
                Math.max(b1.suffix + b2.prefix, Math.max(b1.max, b2.max)),
                Math.max(b1.prefix, b1.total + b2.prefix),
                Math.max(b2.suffix, b1.suffix + b2.total),
                b1.total + b2.total
            );
            return d;
        }
    }
}
