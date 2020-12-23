package baekjoon;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String arg[]) {
		Scanner sc = new Scanner(System.in);
		int m = sc.nextInt();
		int n = sc.nextInt();
		ArrayList<Integer> arr = new ArrayList<>();
		
		int sum = 0;
		int min = 0;
		
		for(int i = 1; i<=100; i++) {
			arr.add(i*i);
		}
		int check = 0;//check for min
		for(int i = m; i<=n; i++) {
			if(arr.contains(i) == true) {//if have Perfect square number
				if(check == 0) {
					min = i;
					check = 1;
				}
				sum += i;
			}
		}
		if(check == 0) {//if don't have Perfect square number
			System.out.println(-1);
		}
		else {//if have Perfect square number
			System.out.println(sum);
			System.out.println(min);
		}

	}
}

	