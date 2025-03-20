# 以下是Python循环语句的示例

# for循环示例
fruits = ['apple', 'banana', 'cherry']
for fruit in fruits:
    print(fruit)

# while循环示例
count = 0
while count < 5:
    print(count)
    count = count + 1
    
# for循环示例
for i in range(5):
    print(i)

# 循环嵌套示例
for i in range(3):
    for j in range(2):
        print(f'({i}, {j})')

# 使用break语句
for i in range(10):
    if i == 5:
        break
    print(i)

# 使用continue语句
for i in range(10):
    if i % 2 == 0:
        continue
    print(i)

# 使用else语句
for i in range(2,5):
    print(i)
else:
    print('循环结束')

# 使用pass语句
for i in range(10):
    pass