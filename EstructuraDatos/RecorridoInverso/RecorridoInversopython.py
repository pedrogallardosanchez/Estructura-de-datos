arr = [40,50,60,70,80,90]
print("Recorrido inverso del array: ", end=" ")
print("\nLos elementos del array son: ", end=" ")
for idx in range(len(arr) - 1, -1, -1):
    print(arr[idx], end=" ")
print()