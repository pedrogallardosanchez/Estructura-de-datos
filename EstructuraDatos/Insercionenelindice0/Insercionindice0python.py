inputArr = [11, 21, 31, 41, 51, 61]
ele =52
print("Antes de la insercion, el array es: ")
for j in range(len(inputArr)):
    print(inputArr[j], end=" ")
inputArr.insert(0, ele)
print("\nDespues de la insercion, el array es: ")
for j in range(len(inputArr)):
    print(inputArr[j], end=" ")