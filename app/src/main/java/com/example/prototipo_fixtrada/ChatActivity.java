package com.example.prototipo_fixtrada;

<<<<<<< Updated upstream
import android.content.SharedPreferences;
=======
>>>>>>> Stashed changes
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< Updated upstream
import com.example.prototipo_fixtrada.construtores.Mensagem;

import java.text.SimpleDateFormat;
=======
import java.text.SimpleDateFormat;
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Mensagem> listaMensagens;
<<<<<<< Updated upstream
    private Banco banco;
    private String userCat;
=======
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

<<<<<<< Updated upstream
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userCat = sharedPreferences.getString("user_cat", "cliente");

        banco = new Banco(this);
        listaMensagens = banco.listarMensagens();

        chatAdapter = new ChatAdapter(listaMensagens);
=======
        listaMensagens = new ArrayList<>();
        carregarMensagensSimuladas();

        chatAdapter = new ChatAdapter(listaMensagens, "cliente");
>>>>>>> Stashed changes
        recyclerView.setAdapter(chatAdapter);

        EditText editMensagem = findViewById(R.id.editMensagem);
        Button btnEnviar = findViewById(R.id.btnEnviar);
<<<<<<< Updated upstream
        Button btnAvaliar = findViewById(R.id.btnAvaliar);
=======
>>>>>>> Stashed changes

        btnEnviar.setOnClickListener(v -> {
            String texto = editMensagem.getText().toString().trim();
            if (!texto.isEmpty()) {
                String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
<<<<<<< Updated upstream
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
=======
                Mensagem nova = new Mensagem("cliente", texto, hora);
                listaMensagens.add(nova);
                chatAdapter.notifyItemInserted(listaMensagens.size() - 1);
                recyclerView.scrollToPosition(listaMensagens.size() - 1);
                editMensagem.setText("");

                // Simulação: resposta automática
                recyclerView.postDelayed(() -> {
                    Mensagem resposta = new Mensagem("prestador", "Obrigado pela mensagem! Em breve responderemos.", hora);
                    listaMensagens.add(resposta);
                    chatAdapter.notifyItemInserted(listaMensagens.size() - 1);
                    recyclerView.scrollToPosition(listaMensagens.size() - 1);
                }, 1500);
            }
        });
    }

    private void carregarMensagensSimuladas() {
        String horaAtual = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        listaMensagens.add(new Mensagem("cliente", "Olá, estou com um problema no meu carro.", horaAtual));
        listaMensagens.add(new Mensagem("prestador", "Olá! Poderia me descrever o que está acontecendo?", horaAtual));
        listaMensagens.add(new Mensagem("cliente", "Está saindo muita fumaça do motor.", horaAtual));
        listaMensagens.add(new Mensagem("prestador", "Entendi. Podemos agendar uma visita técnica?", horaAtual));
        listaMensagens.add(new Mensagem("cliente", "Sim, pode ser hoje à tarde?", horaAtual));
>>>>>>> Stashed changes
    }
}
