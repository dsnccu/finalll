
import java.util.ArrayList;

public class Quicksort {
//	public static void main(String[] args) {
//		int[] x = { 9, 2, 4, 7, 3, 7, 10 };
//		System.out.println(Arrays.toString(x));
// 
//		int low = 0;
//		int high = x.length - 1;
// 
//		quickSort(x, low, high);
//		System.out.println(Arrays.toString(x));
//	}
 
	public static void quickSort(ArrayList<WebNode> nodelist,int low,int high) {
		
		if (nodelist == null || nodelist.size() == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		double pivot = nodelist.get(middle).nodeScore;
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (nodelist.get(i).nodeScore < pivot) {
				i++;
			}
 
			while (nodelist.get(j).nodeScore > pivot) {
				j--;
			}
 
			if (i <= j) {
				double temp = nodelist.get(i).nodeScore;
				nodelist.get(i).nodeScore = nodelist.get(j).nodeScore;
				nodelist.get(j).nodeScore = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(nodelist, low, j);
 
		if (high > i)
			quickSort(nodelist, i, high);
	}
}