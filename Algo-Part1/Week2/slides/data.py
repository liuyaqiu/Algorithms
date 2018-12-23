import random

n = random.randint(5, 10)
array_a = sorted([random.randint(-10, 10) for _ in range(n)])
array_b = sorted([random.randint(-10, 10) for _ in range(n)])
print(n)
for item in array_a:
    print(item, end=' ')
print()
for item in array_b:
    print(item, end=' ')
print()
