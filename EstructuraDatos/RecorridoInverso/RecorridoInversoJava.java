public class RecorridoInversoJava 
{
    public static void main(String[] args) {
        int[] arr = {40, 50, 60, 70, 80, 90};

        System.out.print("Recorrido inverso del array: ");
        System.out.println("\nLos elementos del array son: ");

        for (int idx = arr.length - 1; idx >= 0; idx--) {
            System.out.print(arr[idx] + " ");
        }
        System.out.println();
    }
}