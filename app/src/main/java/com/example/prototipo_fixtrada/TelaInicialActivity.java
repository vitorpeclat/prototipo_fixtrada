package com.example.prototipo_fixtrada;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipo_fixtrada.construtores.Cliente;
import com.google.android.material.textfield.TextInputEditText;

public class TelaInicialActivity extends AppCompatActivity {

    private Button btLogin, btCadastro, btCliente, btPrestador;
    private TextInputEditText edUsuario, edSenha;
    private TextView txMensagem;
    private boolean isPrestador = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

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

    public static void animacaoBotao(Button selected, Button unselected) {
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
            edUsuario.setError("Usuário inválido");
            return false;
        }

        if(senha.isEmpty()) {
            edSenha.setError("Senha inválida");
            return false;
        }

        return true;
    }

    private void fazerLogin() {
        String email = edUsuario.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();

        Banco banco = new Banco(this);

        if (isPrestador) {
            boolean valido = banco.checkUserPrestador(email, senha);
            if (valido) {
                PrestadorServico prestador = banco.buscarPrestadorPorEmailSenha(email, senha);
                if (prestador != null) {
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("tipo", "prestador");
                    editor.putInt("id", prestador.getPreId());
                    editor.apply();

                    Intent intent = new Intent(TelaInicialActivity.this, MenuClienteActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                txMensagem.setText("Credenciais inválidas para Prestador");
            }
        } else {
            boolean valido = banco.checkUserCliente(email, senha);
            if (valido) {
                Cliente cliente = banco.buscarClientePorEmailSenha(email, senha);
                if (cliente != null) {
                    SharedPreferences prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("tipo", "cliente");
                    editor.putInt("id", cliente.getCliId());
                    editor.apply();

                    Intent intent = new Intent(TelaInicialActivity.this, MenuClienteActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                txMensagem.setText("Credenciais inválidas para Cliente");
            }
        }
    }
}