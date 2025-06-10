package com.example.prototipo_fixtrada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

public class TelaInicialActivity extends AppCompatActivity {

    private Button btLogin, btCadastro, btCliente, btPrestador;
    private EditText edUsuario, edSenha;
    private TextView txMensagem;
    private boolean isPrestador = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        // Inicializa componentes
        btLogin = findViewById(R.id.btLogin);
        btCadastro = findViewById(R.id.btCadastro);
        btCliente = findViewById(R.id.btCliente);
        btPrestador = findViewById(R.id.btPrestador);
        edUsuario = findViewById(R.id.edUsuario);
        edSenha = findViewById(R.id.edSenha);
        txMensagem = findViewById(R.id.txMensagem);

        // Configura os botões de tipo de usuário
        btCliente.setOnClickListener(v -> switchParaCliente());
        btPrestador.setOnClickListener(v -> switchParaPrestador());

        // Por padrão, começa como cliente
        switchParaCliente();

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
        btCliente.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        btCliente.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        btPrestador.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200));
        btPrestador.setTextColor(ContextCompat.getColor(this, android.R.color.black));
    }

    private void switchParaPrestador() {
        isPrestador = true;
        btPrestador.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_700));
        btPrestador.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        btCliente.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_200));
        btCliente.setTextColor(ContextCompat.getColor(this, android.R.color.black));
    }

    private boolean validarCampos() {
        String usuario = edUsuario.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();

        if(usuario.isEmpty()) {
            mostrarErro(edUsuario, "Usuário é obrigatório");
            return false;
        }

        if(senha.isEmpty()) {
            mostrarErro(edSenha, "Senha é obrigatória");
            return false;
        }

        edUsuario.setError(null);
        edSenha.setError(null);
        txMensagem.setText("");
        return true;
    }

    private void fazerLogin() {
        String usuario = edUsuario.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();

        // Aqui você faria a chamada ao banco de dados
        if(isPrestador) {
            // Lógica para login de prestador
            loginPrestador(usuario, senha);
        } else {
            // Lógica para login de cliente
            loginCliente(usuario, senha);
        }
    }

    private void loginCliente(String usuario, String senha) {
        // Implemente a lógica de login para cliente
        // Exemplo fictício:
        if(verificarCredenciaisNoBanco(usuario, senha, "clientes")) {
            Intent intent = new Intent(TelaInicialActivity.this, MenuClienteActivity.class);
            startActivity(intent);
        } else {
            txMensagem.setText("Credenciais inválidas para cliente");
        }
    }

    private void loginPrestador(String usuario, String senha) {
        // Implemente a lógica de login para prestador
        // Exemplo fictício:
        if(verificarCredenciaisNoBanco(usuario, senha, "prestadores")) {
            Intent intent = new Intent(TelaInicialActivity.this, MenuPrestadorActivity.class);
            startActivity(intent);
        } else {
            txMensagem.setText("Credenciais inválidas para prestador");
        }
    }

    // Método fictício - substitua pela sua lógica real de banco de dados
    private boolean verificarCredenciaisNoBanco(String usuario, String senha, String tabela) {
        // Aqui você faria a consulta no banco de dados
        // Retornando true se as credenciais estiverem corretas
        return true; // Apenas para exemplo
    }

    private void mostrarErro(EditText campo, String mensagem) {
        campo.setError(mensagem);
        campo.requestFocus();
        txMensagem.setText(mensagem);
    }
}