package com.example.prototipo_fixtrada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo_fixtrada.construtores.PrestadorServico;

import java.util.List;

public class MecanicoAdapter extends RecyclerView.Adapter<MecanicoAdapter.MecanicoViewHolder> {

    private List<PrestadorServico> lista;

    public MecanicoAdapter(List<PrestadorServico> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public MecanicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mecanico, parent, false);
        return new MecanicoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MecanicoViewHolder holder, int position) {
        PrestadorServico p = lista.get(position);
        holder.tvNome.setText(p.getPreNome());
        holder.tvEspecialidade.setText(p.getPreEndereco()); // ou p.getEspecialidade()
        holder.tvInicial.setText(p.getPreNome().substring(0, 1).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MecanicoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvEspecialidade, tvInicial;

        MecanicoViewHolder(View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvEspecialidade = itemView.findViewById(R.id.tvEspecialidade);
            tvInicial = itemView.findViewById(R.id.tvInicial);
        }
    }
}


