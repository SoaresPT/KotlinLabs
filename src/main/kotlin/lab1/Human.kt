package lab1

open class Human(val name: String, var age: Int) {
    fun getOlder() {
        age++
    }
}