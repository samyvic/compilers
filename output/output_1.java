package tests;

public class output_1 {
    public static void main(String[] args) {

        int num = 29;
        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) {
		System.out.println("block_0");
            // condition for nonprime number
            if (num % i == 0) {
		System.out.println("block_1");
                flag = true;
                break;
            }
        }

        if (!flag)
            {System.out.println(num + " is a prime number.");
		System.out.println("block_2");
		}
        else{
            System.out.println(num + " is not a prime number.");
		System.out.println("block_3");
		}
    }
}
