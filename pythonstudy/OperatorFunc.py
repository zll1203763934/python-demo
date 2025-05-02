# Python中的运算符有不同的优先级，这决定了在一个表达式中各个运算符的执行顺序。通常，
# 括号 () 的优先级最高，用于改变默认的运算顺序。其他运算符的优先级从高到低依次为：
# 幂运算符 **、正负号 +x, -x、乘法 *、除法 /、取模 %、取整除法 //、加法 +、减法 -、位运算符（<<, >>, &, ^, |）、
# 比较运算符（<, <=, >, >=, ==, !=）、逻辑运算符（not, and, or）和赋值运算符（= 及其复合形式

# 算数运算符
# 加法
a = 1+2
print(a) # 3
# 减法
a = 1-2
print(a) # -1
# 乘法
a = 1*2
print(a) # 2
# 除法
a = 1/2
print(a) # 0.5
# 取余
a = 1%2
print(a) # 1
# 幂运算
a = 2**3
print(a) # 8
# 整除
a = 2//3
print(a) # 0

# 比较运算符
# 等于
a = 1==2
print(a) # False
# 不等于
a = 1!=2
print(a) # True
# 大于
a = 1>2
print(a) # False
# 小于
a = 1<2
print(a) # True
# 大于等于
a = 1>=2
print(a) # False
# 小于等于
a = 1<=2
print(a) # True

# 逻辑运算符
# 与
a = True and False
print(a) # False
# 或
a = True or False
print(a) # True
# 非
a = not True
print(a) # False
# 赋值运算符
# 等于
a = 1
a += 1
print(a) # 2
# 减等于
b = 1
b -= 1
print(b) # 0
# 乘等于
a *= 1
print(a) # 2
# 除等于
a /= 1
print(a) # 2.0
# 取余等于
a %= 1
print(a) # 0
# 幂等于
a **= 1
print(a) # 0
# 整除等于
a //= 1
print(a) # 0
# 成员运算符
# 在
b = (1,2,3)
a = 1 in b
print(a) # True
# 不在
a = 1 not in b
print(a) # False

# 身份运算符
# 是
a = 1 is 1
print(a) # True
# 不是
a = 1 is not 1
print(a) # False

# 位运算符
a = 1 #0000 0001
b = 2 #0000 0010
# 按位与
a = a & b # 0000 0000
print(a) # 0
# 按位或
a = 1 | 2 # 0000 0011
print(a) # 3
# 按位异或
a = 1 ^ 2 # 0000 0011
print(a) # 3
# 按位取反
a = ~1 # 1111 1110
print(a) # -2
# 左移
a = 1 << 2 # 0000 0100
print(a) # 4
# 右移
a = 1 >> 2 # 0000 0000
print(a) # 0
