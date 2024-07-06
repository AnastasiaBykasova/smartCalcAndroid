package com.example.calculate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.calculate.databinding.SimpleCalcBinding;
import java.text.DecimalFormat;
import java.util.Arrays;

public class CalcSimple extends AppCompatActivity {
    private SimpleCalcBinding binding;
    private final Character[] operations = {'-', '+', '*', '/', '.', '^'};

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SimpleCalcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        binding.calcText.setText(result);

        binding.calc0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "0");
            }
        });

        binding.calc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "1");
            }
        });

        binding.calc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "2");
            }
        });

        binding.calc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "3");
            }
        });

        binding.calc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "4");
            }
        });

        binding.calc5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "5");
            }
        });

        binding.calc6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "6");
            }
        });

        binding.calc7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "7");
            }
        });

        binding.calc8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "8");
            }
        });

        binding.calc9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalcText(getString(), "9");
            }
        });

        binding.calcSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getString().isEmpty() && checkPreviousOperation()) {
                    updateCalcText(getString(), "+");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calcSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getString().isEmpty() && checkPreviousOperation()) {
                    updateCalcText(getString(), "-");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calcMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getString().isEmpty() && checkPreviousOperation()) {
                    updateCalcText(getString(), "*");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calcDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getString().isEmpty() && checkPreviousOperation()) {
                    updateCalcText(getString(), "/");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calcPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getString().isEmpty() && checkPreviousOperation()) {
                    updateCalcText(getString(), "^");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calcPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getString().isEmpty() && checkPreviousOperation() && !isCurrentDecimal()) {
                    updateCalcText(getString(), ".");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calcClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });

        binding.calcClearLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLast();
            }
        });


        binding.calcRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = getString();
                if (!string.isEmpty() && checkPreviousOperation()) {
                    RPNCalculator calculator = new RPNCalculator();
                    String resultStr;
                    try {
                        if (checkHasOperation()) {
                            resultStr = calculator.convertToRPN(string);
                            double resultDouble = calculator.performOperation(resultStr);
                            String resultString = df.format(resultDouble);
                            resultString = resultString.replace(",", ".");
                            updateCalcText("", resultString);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Введите операцию!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ArithmeticException e) {
                        updateCalcText("", "Ошибка: Деление на ноль");
                    } catch (IllegalArgumentException e) {
                        updateCalcText("", "Ошибка: Неправильный оператор");
                    } catch (Exception e) {
                        updateCalcText("", "Ошибка: " + e.getClass().getSimpleName());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intentCalcMetres = new Intent(this, CalcMetres.class);
        binding.btnCalcMetres.setOnClickListener(view -> {
            if (binding.switchRes.isChecked()) {
                String res = binding.calcText.getText().toString().replace(",", ".");
                intentCalcMetres.putExtra("result", res);
            }
            startActivity(intentCalcMetres);
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getString() {
        return binding.calcText.getText().toString();
    }

    private void updateCalcText(String first, String text) {
        String string = first + text;
        binding.calcText.setText(string);
    }

    private boolean checkPreviousOperation() {
        String string = getString().trim();
        char lastChar = string.charAt(string.length() - 1);
        return !Arrays.asList(operations).contains(lastChar);
    }

    private boolean isCurrentDecimal() {
        String str = getString();
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '.' || c == ',') {
                return true;
            }
            else if (Arrays.asList(operations).contains(c)) {
                return false;
            }
        }
        return false;
    }

    private boolean checkHasOperation() {
        String str = getString();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Arrays.asList(operations).contains(c)) {
                return true;
            }
        }
        return false;
    }

    private void clearLast() {
        String string = getString();
        if (!string.isEmpty()) {
            string = string.substring(0, string.length() - 1);
            binding.calcText.setText(string);
        }
    }

    private void clearAll() {
        updateCalcText("", "");
    }

}