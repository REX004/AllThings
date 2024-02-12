package com.tttrfge


import org.jetbrains.annotations.TestOnly
import org.junit.Assert
import java.util.LinkedList

class TestUnit {

    lateinit var myQueue: LinkedList<String>

    fun beforeTest() {
        myQueue = LinkedList()
        myQueue.add("first")
        myQueue.add("second")
        myQueue.add("third")
    }


    fun imageAndTextFromTheQueueAreRetrievedCorrectly() {
        val first = "first"
        val second = "second"
        val third = "third"

        Assert.assertEquals(first, myQueue.peek())
        myQueue.poll() // Remove the first element

        Assert.assertEquals(second, myQueue.peek())
        myQueue.poll() // Remove the second element

        Assert.assertEquals(third, myQueue.peek())
        // Here you might want to do something with the third element
    }
}
