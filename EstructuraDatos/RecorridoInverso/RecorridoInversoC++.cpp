#include <iostream>

int main() {
    int arr[] = {40, 50, 60, 70, 80, 90};
    int len = 6;

    std::cout << "Recorrido inverso del array: ";
    std::cout << "\nLos elementos del array son: ";

    for (int idx = len - 1; idx >= 0; idx--) {
        std::cout << arr[idx] << " ";
    }
    std::cout << "\n";

    return 0;
}