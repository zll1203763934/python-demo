# 组合数据类型操作示例

# 列表操作示例
my_list = [1, 2, 3, 4, 5]
# 访问列表元素
print(my_list[0])
# 修改列表元素
my_list[0] = 10
print(my_list)
my_list.insert(1,20)
# 列表追加元素
my_list.append(6)
print(my_list)

# 元组操作示例
my_tuple = (1, 2, 3)
# 访问元组元素
print(my_tuple[0])

# 集合操作示例
my_set = {1, 2, 3, 4, 5}
# 添加元素到集合
my_set.add(6)
print(my_set)
# 移除集合中的元素
my_set.remove(1)
print(my_set)

# 字典操作示例
my_dict = {'name': 'John', 'age': 30}
# 访问字典元素
print(my_dict['name'])
del my_dict['name']
print(my_dict)
print(my_dict.pop('age'))
# 修改字典元素
my_dict['age'] = 31
print(my_dict)
# 添加新的键值对到字典
my_dict['city'] = 'New York'
print(my_dict)