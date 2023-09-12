import java.util.*;

public class HybridSort {
	
	int[] slot = {0, 0, 1, 3, 9, 4, 0, 1, 7, 1};
	int S = 4;
	int keyComp;

	public HybridSort(){
		// slot = generateRandom(10, 10);
		keyComp = 0;
	}
	public static void main(String[] args)
	{
		HybridSort list = new HybridSort();
    System.out.println("Before Merge Sort:");
    System.out.println(Arrays.toString(list.slot));

    list.hybridSorter(0, 9); // Sort the entire array, assuming the array has 10 elements (indices 0 to 9).

    System.out.println("After Merge Sort wtih InsertSort:");
    System.out.println(Arrays.toString(list.slot));

		System.out.println("Key Comparision - " + list.keyComp);
	}

	public static int[] generateRandom(int size, int max) {
        return new Random().ints(size, 0, max).toArray();
  }

	void hybridSorter(int n, int m){
		int mid = (n+m)/ 2;
		if (m-n <= 0) return;
		else if (m-n > 1){
			hybridSorter(n, mid);
			hybridSorter(mid+1, m);
		}
		merge(n, m);
	}

	void merge(int n, int m)
	{		
		if (slot.length <= S){
			insertionSort(slot.length);
		}
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
			else 
			{   //slot[a] == slot[b]
					if (a == mid && b == m) break;
					tmp = slot[b++];
					a++;
					for (i = ++mid; i > a; i--) slot[i] = slot[i-1];
					slot[a++] = tmp;
			}
		} // end of while loop;
	} // end of merge

	int compare(int a, int b){
		keyComp++;
		if (a>b) return 1;
		else if (a<b) return -1;
		else return 0;
	}

	void insertionSort (int n)
	{ // input slot is an array of n records; // assume n > 1;
		for (int i=1; i < n; i++)
			for (int j=i; j > 0; j--) {
				keyComp++;
				if (slot[j] < slot[j-1])
				{
					swap(slot[j], slot[j-1], j); 
				}
			else break;
		}
	}
	void swap(int n, int m, int index)
	{
		slot[index - 1] = n;
		slot[index] = m;
	}

}
