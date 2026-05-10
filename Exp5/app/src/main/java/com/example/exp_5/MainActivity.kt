package com.example.exp_5

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dayValues = FloatArray(7)
        val spinner: Spinner = findViewById(R.id.spinner)
        val amountInput: EditText = findViewById(R.id.amount)
        val updateButton: Button = findViewById(R.id.button)
        val chart: BarChart = findViewById(R.id.graph)

        val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val entries = ArrayList<BarEntry>()

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(days)
        chart.xAxis.granularity = 1f
        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false

        updateButton.setOnClickListener {
            val amount = amountInput.text.toString()
            if (amount.isNotEmpty()) {
                // Update the specific day's value
                dayValues[spinner.selectedItemPosition] = amount.toFloat()

                // Convert array to Chart Entries
                val entries = dayValues.mapIndexed { index, value ->
                    BarEntry(index.toFloat(), value)
                }

                // IMPORTANT: You must wrap the DataSet in BarData and set it to the chart
                val dataSet = BarDataSet(entries,"Day wise Expenses")
                chart.data = BarData(dataSet)
                val colors = listOf(Color.RED, Color.BLUE, Color.GREEN,Color.CYAN,Color.MAGENTA,Color.YELLOW,Color.LTGRAY)
                dataSet.setColors(colors)
                dataSet.valueTextSize = 16f
                chart.animateY(1000)
                chart.invalidate() // Refresh

                amountInput.text.clear()
            }

        }
    }
}
