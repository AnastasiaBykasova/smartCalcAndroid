package com.example.calculate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate.databinding.MetresBinding;

import java.text.DecimalFormat;

public class CalcMetres extends AppCompatActivity {
    private MetresBinding binding;
    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MetresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        binding.enterMetres.setText(result);

        binding.btnConvert.setEnabled(false);

        binding.enterMetres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButtonAvailability();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        RadioGroup radioGroup = binding.radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkButtonAvailability();
            }
        });

        binding.btnConvert.setOnClickListener(view -> {
            if (isNumeric(getMetres())) {
                double metres = Double.parseDouble(getMetres());
                binding.resultText.setText("Результат (в погонных метрах):");
                binding.resultCalc.setText(df.format(startCalculations(getWidth(), metres)));
                Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intentCalcSimple = new Intent(this, CalcSimple.class);
        binding.btnCalcSimple.setOnClickListener(view -> {
            if (binding.switchRes.isChecked()) {
                String res = binding.resultCalc.getText().toString().replace(",", ".");
                intentCalcSimple.putExtra("result", res);
            }
            startActivity(intentCalcSimple);
        });

        binding.btnCancel.setOnClickListener(view -> {
            finish();
        });

    }

    private void checkButtonAvailability() {
        String enteredMetres = binding.enterMetres.getText().toString();
        binding.btnConvert.setEnabled(!enteredMetres.isEmpty() && binding.radioGroup.getCheckedRadioButtonId() != -1);
    }

    private String getMetres() {
        String res_str = binding.enterMetres.getText().toString();
        if (res_str.contains(",")) {
            res_str = res_str.replace(",", ".");
        }
        return res_str;
    }

    private double getWidth() {
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = binding.getRoot().findViewById(selectedId);
        return Double.parseDouble(selectedRadioButton.getText().toString());
    }


    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private double startCalculations(double width, double metres) {
        return metres / width;
    }
}
