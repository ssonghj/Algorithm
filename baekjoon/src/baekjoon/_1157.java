
package baekjoon;

import java.util.Scanner;

public class _1157 {
	public static void main(String arg[]) {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		int[] arr = new int[26];
		
		input = input.toUpperCase();//change to upper alphabet
		
		for(int i = 0; i < input.length(); i++) {//A's ASCII code
			arr[input.charAt(i)-65] += 1;
		}
		
		int max_i = 0;
		char max_c=0;
		
		for(int i = 0; i<arr.length; i++) {
			if( max_i <= arr[i]) {//max < count 
				max_i = arr[i];
				max_c = (char) (i+65);
			}
		}
		
		int n = 0;
		for(int i = 0; i<arr.length;i++) {
			if(max_i == arr[i]) {
				n += 1;
			}
		}
		
		if(n >= 2) {//overlap
			System.out.println('?');
		}
		else {//not overlap
			System.out.println(max_c);	
		}
	}
}
