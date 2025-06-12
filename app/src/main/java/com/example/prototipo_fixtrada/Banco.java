package com.example.prototipo_fixtrada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prototipo_fixtrada.construtores.Mensagem;
import com.example.prototipo_fixtrada.construtores.PrestadorServico;

import java.util.ArrayList;
import java.util.List;

public class Banco extends SQLiteOpenHelper {
    private static final String BANCO = "fixtrada.db";
    private static final int VERSAO = 2;
    //tabela cliente
    public static final String TABELA_CLIENTE = "cliente";
    public static final String COLUNA_CLIID = "cliId";
    public static final String COLUNA_CLINOME = "cliNome";
    public static final String COLUNA_CLIEMAIL = "cliEmail";
    public static final String COLUNA_CLISENHA = "cliSenha";
    public static final String COLUNA_CLICPF = "cliCpf";
    public static final String COLUNA_CLIDATANASC = "cliDataNasc";
    //tabela prestadorSevico
    public static final String TABELA_PRESTADORSERVICO = "prestadorSevico";
    public static final String COLUNA_PREID = "preId";
    public static final String COLUNA_PRENOME = "preNome";
    public static final String COLUNA_PREEMAIL = "preEmail";
    public static final String COLUNA_PREENDERECO = "preEndereco";
    public static final String COLUNA_PRENOTA = "preNota";
    public static final String COLUNA_PRESENHA = "preSenha";
    public static final String COLUNA_PRECNPJ = "preCnpj";
    //tabela veiculo
    public static final String TABELA_VEICULO = "veiculo";
    public static final String COLUNA_VEIID = "veiId";
    public static final String COLUNA_VEIMODELO = "veiModelo";
    public static final String COLUNA_VEIMARCA = "veiMarca";
    public static final String COLUNA_VEIPLACA = "veiPlaca";
    public static final String COLUNA_VEICOR = "veiCor";
    public static final String COLUNA_VEIANO = "veiAno";
    public static final String COLUNA_VEIKM = "veiKm";
    public static final String COLUNA_VEICLIID = "veiClieId";
    //tabela mensagem
    public static final String TABELA_MENSAGEM = "mensagem";
    public static final String COLUNA_MENID = "menId";
    public static final String COLUNA_MENCONTEUDO = "menConteudo";
    public static final String COLUNA_MENDESTINATARIO = "menDestinatario";
    public static final String COLUNA_MENREMENTENTE = "menRemetente";
    public static final String COLUNA_MENHORARIO = "menHorario";

    public Banco(Context context) {
        super(context, BANCO, null, VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tabela cliente
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_CLIENTE + " ("
                + COLUNA_CLIID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_CLINOME + " TEXT NOT NULL, "
                + COLUNA_CLIEMAIL + " TEXT NOT NULL, "
                + COLUNA_CLISENHA + " TEXT NOT NULL, "
                + COLUNA_CLICPF + " TEXT NOT NULL, "
                + COLUNA_CLIDATANASC + " TEXT NOT NULL);"
        );
        // Tabela prestadorSevico
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_PRESTADORSERVICO + " ("
                + COLUNA_PREID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_PRENOME + " TEXT NOT NULL, "
                + COLUNA_PREEMAIL + " TEXT NOT NULL, "
                + COLUNA_PREENDERECO + " TEXT NOT NULL, "
                + COLUNA_PRENOTA + " REAL, "
                + COLUNA_PRESENHA + " TEXT NOT NULL, "
                + COLUNA_PRECNPJ + " TEXT NOT NULL);"
        );
        // Tabela veiculo
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_VEICULO + " ("
                + COLUNA_VEIID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_VEIMODELO + " TEXT NOT NULL, "
                + COLUNA_VEIMARCA + " TEXT NOT NULL, "
                + COLUNA_VEIPLACA + " TEXT NOT NULL, "
                + COLUNA_VEICOR + " TEXT NOT NULL, "
                + COLUNA_VEIANO + " INTEGER NOT NULL, "
                + COLUNA_VEIKM + " INTEGER NOT NULL, "
                + COLUNA_VEICLIID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + COLUNA_VEICLIID + ") REFERENCES " + TABELA_CLIENTE + "(" + COLUNA_CLIID + "));"
        );
        // Tabela mensagem
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_MENSAGEM + " ("
                + COLUNA_MENID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_MENCONTEUDO + " TEXT NOT NULL, "
                + COLUNA_MENDESTINATARIO + " TEXT NOT NULL, "
                + COLUNA_MENREMENTENTE + " TEXT NOT NULL, "
                + COLUNA_MENHORARIO + " TEXT NOT NULL);"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABELA_PRESTADORSERVICO +
                    " ADD COLUMN " + COLUNA_PREENDERECO + " TEXT");
        }
    }

    //VALIDAÇÃO DE USUÁRIO
    public boolean checkUserCliente(String email, String senha){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUNA_CLIEMAIL + " = ? AND " + COLUNA_CLISENHA + " = ?";
        String[] selectionArgs = {email, senha};
        Cursor cursor = db.query(TABELA_CLIENTE, new String[]{COLUNA_CLIID}, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public boolean checkUserPrestador(String email, String senha){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUNA_PREEMAIL + " = ? AND " + COLUNA_PRESENHA + " = ?";
        String[] selectionArgs = {email, senha};
        Cursor cursor = db.query(TABELA_PRESTADORSERVICO, new String[]{COLUNA_PREID}, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    //ÁREA DO CLIENTE
    public long inserirCliente(String nome, String email, String senha, String cpf, String dataNasc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_CLINOME, nome);
        values.put(COLUNA_CLIEMAIL, email);
        values.put(COLUNA_CLISENHA, senha);
        values.put(COLUNA_CLICPF, cpf);
        values.put(COLUNA_CLIDATANASC, dataNasc);
        long id = db.insert(TABELA_CLIENTE, null, values);
        db.close();
        return id;
    }

    //ÁREA DO VEÍCULO
    public long inserirVeiculo(String modelo, String marca, String placa, String cor, int ano, int km, int clienteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_VEIMODELO, modelo);
        values.put(COLUNA_VEIMARCA, marca);
        values.put(COLUNA_VEIPLACA, placa);
        values.put(COLUNA_VEICOR, cor);
        values.put(COLUNA_VEIANO, ano);
        values.put(COLUNA_VEIKM, km);
        values.put(COLUNA_VEICLIID, clienteId);
        long id = db.insert(TABELA_VEICULO, null, values);
        db.close();
        return id;
    }

    //ÁREA DO PRESTADOR
    public long inserirPrestador(String nome, String email, String senha, String cnpj, String endereco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_PRENOME, nome);
        values.put(COLUNA_PREEMAIL, email);
        values.put(COLUNA_PRESENHA, senha);
        values.put(COLUNA_PRECNPJ, cnpj);
        values.put(COLUNA_PREENDERECO, endereco);
        long id = db.insert(TABELA_PRESTADORSERVICO, null, values);
        db.close();
        return id;
    }
    public boolean inserirNota(int prestadorId, float nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_PRENOTA, nota);

        int linhasAfetadas = db.update(
                TABELA_PRESTADORSERVICO,
                values,
                COLUNA_PREID + " = ?",
                new String[]{String.valueOf(prestadorId)}
        );

        db.close();
        return linhasAfetadas > 0;
    }

    public List<PrestadorServico> listarPrestadoresComEndereco() {
        List<PrestadorServico> prestadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUNA_PRENOME + ", " + COLUNA_PREENDERECO +
                " FROM " + TABELA_PRESTADORSERVICO, null);

        while (cursor.moveToNext()) {
            PrestadorServico p = new PrestadorServico();
            p.setPreNome(cursor.getString(cursor.getColumnIndex(COLUNA_PRENOME)));
            p.setPreEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_PREENDERECO)));
            prestadores.add(p);
        }

        cursor.close();
        db.close();
        return prestadores;
    }
}
