package Hjelpemetoder;

public class StorteringsMetoder {

    public static void bubbleSort(int[] a, int start, int end){
        int temp;
        for (int i = start; i < end; i++){
            for (int j = start; j < (end -1); j++ ){
                if (a[j] > a[j+1]){
                    temp = a[j+1];
                    a[j+1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    public static int partisjon(int a[], int start, int end)
    {
        int vippePunkt = a[end];
        int i = (start-1);
        for (int j=start; j<end; j++)
        {
            if (a[j] < vippePunkt)
            {
                i++;

                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        int temp = a[i+1];
        a[i+1] = a[end];
        a[end] = temp;

        return i+1;
    }

    public static void quickSort(int a[], int start, int end)
    {
        if (start < end)
        {
            int vippePunkt = partisjon(a, start, end);


            quickSort(a, start, vippePunkt-1);
            quickSort(a, vippePunkt+1, end);
        }
    }
}
