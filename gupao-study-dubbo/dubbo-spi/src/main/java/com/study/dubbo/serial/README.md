# 序列化类型

`com.study.dubbo.serial.SerializeUtils`

## java

`com.study.dubbo.serial.java.JDKSerializableDemo`

## Hessian

`com.study.dubbo.serial.hessian.HessianSerializable`

## Hessian2

`com.study.dubbo.serial.hessian.Hessian2SerializeDemo`

## Kryo

`com.study.dubbo.serial.kryo.KryoSerializeDemo`

## 性能对比

10000 次 serialize 与 10000 次 deSerialize   单位：ms

|          | serialize | deSerialize |
| -------- | --------- | ----------- |
| Java     | 103       | 159         |
| Hessian  | 1285      | 208         |
| Hessian2 | 749       | 96          |
| Kryo     | 34        | 50          |

序列化相同对象的情况下，占用的字节数最小 Java > Hessian > Hessian2 > Kryo