package com.example.prototipo_fixtrada;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    private Button btCadCliente, btCadPrestador, btConfirmarCadastro;
    private TextInputEditText edNome, edEmail, edSenha, edConfirmarSenha, edCpf, edCnpj, edCep, edEndereco, edDataNasc;
    private TextInputEditText edModelo, edMarca, edPlaca, edCor, edAno, edKm;
    private TextInputLayout layoutCpf, layoutCnpj, layoutCep, layoutEndereco, layoutDataNasc, layoutNome;
    private TextInputLayout layoutModelo, layoutMarca, layoutPlaca, layoutCor, layoutAno, layoutKm;
    private TextView txMensagemCadastro;
    private boolean isPrestador = false;
    private Banco dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        dbHelper = new Banco(this);

        // Botões
        btCadCliente = findViewById(R.id.btCadCliente); btCadCliente.setBackgroundTintList(null);
        btCadPrestador = findViewById(R.id.btCadPrestador); btCadPrestador.setBackgroundTintList(null);
        btConfirmarCadastro = findViewById(R.id.btConfirmarCadastro); btConfirmarCadastro.setBackgroundTintList(null);

        // Campos comuns
        edNome = findViewById(R.id.edNome);
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        edConfirmarSenha = findViewById(R.id.edConfirmarSenha);
        edCpf = findViewById(R.id.edCpf);
        edCnpj = findViewById(R.id.edCnpj);
        edCep = findViewById(R.id.edCep);
        edEndereco = findViewById(R.id.edEndereco);
        edDataNasc = findViewById(R.id.edDataNasc);

        edModelo = findViewById(R.id.edModelo);
        edMarca = findViewById(R.id.edMarca);
        edPlaca = findViewById(R.id.edPlaca);
        edCor = findViewById(R.id.edCor);
        edAno = findViewById(R.id.edAno);
        edKm = findViewById(R.id.edKm);

        // Layouts
        layoutCpf = findViewById(R.id.layoutCpf);
        layoutCnpj = findViewById(R.id.layoutCnpj);
        layoutCep = findViewById(R.id.layoutCep);
        layoutEndereco = findViewById(R.id.layoutEndereco);
        layoutDataNasc = findViewById(R.id.layoutDataNasc);
        layoutNome = findViewById(R.id.layoutNome);

        layoutModelo = findViewById(R.id.layoutModelo);
        layoutMarca = findViewById(R.id.layoutMarca);
        layoutPlaca = findViewById(R.id.layoutPlaca);
        layoutCor = findViewById(R.id.layoutCor);
        layoutAno = findViewById(R.id.layoutAno);
        layoutKm = findViewById(R.id.layoutKm);

        txMensagemCadastro = findViewById(R.id.txMensagemCadastro);

        // Máscaras e ações
        mascaraCpf();
        mascaraCnpj();
        mascaraDataNasc();
        configurarCepListener();
        switchParaCliente();

        btCadCliente.setOnClickListener(v -> switchParaCliente());
        btCadPrestador.setOnClickListener(v -> switchParaPrestador());

        btConfirmarCadastro.setOnClickListener(v -> {
            if (validarCampos()) {
                cadastrarUsuario();
            }
        });
    }

    private void switchParaCliente() {
        isPrestador = false;
        btCadCliente.setBackgroundResource(R.drawable.button_selected);
        btCadPrestador.setBackgroundResource(R.drawable.button_static);
        TelaInicialActivity.animacaoBotao(btCadCliente, btCadPrestador);

        layoutCpf.setVisibility(View.VISIBLE);
        layoutCnpj.setVisibility(View.GONE);
        layoutCep.setVisibility(View.GONE);
        layoutEndereco.setVisibility(View.GONE);
        layoutDataNasc.setVisibility(View.VISIBLE);
        layoutNome.setHint("Nome Completo");

        layoutModelo.setVisibility(View.VISIBLE);
        layoutMarca.setVisibility(View.VISIBLE);
        layoutPlaca.setVisibility(View.VISIBLE);
        layoutCor.setVisibility(View.VISIBLE);
        layoutAno.setVisibility(View.VISIBLE);
        layoutKm.setVisibility(View.VISIBLE);
    }

    private void switchParaPrestador() {
        isPrestador = true;
        btCadPrestador.setBackgroundResource(R.drawable.button_selected);
        btCadCliente.setBackgroundResource(R.drawable.button_static);
        TelaInicialActivity.animacaoBotao(btCadPrestador, btCadCliente);

        layoutCpf.setVisibility(View.GONE);
        layoutCnpj.setVisibility(View.VISIBLE);
        layoutCep.setVisibility(View.VISIBLE);
        layoutEndereco.setVisibility(View.VISIBLE);
        layoutDataNasc.setVisibility(View.GONE);
        layoutNome.setHint("Nome da Mecânica");

        layoutModelo.setVisibility(View.GONE);
        layoutMarca.setVisibility(View.GONE);
        layoutPlaca.setVisibility(View.GONE);
        layoutCor.setVisibility(View.GONE);
        layoutAno.setVisibility(View.GONE);
        layoutKm.setVisibility(View.GONE);
    }

    private boolean validarCampos() {
        boolean valido = true;

        String nome = edNome.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();
        String confirmarSenha = edConfirmarSenha.getText().toString().trim();
        String documento = isPrestador ? edCnpj.getText().toString().trim() : edCpf.getText().toString().trim();
        String cep = isPrestador ? edCep.getText().toString().trim() : "";
        String endereco = isPrestador ? edEndereco.getText().toString().trim() : "";
        String dataNasc = edDataNasc.getText().toString().trim();

        if (nome.isEmpty()) {
            edNome.setError("Preencha o nome");
            valido = false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Email inválido, coloque o domínio (ex. @gmail.com)");
            valido = false;
        }
        if (senha.isEmpty() || senha.length() < 6) {
            edSenha.setError("Senha deve ter ao menos 6 caracteres");
            valido = false;
        }
        if (!senha.equals(confirmarSenha)) {
            edConfirmarSenha.setError("As senhas não coincidem");
            valido = false;
        }

        if (isPrestador) {
            if (documento.isEmpty() || documento.replaceAll("[^\\d]", "").length() != 14) {
                edCnpj.setError("CNPJ inválido");
                valido = false;
            }
            if (cep.isEmpty() || cep.length() < 8) {
                edCep.setError("Informe um CEP válido");
                valido = false;
            }
            if (endereco.isEmpty()) {
                edEndereco.setError("Endereço não encontrado");
                valido = false;
            }
        } else {
            if (documento.isEmpty() || documento.replaceAll("[^\\d]", "").length() != 11) {
                edCpf.setError("CPF inválido");
                valido = false;
            }
            if (dataNasc.isEmpty() || !dataNasc.matches("\\d{2}/\\d{2}/\\d{4}")) {
                edDataNasc.setError("Data inválida (DD/MM/AAAA)");
                valido = false;
            }
            if (edModelo.getText().toString().trim().isEmpty()) {
                edModelo.setError("Informe o modelo");
                valido = false;
            }
            if (edMarca.getText().toString().trim().isEmpty()) {
                edMarca.setError("Informe a marca");
                valido = false;
            }
            if (edPlaca.getText().toString().trim().length() < 6) {
                edPlaca.setError("Placa inválida");
                valido = false;
            }
            if (edCor.getText().toString().trim().isEmpty()) {
                edCor.setError("Informe a cor");
                valido = false;
            }
            if (edAno.getText().toString().trim().isEmpty()) {
                edAno.setError("Ano inválido");
                valido = false;
            }
            if (edKm.getText().toString().trim().isEmpty()) {
                edKm.setError("Informe a quilometragem");
                valido = false;
            }
        }
        return valido;
    }

    private void cadastrarUsuario() {
        String nome = edNome.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();
        String documento = isPrestador ? edCnpj.getText().toString().trim() : edCpf.getText().toString().trim();
        String cep = isPrestador ? edCep.getText().toString().trim() : "";
        String endereco = isPrestador ? edEndereco.getText().toString().trim() : "";
        String dataNasc = isPrestador ? "" : edDataNasc.getText().toString().trim();

        long idUsuario;
        if (isPrestador) {
            idUsuario = dbHelper.inserirPrestador(nome, email, senha, documento, cep, endereco);
        } else {
            idUsuario = dbHelper.inserirCliente(nome, email, senha, documento, dataNasc);
            if (idUsuario != -1) {
                dbHelper.inserirVeiculo(
                        edModelo.getText().toString().trim(),
                        edMarca.getText().toString().trim(),
                        edPlaca.getText().toString().trim(),
                        edCor.getText().toString().trim(),
                        Integer.parseInt(edAno.getText().toString().trim()),
                        Integer.parseInt(edKm.getText().toString().trim()),
                        (int) idUsuario
                );
            }
        }

        if (idUsuario != -1) {
            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            txMensagemCadastro.setText("Erro ao cadastrar. Tente novamente.");
        }
    }

    private void configurarCepListener() {
        edCep.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                String cep = s.toString().replaceAll("[^\\d]", "");
                if (cep.length() == 8) {
                    buscarEnderecoViaCep(cep);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void buscarEnderecoViaCep(String cep) {
        new Thread(() -> {
            try {
                java.net.URL url = new java.net.URL("https://viacep.com.br/ws/" + cep + "/json/");
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();

                java.io.InputStream inputStream = conn.getInputStream();
                java.util.Scanner scanner = new java.util.Scanner(inputStream);
                StringBuilder result = new StringBuilder();
                while (scanner.hasNext()) result.append(scanner.nextLine());
                scanner.close();

                org.json.JSONObject obj = new org.json.JSONObject(result.toString());

                if (!obj.has("erro")) {
                    String enderecoCompleto = obj.optString("logradouro") + ", " +
                            obj.optString("bairro") + " - " +
                            obj.optString("localidade") + "/" +
                            obj.optString("uf");

                    runOnUiThread(() -> edEndereco.setText(enderecoCompleto));
                } else {
                    runOnUiThread(() -> edEndereco.setText(""));
                }

                conn.disconnect();
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }

    private void mascaraDataNasc() {
        edDataNasc.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            private final String mask = "##/##/####";
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) { isUpdating = false; return; }
                String digitsOnly = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#') formatted.append(m);
                    else if (i < digitsOnly.length()) formatted.append(digitsOnly.charAt(i++));
                }
                isUpdating = true;
                edDataNasc.setText(formatted.toString());
                edDataNasc.setSelection(formatted.length());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void mascaraCpf() {
        edCpf.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) { isUpdating = false; return; }
                String digits = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();
                int i = 0;
                while (i < digits.length() && i < 11) {
                    if (i == 3 || i == 6) formatted.append(".");
                    else if (i == 9) formatted.append("-");
                    formatted.append(digits.charAt(i++));
                }
                isUpdating = true;
                edCpf.setText(formatted.toString());
                edCpf.setSelection(formatted.length());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void mascaraCnpj() {
        edCnpj.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) { isUpdating = false; return; }
                String digits = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();
                int i = 0;
                while (i < digits.length() && i < 14) {
                    if (i == 2 || i == 5) formatted.append(".");
                    else if (i == 8) formatted.append("/");
                    else if (i == 12) formatted.append("-");
                    formatted.append(digits.charAt(i++));
                }
                isUpdating = true;
                edCnpj.setText(formatted.toString());
                edCnpj.setSelection(formatted.length());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
