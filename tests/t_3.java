package tests;

public class t_3 {
    public static void main(String[] args) {
        int number = 34;
        boolean flag = false;
        for (int i = 2; i <= number / 2; ++i) {

        }

        if (!flag)
            System.out.println(number + " cannot be expressed as the sum of two prime numbers.");
    }

    // Function to check prime number
    static boolean checkPrime(int num) {
        boolean isPrime = true;
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }
}
