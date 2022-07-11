package com.example.convertion

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.convertion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.convertButton.setOnClickListener {
            convert()
        }
        binding.textInput.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
        displayValue(0.0)
    }

    private fun convert() {
        val input = getInputValue()
        val valueInMl = when (binding.fromUnitOption.checkedRadioButtonId) {
            R.id.option_from_ml -> input
            R.id.option_from_cup -> input*236.6
            R.id.option_from_tsp -> input*4.929
            else -> input*14.79
        }
        val convertedValue = when (binding.toUnitOption.checkedRadioButtonId) {
            R.id.option_to_ml -> valueInMl
            R.id.option_to_cup -> valueInMl/236.6
            R.id.option_to_tsp -> valueInMl/4.929
            else -> valueInMl/14.79
        }
        displayValue(convertedValue)
    }

    private fun displayValue(value: Double) {
        val toUnit = when (binding.toUnitOption.checkedRadioButtonId) {
            R.id.option_to_ml -> "ml"
            R.id.option_to_cup -> "cup"
            R.id.option_to_tsp -> "tsp"
            else -> "tbsp"
        }
        binding.resultText.text = getString(R.string.converted_amount, "%.2f".format(value), toUnit)
    }

    private fun getInputValue(): Double {
        val value = binding.textInput.text.toString().toDoubleOrNull()
        if (value == null) { return 0.0 }
            else { return value }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}