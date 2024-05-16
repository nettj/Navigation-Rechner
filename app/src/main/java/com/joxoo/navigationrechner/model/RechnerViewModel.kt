package com.joxoo.navigationrechner.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RechnerViewModel : ViewModel() {

    private val numberList = mutableListOf<Int>()

    private val operatorList = mutableListOf<String>()

    private val _textValue = MutableLiveData("")

    val textValue: LiveData<String>
        get() = _textValue

    fun addNumber(number: Int) {
        val index = operatorList.size
        //setzt den wert anhand des index welcher der operatorList size entspricht
        val currentValue = numberList.getOrNull(index)
        if (currentValue == null) {
            numberList.add(index, number)
        } else {
            numberList[index] = "${currentValue}${number}".toInt()
        }
        _textValue.value += number
    }

    fun addOperator(operator: String) {
        operatorList.add(operator)
        _textValue.value += " $operator "
    }

    fun reset() {
        numberList.clear()
        operatorList.clear()
    }

    fun calculate(): Int {
        return numberList.reduceIndexed { index, firstNumber, secondNumber ->
            Log.d("RechnerViewModel", numberList.joinToString(","))
            Log.d("RechnerViewModel", "firstNumber: $firstNumber, secondNumber: $secondNumber, operatorIndex: $index")
            val operator = operatorList.getOrNull(index - 1) ?: return@reduceIndexed firstNumber
            when (operator) {
                "+" -> firstNumber.plus(secondNumber)
                "-" -> firstNumber.minus(secondNumber)
                "*" -> firstNumber * secondNumber
                "/" -> firstNumber / secondNumber
                else -> firstNumber
            }
        }
    }
}