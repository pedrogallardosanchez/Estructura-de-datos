public class Insercionindice0java
{
    public static void main(String[] args)
    {
         int inputArr[] = {11, 21, 31, 41, 51, 61};
        int ele = 52;
        System.out.println("Antes de la insercion, el array es: ");
        for (int j = 0; j < inputArr.length; j++)
            System.out.print(inputArr[j] + " ");
        System.out.println();
        int newArr[] = new int[inputArr.length + 1];
        newArr[0] = ele;
        System.arraycopy(inputArr, 0, newArr, 1, inputArr.length);
        System.out.println("Despues de la insercion, el array es: ");
        for (int j = 0; j < newArr.length; j++)
            System.out.print(newArr[j] + " ");
    }
}