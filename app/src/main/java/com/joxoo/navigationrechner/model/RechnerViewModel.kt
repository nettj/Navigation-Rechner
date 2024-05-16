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
        val operatorCount = operatorList.size
        /*
        ermittelt die Größe der operatorList und weist sie der Variable index zu
         */
        val currentValue = numberList.getOrNull(operatorCount)
        /*
        versucht, den Wert an der Position index aus der numberList zu holen.
        Wenn es an dieser Position keinen Wert gibt, gibt sie null zurück.
         */
        if (currentValue == null) {
            numberList.add(number)
            /*
            überprüft ob currentValue null ist.
            wenn ja, fügt sie die Eingabezahl an der Position index zur numberList hinzu
             */
        } else {
            numberList[operatorCount] = "${currentValue}${number}".toInt()
            /*
            Wenn currentValue nicht null ist, ist bereits eine Zahl an dieser Position in der numberList
            hier verknüpft man die Funktion currentValue und die Eingabe number als String, konvertiert sie zurück in eine Ganzzahl
            und ersetzt currentValue in der numberList durch diese neue Zahl
             */
        }
        _textValue.value += number
        /*
        hier hängt die Funktion die Eingabe number an das _textValue LiveData-Objekt an
         */
    }

    fun addOperator(operator: String) {
        operatorList.add(operator)
        _textValue.value += " $operator "
    }

    fun reset() {
        numberList.clear()
        operatorList.clear()
        _textValue.value = ""
    }

    fun calculate(): Int {
        //reduziert meine liste an nummern und operatoren auf ein ergebnis
        return numberList.reduceIndexed { index, firstNumber, secondNumber ->
            Log.d("RechnerViewModel", numberList.joinToString(","))
            Log.d("RechnerViewModel", "firstNumber: $firstNumber, secondNumber: $secondNumber, operatorIndex: $index")
            //erste zahl ist immer das erste element der liste, deswegen nimmt er die zweite als index wert deswegen -1
           // wenn er keinen operator ht, dann soll er die fristnumber zurück geben
            val operator = operatorList.getOrNull(index - 1) ?: return@reduceIndexed firstNumber
            //wenn keine operatoren vorhanden sind, gibt er die erste zahl zurück
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