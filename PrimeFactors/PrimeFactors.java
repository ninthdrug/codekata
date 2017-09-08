import java.util.List;
import java.util.ArrayList;

public class PrimeFactors {
    public static void main(String[] args) {
        assert findPrimeFactors(1).isEmpty();

        List<Long> pf2 = findPrimeFactors(2);
        assert pf2.size() == 1;
        assert pf2.contains(new Long(2L));

        List<Long> pf15 = findPrimeFactors(15);
        assert pf2.size() == 2;
        assert pf2.contains(new Long(3L));
        assert pf2.contains(new Long(5L));
    }

    static List<Long> findPrimeFactors(long n) {
        List<Long> factors = new ArrayList<>();
        long m = n;
        for (long factor = 2; m > 1 && factor*factor <= n; factor++) {
            while (m % factor == 0) {
                factors.add(factor);
                m /= factor;
            }
        }
        if (m > 1) {
            factors.add(m);
        }
        return factors;
    }
}
