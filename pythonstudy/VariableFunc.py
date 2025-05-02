x = 5;
y = "Hello,World"
print(type(x))
print(type(y))
y = 5
print(type(y))
# 整数
a = 1
print(type(a))
# 浮点数
b = 1.0
print(type(b))
# 布尔值
c = True
print(type(c))
# 字符串
d = "Hello,World"
print(type(d))
# 列表 可变有序列表
e = [1,2,3]
print(type(e))
# 元组  不可变列表, 列表元素不能修改，列表长度,整个列表不能修改
f = (1,2,3)
print(type(f))
# 字典
g = {"name":"张三","age":18}
print(type(g))
# 集合 无序不重复元素集合
h = {1,2,3}
print(type(h))

# 类型转换
aStr = str(a)
print(aStr)
aFloat = float(a)
print(aFloat)