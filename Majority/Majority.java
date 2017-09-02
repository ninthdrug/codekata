import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

class Majority {
    public static void main(String [] args) {
        int[] votes = {3,2,2,1,2,2,1};
        System.out.println(findMajorityCandidate(votes));
    }

    static Optional<Integer> findMajorityCandidate(int[] votes) {
        int limit = votes.length / 2;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : votes) {
            int n = counts.merge(i, 1, Integer::sum);
            if (n > limit) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
