package com.example.prototipo_fixtrada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Mensagem> mensagens;
    private final String usuarioAtual;

    public ChatAdapter(List<Mensagem> mensagens, String usuarioAtual) {
        this.mensagens = mensagens;
        this.usuarioAtual = usuarioAtual;
    }

    @Override
    public int getItemViewType(int position) {
        Mensagem msg = mensagens.get(position);
        return msg.getRemetente().equals(usuarioAtual) ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem_cliente, parent, false);
            return new EnviadaViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem_prestador, parent, false);
            return new RecebidaViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Mensagem mensagem = mensagens.get(position);

        if (holder.getItemViewType() == 0) {
            ((EnviadaViewHolder) holder).bind(mensagem);
        } else {
            ((RecebidaViewHolder) holder).bind(mensagem);
        }
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    static class EnviadaViewHolder extends RecyclerView.ViewHolder {
        TextView txtMensagem, txtHora;

        EnviadaViewHolder(View itemView) {
            super(itemView);
            txtMensagem = itemView.findViewById(R.id.txtMensagemCliente);
            txtHora = itemView.findViewById(R.id.txtHoraCliente);
        }

        void bind(Mensagem mensagem) {
            txtMensagem.setText(mensagem.getTexto());
            txtHora.setText(mensagem.getHorario());
        }
    }

    static class RecebidaViewHolder extends RecyclerView.ViewHolder {
        TextView txtMensagem, txtHora;

        RecebidaViewHolder(View itemView) {
            super(itemView);
            txtMensagem = itemView.findViewById(R.id.txtMensagemPrestador);
            txtHora = itemView.findViewById(R.id.txtHoraPrestador);
        }

        void bind(Mensagem mensagem) {
            txtMensagem.setText(mensagem.getTexto());
            txtHora.setText(mensagem.getHorario());
        }
    }
}