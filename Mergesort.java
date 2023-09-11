import java.util.*;

public class Mergesort
{
	int[] slot;

	public Mergesort(){
		slot = generateRandom(10, 10);
	}
	public static void main(String[] args)
	{
		Mergesort list = new Mergesort();
    System.out.println("Before Merge Sort:");
    System.out.println(Arrays.toString(list.slot));

    list.mergesort(0, 9); // Sort the entire array, assuming the array has 10 elements (indices 0 to 9).

    System.out.println("After Merge Sort:");
    System.out.println(Arrays.toString(list.slot));
	}

	public static int[] generateRandom(int size, int max) {
        return new Random().ints(size, 0, max).toArray();
  }

	void mergesort(int n, int m){
		int mid = (n+m)/ 2;
		if (m-n <= 0) return;
		else if (m-n > 1){
			mergesort(n, mid);
			mergesort(mid+1, m);
		}
		merge(n, m);
	}

	void merge(int n, int m)
	{		
		int mid = (n+m)/2;
		int a = n, b = mid+1, i, tmp;
		if (m-n <= 0) return;
		while (a <= mid && b <= m) {   
			int cmp = compare(slot[a], slot[b]);
			if (cmp > 0) { //slot[a] > slot[b]
				tmp = slot[b++];
				for (i = ++mid; i > a; i--) slot[i] = slot[i-1];
				slot[a++] = tmp;
			} else if (cmp < 0) //slot[a] < slot[b]
					a++;        
			else {   //slot[a] == slot[b]
					if (a == mid && b == m) 
			break;
					tmp = slot[b++];
					a++;
					for (i = ++mid; i > a; i--)
			slot[i] = slot[i-1];
					slot[a++] = tmp;
						}
				} // end of while loop;
	} // end of merge

	int compare(int a, int b){
		if (a>b) return 1;
		else if (a<b) return -1;
		else return 0;
	}
	
}