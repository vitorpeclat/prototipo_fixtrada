package com.example.prototipo_fixtrada;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import com.example.prototipo_fixtrada.construtores.PrestadorServico;

public class PopupAvaliacao extends Dialog {

    public PopupAvaliacao(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_avaliacao);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(v -> {
            float nota = ratingBar.getRating();
            Banco banco = new Banco(getContext());
            int atualizados = 0;

            for (PrestadorServico p : banco.listarPrestadoresComEndereco()) {
                banco.inserirNota(p.getPreNome(), nota);
                atualizados++;
                Toast.makeText(getContext(), "Nota " + nota + " atribuída a: " + p.getPreNome(), Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });
    }
}
