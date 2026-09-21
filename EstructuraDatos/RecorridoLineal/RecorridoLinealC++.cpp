#include <iostream>

using namespace std;

int main()
 {
    int arr[] = {40, 50, 60, 70, 80, 90};

    std::cout << "Recorrido lineal (secuencial): " << std::endl;
    std::cout << "Los elementos del array son: " << std::endl;

    for (int elemento : arr) 
    {
        std::cout << elemento << " ";
    }

    return 0;
}