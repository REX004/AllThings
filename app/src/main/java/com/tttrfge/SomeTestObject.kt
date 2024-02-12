package com.tttrfge

import java.util.LinkedList

class SomeTestObject(val turn: LinkedList<String>) {

    var icEmpty = false
    fun poll(): String{
        return turn.poll()
    }

    fun count(): Int{
        return turn.count()
    }

    fun getValuesOfButtons(): List<String>{
        if(count() == 2) {
            return listOf("Skip", "Next")

        }
        if(count() == 1) {
            return listOf("Back", "Finish")

        }
        if(count() == 0) {
            return listOf("Sign Up")

        }
        return emptyList()
    }
    fun navigateToEmpty():Boolean{
        return count() == 0
    }
}