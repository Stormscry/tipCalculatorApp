package com.example.tipcalculator

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.tipcalculator.databinding.FragmentMainBinding
import java.text.NumberFormat
import kotlin.math.ceil


class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val b
        get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.btnCalculate.setOnClickListener {
            calculateTips()
            handleKeyEvent(it, KeyEvent.KEYCODE_ENTER)
        }
        b.etServiceCost.setOnKeyListener { v, keyCode, _ ->
            handleKeyEvent(v, keyCode)
        }
    }

    private fun calculateTips() {
        val costStr = b.etServiceCost.text.toString()
        val serviceCost = costStr.toDoubleOrNull()

        serviceCost?.let {
            val tipPercentage = when (b.radioGroup.checkedRadioButtonId) {
                R.id.rbChoice1 -> 0.20
                R.id.rbChoice2 -> 0.18
                else -> 0.15
            }
            var tips = serviceCost * tipPercentage

            if (b.switcher.isChecked)
                tips = ceil(tips)

            val formattedTips = NumberFormat.getCurrencyInstance().format(tips)
            b.tvTipAmount.text = getString(R.string.final_tip_amount, formattedTips)
            b.tilServiceCost.error = null

        } ?: run {
            b.tvTipAmount.text = ""
            b.tilServiceCost.error = "Введите стоимость"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}