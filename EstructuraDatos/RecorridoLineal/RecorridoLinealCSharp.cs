using System;

public class RecorridoLinealCSharp
{
    public static void Main(string[] args)
    {
        int[] arr = { 40, 50, 60, 70, 80, 90 };
        int size = arr.Length;
        
        Console.WriteLine("Recorrido lineal (secuencial): ");
        Console.WriteLine("Los elementos del array son: ");
        
        for (int idx = 0; idx < size; idx++)
        {
            Console.Write(arr[idx] + " ");
        }
    }
}