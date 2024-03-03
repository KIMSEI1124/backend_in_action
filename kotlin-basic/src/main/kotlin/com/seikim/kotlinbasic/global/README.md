```kotlin
interface DataBase<T> {
    fun save(value: T): T
    fun findById(id: Int): T?
}
```

인터페이스의 반환값이 `null`일 수 있다면 `?`를 붙여줍니다.