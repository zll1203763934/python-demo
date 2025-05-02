a = 1
b = 2
if a > b:
    print("a大于b")
elif a < b:
    print("a小于b")
else:
    print("a等于b")

match a:
    case 1:
        print("a等于1")
    case 2:
        print("a等于2")
    case _:
        print("a不等于1和2")    