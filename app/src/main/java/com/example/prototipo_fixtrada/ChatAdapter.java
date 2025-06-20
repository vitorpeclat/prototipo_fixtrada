package com.example.prototipo_fixtrada;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo_fixtrada.construtores.Mensagem;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Mensagem> lista;
    private String userCat;
    public ChatAdapter(List<Mensagem> lista, String userCat) {
        this.lista = lista;
        this.userCat = userCat;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMensagemEnviada, txtMensagemRecebida, txtHora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMensagemEnviada = itemView.findViewById(R.id.txtMensagemEnviada);
            txtMensagemRecebida = itemView.findViewById(R.id.txtMensagemRecebida);
            txtHora = itemView.findViewById(R.id.txtHoraMensagem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mensagem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Mensagem msg = lista.get(position);
        holder.txtHora.setText(msg.getHorario());

        if (msg.getRemetente().equalsIgnoreCase(userCat)) {
            holder.txtMensagemRecebida.setText(msg.getTexto());
            holder.txtMensagemRecebida.setVisibility(View.VISIBLE);
            holder.txtMensagemEnviada.setVisibility(View.GONE);
        } else {
            holder.txtMensagemEnviada.setText(msg.getTexto());
            holder.txtMensagemEnviada.setVisibility(View.VISIBLE);
            holder.txtMensagemRecebida.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}

