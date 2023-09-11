import java.util.*;

public class test {
	public static void main(String[] args) {
		int[] slot = {999, 124, 43, 299, 615, 848, 756, 165, 667, 880};
		int cmp = compare(slot[0],slot[9]);
		System.out.println("cmp - '" + cmp +"'");
	}

	static int compare(int a, int b){
		if (a<b) return 1;
		else if (a>b) return -1;
		else return 0;
	}

}
