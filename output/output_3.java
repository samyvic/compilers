package tests;

public class output_3 {
    public static void main(String[] args) {
        int number = 34;
        boolean flag = false;
        for (int i = 2; i <= number / 2; ++i) {
		System.out.println("block_0");

        }

        if (!flag)
            {System.out.println(number + " cannot be expressed as the sum of two prime numbers.");
		System.out.println("block_1");
		}
    }

    // Function to check prime number
    static boolean checkPrime(int num) {
        boolean isPrime = true;

        for (int i = 2; i <= num / 2; ++i) {
		System.out.println("block_2");
            if (num % i == 0) {
		System.out.println("block_3");
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }
}
