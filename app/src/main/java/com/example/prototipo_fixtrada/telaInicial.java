package com.example.prototipo_fixtrada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class telaInicial extends AppCompatActivity {

    private Button btLogin, btCadastro;
    private EditText edUsuario, edSenha;
    private TextView txMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        btLogin = findViewById(R.id.btLogin);
        btCadastro = findViewById(R.id.btCadastro);
        edUsuario = findViewById(R.id.edUsuario);
        edSenha = findViewById(R.id.edSenha);
        txMensagem = findViewById(R.id.txMensagem);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()) {
                    Intent intent = new Intent(telaInicial.this, LoginActivity.class);
                    intent.putExtra("usuario", edUsuario.getText().toString());
                    startActivity(intent);
                }
            }
        });
//quarenta
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaInicial.this, ChamadaServicoActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validarCampos() {
        String usuario = edUsuario.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();

        if(usuario.isEmpty()) {
            txMensagem.setText("Por favor, informe o usuário!");
            edUsuario.requestFocus();
            return false;
        }
        if(senha.isEmpty()) {
            txMensagem.setText("Por favor, informe a senha!");
            edSenha.requestFocus();
            return false;
        }
        txMensagem.setText("");
        return true;
    }
}