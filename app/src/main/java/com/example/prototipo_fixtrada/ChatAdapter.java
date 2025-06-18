package com.example.prototipo_fixtrada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
<<<<<<< Updated upstream

import com.example.prototipo_fixtrada.construtores.Mensagem;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Mensagem> lista;

    public ChatAdapter(List<Mensagem> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMensagemEnviada, txtMensagemRecebida, txtHora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMensagemEnviada = itemView.findViewById(R.id.txtMensagemEnviada);
            txtMensagemRecebida = itemView.findViewById(R.id.txtMensagemRecebida);
            txtHora = itemView.findViewById(R.id.txtHoraMensagem);
        }
=======
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
>>>>>>> Stashed changes
    }

    @NonNull
    @Override
<<<<<<< Updated upstream
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mensagem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mensagem msg = lista.get(position);
        holder.txtHora.setText(msg.getHorario());

        if (msg.getRemetente().equalsIgnoreCase("cliente")) {
            holder.txtMensagemEnviada.setText(msg.getTexto());
            holder.txtMensagemEnviada.setVisibility(View.VISIBLE);
            holder.txtMensagemRecebida.setVisibility(View.GONE);
        } else {
            holder.txtMensagemRecebida.setText(msg.getTexto());
            holder.txtMensagemRecebida.setVisibility(View.VISIBLE);
            holder.txtMensagemEnviada.setVisibility(View.GONE);
=======
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
>>>>>>> Stashed changes
        }
    }

    @Override
    public int getItemCount() {
<<<<<<< Updated upstream
        return lista.size();
    }
}

=======
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
>>>>>>> Stashed changes
