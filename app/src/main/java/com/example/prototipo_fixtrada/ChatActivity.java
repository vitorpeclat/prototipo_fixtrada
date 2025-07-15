package com.example.prototipo_fixtrada;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo_fixtrada.construtores.Mensagem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Mensagem> listaMensagens;
    private Banco banco;
    private String userCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userCat = sharedPreferences.getString("user_cat", "cliente");

        banco = new Banco(this);
        listaMensagens = banco.listarMensagens();

        chatAdapter = new ChatAdapter(listaMensagens, userCat);
        recyclerView.setAdapter(chatAdapter);

        EditText editMensagem = findViewById(R.id.editMensagem);
        Button btnEnviar = findViewById(R.id.btnEnviar);
        Button btnAvaliar = findViewById(R.id.btnAvaliar);

        btnEnviar.setOnClickListener(v -> {
            String texto = editMensagem.getText().toString().trim();
            if (!texto.isEmpty()) {
                String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Mensagem nova = new Mensagem(userCat, texto, hora);

                listaMensagens.add(nova);
                chatAdapter.notifyItemInserted(listaMensagens.size() - 1);
                recyclerView.scrollToPosition(listaMensagens.size() - 1);

                banco.salvarMensagem(userCat, texto, hora);
                editMensagem.setText("");
            }
        });

        btnAvaliar.setOnClickListener(v -> {
            // Implementar ação de avaliação aqui, se necessário
        });
    }
}
