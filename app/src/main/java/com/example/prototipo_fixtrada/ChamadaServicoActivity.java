package com.example.prototipo_fixtrada;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChamadaServicoActivity extends AppCompatActivity {

    private Spinner spinnerVeiculos;
    private EditText editDescricao;
    private Button btnEnviarChamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada_servico);

        spinnerVeiculos = findViewById(R.id.spinnerVeiculos);
        editDescricao = findViewById(R.id.editDescricao);
        btnEnviarChamada = findViewById(R.id.btnEnviarChamada);

        carregarVeiculos();

        btnEnviarChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String veiculoSelecionado = spinnerVeiculos.getSelectedItem().toString();
                String descricao = editDescricao.getText().toString().trim();

                if (descricao.isEmpty()) {
                    Toast.makeText(ChamadaServicoActivity.this, "Descreva o problema antes de enviar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Simulação de envio de chamada (aqui entraria a lógica com SQLite, API, etc.)
                Toast.makeText(ChamadaServicoActivity.this, "Chamada enviada para: " + veiculoSelecionado, Toast.LENGTH_LONG).show();

                editDescricao.setText(""); // limpa campo
                spinnerVeiculos.setSelection(0); // reinicia spinner
            }
        });
    }

    private void carregarVeiculos() {
        // Simulação: Lista de veículos cadastrados. Substituir por dados do banco se necessário.
        List<String> listaVeiculos = new ArrayList<>();
        listaVeiculos.add("Selecione um veículo...");
        listaVeiculos.add("Fiat Uno - ABC1234");
        listaVeiculos.add("Gol G5 - DEF5678");
        listaVeiculos.add("Civic - GHI9012");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaVeiculos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVeiculos.setAdapter(adapter);
    }
}
