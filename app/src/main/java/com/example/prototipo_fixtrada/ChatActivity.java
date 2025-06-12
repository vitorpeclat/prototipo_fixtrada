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

        recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userCat = sharedPreferences.getString("user_cat", null);

        listaMensagens = new ArrayList<>();
        carregarMensagensSimuladas(userCat);

        chatAdapter = new ChatAdapter(listaMensagens, "cliente");
        recyclerView.setAdapter(chatAdapter);

        EditText editMensagem = findViewById(R.id.editMensagem);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> {
            String texto = editMensagem.getText().toString().trim();
            if (!texto.isEmpty()) {
                String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Mensagem nova = new Mensagem("cliente", texto, hora);
                listaMensagens.add(nova);
                chatAdapter.notifyItemInserted(listaMensagens.size() - 1);
                recyclerView.scrollToPosition(listaMensagens.size() - 1);
                editMensagem.setText("");

                recyclerView.postDelayed(() -> {
                    if (userCat == "prestador"){
                        Mensagem resposta = new Mensagem("prestador", "ok", hora);
                        listaMensagens.add(resposta);
                    }
                    else{
                        Mensagem resposta = new Mensagem("prestador", "Obrigado pela mensagem! Em breve responderemos.", hora);
                        listaMensagens.add(resposta);
                    }
                    chatAdapter.notifyItemInserted(listaMensagens.size() - 1);
                    recyclerView.scrollToPosition(listaMensagens.size() - 1);
                }, 1500);
            }
        });
    }

    private void carregarMensagensSimuladas(String userCat) {
        if (userCat == "prestador"){
            String horaAtual = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            listaMensagens.add(new Mensagem("prestador", "Olá, estou com um problema no meu carro.", horaAtual));
            listaMensagens.add(new Mensagem("cliente", "Olá! Poderia me descrever o que está acontecendo?", horaAtual));
            listaMensagens.add(new Mensagem("prestador", "Está saindo muita fumaça do motor.", horaAtual));
            listaMensagens.add(new Mensagem("cliente", "Entendi. Podemos agendar uma visita técnica?", horaAtual));
            listaMensagens.add(new Mensagem("prestador", "Sim, pode ser hoje à tarde?", horaAtual));
            listaMensagens.add(new Mensagem("prestador", "estou aguardando", horaAtual));
            listaMensagens.add(new Mensagem("cliente", "Obrigado pela mensagem! Em breve responderemos.", horaAtual));
        }
        else {
            String horaAtual = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            listaMensagens.add(new Mensagem("cliente", "Olá, estou com um problema no meu carro.", horaAtual));
            listaMensagens.add(new Mensagem("prestador", "Olá! Poderia me descrever o que está acontecendo?", horaAtual));
            listaMensagens.add(new Mensagem("cliente", "Está saindo muita fumaça do motor.", horaAtual));
            listaMensagens.add(new Mensagem("prestador", "Entendi. Podemos agendar uma visita técnica?", horaAtual));
            listaMensagens.add(new Mensagem("cliente", "Sim, pode ser hoje à tarde?", horaAtual));
        }
    }
}
