package tests;

import static java.lang.System.out;

public class output_2 {
    public static void main(String[] args) {

        int num = 33, i = 2;
        boolean flag = false;
        while (i <= num / 2) {
		System.out.println("block_0");
            // condition for nonprime number
            if (num % i == 0) {
		System.out.println("block_1");
                flag = true;
                break;
            }

            ++i;
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
