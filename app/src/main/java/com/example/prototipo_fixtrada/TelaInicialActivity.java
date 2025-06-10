package com.example.prototipo_fixtrada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.textfield.TextInputEditText;

public class TelaInicialActivity extends AppCompatActivity {

    private Button btLogin, btCadastro, btCliente, btPrestador;
    private TextInputEditText edUsuario, edSenha;
    private TextView txMensagem;
    private boolean isPrestador = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        btLogin = findViewById(R.id.btLogin); btLogin.setBackgroundTintList(null);
        btCadastro = findViewById(R.id.btCadastro); btCadastro.setBackgroundTintList(null);
        btCliente = findViewById(R.id.btCliente); btCliente.setBackgroundTintList(null);
        btPrestador = findViewById(R.id.btPrestador); btPrestador.setBackgroundTintList(null);
        edUsuario = findViewById(R.id.edUsuario);
        edSenha = findViewById(R.id.edSenha);
        txMensagem = findViewById(R.id.txMensagem);

        switchParaCliente();

        btCliente.setOnClickListener(v -> switchParaCliente());
        btPrestador.setOnClickListener(v -> switchParaPrestador());

        btLogin.setOnClickListener(v -> {
            if(validarCampos()) {
                fazerLogin();
            }
        });

        btCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(TelaInicialActivity.this, CadastroActivity.class);
            intent.putExtra("tipoUsuario", isPrestador ? "prestador" : "cliente");
            startActivity(intent);
        });
    }

    private void switchParaCliente() {
        isPrestador = false;

        btCliente.setBackgroundResource(R.drawable.button_selected);
        btPrestador.setBackgroundResource(R.drawable.button_static);

        animacaoBotao(btCliente, btPrestador);
    }

    private void switchParaPrestador() {
        isPrestador = true;

        btPrestador.setBackgroundResource(R.drawable.button_selected);

        btCliente.setBackgroundResource(R.drawable.button_static);
        animacaoBotao(btPrestador, btCliente);
    }

    private void animacaoBotao(Button selected, Button unselected) {
        selected.animate()
                .scaleX(1.15f)
                .scaleY(1.15f)
                .setDuration(200)
                .start();

        unselected.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(200)
                .start();
    }

    private boolean validarCampos() {
        String usuario = edUsuario.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();

        if(usuario.isEmpty()) {
            edUsuario.setError("Usuário é obrigatório");
            return false;
        }

        if(senha.isEmpty()) {
            edSenha.setError("Senha é obrigatória");
            return false;
        }

        return true;
    }

    private void fazerLogin() {
        // Implemente sua lógica de login aqui
        String tipoUsuario = isPrestador ? "Prestador" : "Cliente";
        txMensagem.setText("Login como " + tipoUsuario + " em desenvolvimento");
    }
}