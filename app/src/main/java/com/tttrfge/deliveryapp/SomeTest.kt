package com.tttrfge.deliveryapp

import com.tttrfge.SomeTestObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.LinkedList

lateinit var SomeTestObject: SomeTestObject

class SomeTest {

    @Before
    fun beforeTest(){
        // Создание и инициализация LinkedList<String> с тремя элементами
        var turn = LinkedList<String>()
        turn = LinkedList()
        turn.add("first")
        turn.add("second")
        turn.add("third")

        // Инициализация глобальной переменной SomeTestObject экземпляром SomeTestObject
        // и передача созданного LinkedList в качестве параметра
        SomeTestObject = SomeTestObject(turn)
    }

    @Test
    fun Imagecorrectly(){
        // Проверка, что метод poll объекта SomeTestObject возвращает ожидаемые значения
        val first = "first"
        val second = "second"
        val third = "third"
        Assert.assertEquals(first, SomeTestObject.poll())
        Assert.assertEquals(second, SomeTestObject.poll())
        Assert.assertEquals(third, SomeTestObject.poll())
    }

    @Test
    fun SomeCount(){
        // Проверка, что метод count объекта SomeTestObject корректно возвращает количество элементов в очереди
        Assert.assertEquals(3, SomeTestObject.count())

        // Вызов метода poll несколько раз и проверка уменьшения количества элементов в очереди
        SomeTestObject.poll()
        Assert.assertEquals(2, SomeTestObject.count())
        SomeTestObject.poll()
        Assert.assertEquals(1, SomeTestObject.count())
        SomeTestObject.poll()
        Assert.assertEquals(0, SomeTestObject.count())
    }

    @Test
    fun correct() {
        // Проверка, что после нескольких вызовов метода poll с корректными значениями
        // метод getValuesOfButtons возвращает ожидаемые значения кнопок в зависимости от количества элементов в очереди
        Assert.assertEquals(3, SomeTestObject.count())
        SomeTestObject.poll()
        assert(SomeTestObject.getValuesOfButtons().containsAll(listOf("Skip", "Next")))
        SomeTestObject.poll()
        assert(SomeTestObject.getValuesOfButtons().containsAll(listOf("Back", "Finish")))
        SomeTestObject.poll()
        assert(SomeTestObject.getValuesOfButtons().containsAll(listOf("Sign Up")))
    }

    @Test
    fun correctSign(){
        // После вызова нескольких раз метода poll проверка, что метод getValuesOfButtons возвращает ожидаемые значения кнопок
        SomeTestObject.poll()
        SomeTestObject.poll()
        SomeTestObject.poll()
        assert(SomeTestObject.getValuesOfButtons().containsAll(listOf("Sign Up")))
    }

    @Test
    fun correctNav(){
        // После вызова нескольких раз метода poll проверка, что метод navigateToEmpty возвращает true
        SomeTestObject.poll()
        SomeTestObject.poll()
        SomeTestObject.poll()
        assert(SomeTestObject.navigateToEmpty())
    }
}
