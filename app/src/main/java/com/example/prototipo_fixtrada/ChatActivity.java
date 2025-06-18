package com.example.prototipo_fixtrada;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo_fixtrada.construtores.Mensagem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Mensagem> listaMensagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Banco banco = new Banco(this);

        listaMensagens = banco.listarMensagens();

        recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userCat = sharedPreferences.getString("user_cat", null);

        chatAdapter = new ChatAdapter(listaMensagens, "cliente");
        recyclerView.setAdapter(chatAdapter);

        EditText editMensagem = findViewById(R.id.editMensagem);
        Button btnEnviar = findViewById(R.id.btnEnviar);
        Button btnAvaliar = findViewById(R.id.btnAvaliar);

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEnviar.setOnClickListener(v -> {
            String texto = editMensagem.getText().toString().trim();
            if (!texto.isEmpty()) {
                String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Mensagem nova = new Mensagem(userCat, texto, hora);
                listaMensagens.add(nova);
                chatAdapter.notifyItemInserted(listaMensagens.size() - 1);
                recyclerView.scrollToPosition(listaMensagens.size() - 1);
                editMensagem.setText("");

                banco.salvarMensagem(nova.getRemetente(), nova.getTexto(), nova.getHorario());
            }
        });
    }
}
