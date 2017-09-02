public class FizzBuzz {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            System.out.println(fizzbuzz(i));
        }
    }

    static String fizzbuzz(int n) {
        if (n % 3 == 0 && n % 5 == 0) {
            return "FizzBuzz";
        } else if (n % 5 == 0) {
            return "Buzz";
        } else if (n % 3 == 0) {
            return "Fizz";
        } else {
            return String.valueOf(n);
        }
    }
}
