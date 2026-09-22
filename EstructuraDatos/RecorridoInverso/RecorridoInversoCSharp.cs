using System;
public class RecorridoInversoCSharp
{
    public static void Main(string[] args)
    {
        int[] arr = { 40, 50, 60, 70, 80, 90 };

        Console.Write("Recorrido inverso del array: ");
        Console.WriteLine("\nLos elementos del array son: ");

        for (int idx = arr.Length - 1; idx >= 0; idx--)
        {
            Console.Write(arr[idx] + " ");
        }
        Console.WriteLine();
    }
}