public class RecorridoLinealJava
{
    public static void main (String[] args)
    {
        int arr[] = {40, 50, 60, 70, 80, 90};
        int size = arr.length;
        System.out.println("Recorrido lineal (secuencial): ");
        System.out.println("Los elementos del array son: ");
        for(int idx = 0; idx < size; idx++)
        {
            System.out.print(arr[idx] + " ");
        }
    }
}