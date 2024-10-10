package com.example.mycalculator

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var state = 1
    var op1: Int = 0
    var op2: Int = 0
    var op1Double: Double = 0.0
    var op2Double: Double = 0.0
    var decimalFactor:Double = 1.0
    var isDecimal:Boolean = false
    var temp: Int = 0
    var temp4: Int = 0
    var op: Int = 0

    lateinit var textContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout_calculator)

        textContent = findViewById(R.id.text_content)

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnplus).setOnClickListener(this)
        findViewById<Button>(R.id.btnminus).setOnClickListener(this)
        findViewById<Button>(R.id.btnmulti).setOnClickListener(this)
        findViewById<Button>(R.id.btndivide).setOnClickListener(this)
        findViewById<Button>(R.id.btnequal).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnBS).setOnClickListener(this)
        findViewById<Button>(R.id.btnposi_nega).setOnClickListener(this)
        findViewById<Button>(R.id.btnpoint).setOnClickListener(this)
    }

    fun formatNumber(number: Double): String {
        return if (number == number.toInt().toDouble()) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }

    override fun onClick(p0: View?) {
        var id = p0?.id
        if(id == R.id.btn0)
            addDigit(0)
        else if(id == R.id.btn1)
            addDigit(1)
        else if(id == R.id.btn2)
            addDigit(2)
        else if(id == R.id.btn3)
            addDigit(3)
        else if(id == R.id.btn4)
            addDigit(4)
        else if(id == R.id.btn5)
            addDigit(5)
        else if(id == R.id.btn6)
            addDigit(6)
        else if(id == R.id.btn7)
            addDigit(7)
        else if(id == R.id.btn8)
            addDigit(8)
        else if(id == R.id.btn9)
            addDigit(9)
        else if(id == R.id.btnplus) {
            if(state == 2)
                calculateResult()
            op = 1
            state = 2
            decimalFactor = 1.0
            if(isDecimal)
                textContent.text = "$op1Double +"
            else
                textContent.text = "$op1 +"
        }
        else if(id == R.id.btnminus) {
            if(state == 2)
                calculateResult()
            op = 2
            state = 2
            decimalFactor = 1.0
            if(isDecimal)
                textContent.text = "$op1Double -"
            else
                textContent.text = "$op1 -"
        }
        else if(id == R.id.btnmulti) {
            if(state == 2)
                calculateResult()
            op = 3
            state = 2
            decimalFactor = 1.0
            if(isDecimal)
                textContent.text = "$op1Double x"
            else
                textContent.text = "$op1 x"
        }
        else if(id == R.id.btndivide) {
            if(state == 2)
                calculateResult()
            op = 4
            state = 2
            decimalFactor = 1.0
            if(isDecimal)
                textContent.text = "$op1Double /"
            else
                textContent.text = "$op1 /"
        }
        else if(id == R.id.btnequal)
            calculateResult()
        else if(id == R.id.btnC) {
            resetCalculator()
            textContent.text = "0"
        }
        else if(id == R.id.btnCE) {
            if (state == 1){
                resetCalculator()
                textContent.text = "0"
            }
            else {
                op2 = 0
                if(isDecimal) {
                    op2Double = 0.0
                    decimalFactor = 1.0
                    textContent.text = "$op1Double ${
                        when (temp) {
                            1 -> "+"
                            2 -> "-"
                            3 -> "x"
                            4 -> "/"
                            else -> "${
                                when (op) {
                                    1 -> "+"
                                    2 -> "-"
                                    3 -> "x"
                                    4 -> "/"
                                    else -> ""
                                }
                            }"
                        }
                    }"
                    if(temp != 0)
                        op = temp
                }
                else {
                    textContent.text = "$op1 ${
                        when (op) {
                            1 -> "+"
                            2 -> "-"
                            3 -> "x"
                            4 -> "/"
                            else -> ""
                        }
                    }"
                }
            }
        }
        else if (id == R.id.btnBS) {
            backspace()
        }
        else if (id == R.id.btnpoint) {
            if(op in 1..4)
                temp = op
            op = 5
            isDecimal = true
            addDecimalPoint()
        }
        else if (id == R.id.btnposi_nega) {
            if(op in 1..4)
                temp4 = op
            op = 6
            toggleSign()
        }
    }

    fun addDigit(c: Int) {
        var temp2 = c.toDouble()
        if(state == 1) {
            when (op) {
                5 -> {
                    decimalFactor *= 10.0
                    op1Double = op1Double + temp2/decimalFactor
                    textContent.text = formatNumber(op1Double)
                }
                6 -> {
                    if(isDecimal) {
                        if(temp == 0) {
                            decimalFactor *= 10.0
                            if (op1Double < 0.0) {
                                op1Double = (-op1Double) + temp2 / decimalFactor
                                op1Double = -op1Double
                            } else
                                op1Double = op1Double + temp2 / decimalFactor
                            textContent.text = formatNumber(op1Double)
                        } else {
                            decimalFactor *= 10.0
                            if (op2Double < 0.0) {
                                op2Double = (-op2Double) + temp2 / decimalFactor
                                op2Double = -op2Double
                            } else
                                op2Double = op2Double + temp2 / decimalFactor
                            textContent.text = "$op1Double ${
                                when (temp) {
                                    1 -> "+"
                                    2 -> "-"
                                    3 -> "x"
                                    4 -> "/"
                                    else -> ""
                                }
                            } $op2Double"
                        }
                    }
                    else {
                        if (op1 < 0) {
                            op1 = (-op1) * 10 + c
                            op1 = -op1
                            textContent.text = "$op1"
                        } else {
                            op1 = op1 * 10 + c
                            textContent.text = "$op1"
                        }
                    }
                }
                else -> {
                    op1 = op1 * 10 + c
                    textContent.text = "$op1"
                }
            }
        } else if(state == 2) {
            when (op) {
                5 -> {
                    if(temp4 == 0) {
                        decimalFactor *= 10.0
                        op2Double = op2Double + temp2 / decimalFactor
                        val temp1 = formatNumber(op2Double)
                        textContent.text = "$op1Double ${
                            when (temp) {
                                1 -> "+"
                                2 -> "-"
                                3 -> "x"
                                4 -> "/"
                                else -> ""
                            }
                        } $temp1"
                    } else {
                        decimalFactor *= 10.0
                        op2Double = (-op2Double) + temp2 / decimalFactor
                        op2Double = -op2Double
                        val temp1 = formatNumber(op2Double)
                        textContent.text = "$op1Double ${
                            when (temp4) {
                                1 -> "+"
                                2 -> "-"
                                3 -> "x"
                                4 -> "/"
                                else -> ""
                            }
                        } $temp1"
                    }
                }
                6 -> {
                    if(isDecimal) {
                        decimalFactor *= 10.0
                        if (op2Double < 0.0) {
                            op2Double = (-op2Double) + temp2 / decimalFactor
                            op2Double = -op2Double
                        } else
                            op2Double = op2Double + temp2 / decimalFactor
                        textContent.text = "$op1Double ${
                            when (temp) {
                                1 -> "+"
                                2 -> "-"
                                3 -> "x"
                                4 -> "/"
                                else -> "${
                                    when (temp4) {
                                        1 -> "+"
                                        2 -> "-"
                                        3 -> "x"
                                        4 -> "/"
                                        else -> ""
                                    }
                                }"
                            }
                        } $op2Double"
                    }
                    else {
                        if (op2 < 0) {
                            op2 = (-op2) * 10 + c
                            op2 = -op2
                            textContent.text = "$op1 ${
                                when (temp) {
                                    1 -> "+"
                                    2 -> "-"
                                    3 -> "x"
                                    4 -> "/"
                                    else -> ""
                                }
                            } $op2"
                        } else {
                            op2 = op2 * 10 + c
                            textContent.text = "$op1 ${
                                when (temp) {
                                    1 -> "+"
                                    2 -> "-"
                                    3 -> "x"
                                    4 -> "/"
                                    else -> ""
                                }
                            } $op2"
                        }
                    }
                }
                else -> {
                    op2 = op2 * 10 + c
                    if (isDecimal) {
                        op2Double = op2.toDouble()
                        textContent.text = "$op1Double ${
                            when (op) {
                                1 -> "+"
                                2 -> "-"
                                3 -> "x"
                                4 -> "/"
                                else -> ""
                            }
                        } $op2Double"
                    } else {
                        textContent.text = "$op1 ${
                            when (op) {
                                1 -> "+"
                                2 -> "-"
                                3 -> "x"
                                4 -> "/"
                                else -> ""
                            }
                        } $op2"
                    }
                }
            }
        }
    }

    fun backspace() {
        if (state == 1) {
            if (isDecimal) {
//                if(decimalFactor == 1.0) {
//
//                }
//                else {
                op1Double = (((op1Double * decimalFactor).toInt()) / 10).toDouble()
                decimalFactor /= 10
                op1Double /= decimalFactor
//                }

                textContent.text = formatNumber(op1Double)
            } else {
                op1 /= 10
                if (op1 == 0) {
                    textContent.text = "0"
                } else {
                    textContent.text = "$op1"
                }
            }
        } else if (state == 2) {
            if (isDecimal) {
                if(decimalFactor == 1.0) {
                    op2 = 0
                    op2Double = 0.0
                } else {
                    op2Double = (((op2Double * decimalFactor).toInt()) / 10).toDouble()
                    decimalFactor /= 10
                    op2Double /= decimalFactor
                }

                textContent.text = "$op1Double ${when (op) {
                    1 -> "+"
                    2 -> "-"
                    3 -> "x"
                    4 -> "/"
                    5 -> "${
                        when (temp) {
                            1 -> "+"
                            2 -> "-"
                            3 -> "x"
                            4 -> "/"
                            else -> ""
                        }
                    }"
                    else -> ""
                }} ${formatNumber(op2Double)}"
            } else {
                op2 /= 10
                if (op2 == 0) {
                    textContent.text = "$op1 ${when (op) {
                        1 -> "+"
                        2 -> "-"
                        3 -> "x"
                        4 -> "/"
                        else -> ""
                    }} 0"
                } else {
                    textContent.text = "$op1 ${when (op) {
                        1 -> "+"
                        2 -> "-"
                        3 -> "x"
                        4 -> "/"
                        else -> ""
                    }} $op2"
                }
            }
        }
    }

    fun toggleSign() {
        if (state == 1) {
            if(isDecimal) {
                op1Double = -op1Double
                textContent.text = "$op1Double"
            } else {
                op1 = -op1
                textContent.text = "$op1"
            }
        } else if (state == 2) {
            if(isDecimal) {
                op2Double = -op2Double
                if(temp4 == 0) {
                    textContent.text = "$op1Double ${
                        when (op) {
                            1 -> "+"
                            2 -> "-"
                            3 -> "x"
                            4 -> "/"
                            6 -> "${
                                when (temp) {
                                    1 -> "+"
                                    2 -> "-"
                                    3 -> "x"
                                    4 -> "/"
                                    else -> ""
                                }
                            }"

                            else -> ""
                        }
                    } $op2Double"
                } else {
                    textContent.text = "$op1Double ${
                        when (op) {
                            1 -> "+"
                            2 -> "-"
                            3 -> "x"
                            4 -> "/"
                            6 -> "${
                                when (temp4) {
                                    1 -> "+"
                                    2 -> "-"
                                    3 -> "x"
                                    4 -> "/"
                                    else -> ""
                                }
                            }"

                            else -> ""
                        }
                    } $op2Double"
                }
            } else {
                op2 = -op2
                textContent.text = "$op1 ${
                    when (temp) {
                        1 -> "+"
                        2 -> "-"
                        3 -> "x"
                        4 -> "/"
                        else -> ""
                    }
                } $op2"
            }
        }
    }

    fun addDecimalPoint() {
        if(state == 1) {
            op1Double = op1.toDouble()
            textContent.text = "$op1."
        }
        else {
            if(op1Double == 0.0)
                op1Double = op1.toDouble()
            if(temp4 == 0) {
                op2Double = op2.toDouble()
                textContent.text = "$op1Double ${
                    when (temp) {
                        1 -> "+"
                        2 -> "-"
                        3 -> "x"
                        4 -> "/"
                        else -> ""
                    }
                } $op2Double"
            } else {
                textContent.text = "$op1Double ${
                    when (temp4) {
                        1 -> "+"
                        2 -> "-"
                        3 -> "x"
                        4 -> "/"
                        else -> ""
                    }
                } $op2Double"
            }
        }
    }

    fun calculateResult() {
        val result: Int
        val ans: Double
        if (op == 5) {
            if (temp4 == 0)
                op = temp
            else
                op = temp4
        }
        if (op == 6) {
            if (temp4 == 0)
                op = temp
            else
                op = temp4
        }
        when (op) {
            1 -> {
                if (isDecimal) {
                    ans = op1Double + op2Double
                    textContent.text = "$op1Double + $op2Double = ${formatNumber(ans)}"
                    op1Double = ans
                } else {
                    result = op1 + op2
                    textContent.text = "$op1 + $op2 = $result"
                    op1 = result
                }
            }
            2 -> {
                if (isDecimal) {
                    ans = op1Double - op2Double
                    textContent.text = "$op1Double - $op2Double = ${formatNumber(ans)}"
                    op1Double = ans
                } else {
                    result = op1 - op2
                    textContent.text = "$op1 - $op2 = $result"
                    op1 = result
                }
            }
            3 -> {
                if (isDecimal) {
                    ans = op1Double * op2Double
                    textContent.text = "$op1Double x $op2Double = ${formatNumber(ans)}"
                    op1Double = ans
                } else {
                    result = op1 * op2
                    textContent.text = "$op1 x $op2 = $result"
                    op1 = result
                }
            }
            4 -> {
                if (isDecimal) {
                    if (op2 != 0) {
                        ans = op1Double / op2Double
                        textContent.text = "$op1Double / $op2Double = ${formatNumber(ans)}"
                        op1Double = ans
                    } else {
                        textContent.text = "Can't divide by zero"
                    }
                } else {
                    if (op2 != 0) {
                        if (op1 % op2 == 0) {
                            result = op1 / op2
                            textContent.text = "$op1 / $op2 = $result"
                            op1 = result
                        } else {
                            ans = op1.toDouble() / op2.toDouble()
                            textContent.text = "$op1 / $op2 = ${formatNumber(ans)}"
                            op1Double = ans
                            isDecimal = true
                        }
                    } else {
                        textContent.text = "Can't divide by zero"
                    }
                }
            }
            else -> return
        }
        op2 = 0
        op2Double = 0.0
        state = 1
        decimalFactor = 1.0
        temp = 0
        temp4 = 0
    }

    fun resetCalculator() {
        op1 = 0
        op2 = 0
        state = 1
        op = 0
        op1Double = 0.0
        op2Double = 0.0
        decimalFactor = 1.0
        isDecimal = false
        temp = 0
        temp4 = 0
    }

}