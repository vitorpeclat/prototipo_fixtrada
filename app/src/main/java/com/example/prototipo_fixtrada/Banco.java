package com.example.prototipo_fixtrada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prototipo_fixtrada.construtores.Cliente;

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
    //tabela registro
    public static final String TABELA_REGISTRO = "registro_servico";
    public static final String COLUNA_REGID = "regId";
    public static final String COLUNA_REGDESC = "regDesc";
    public static final String COLUNA_REGDATA = "regData";
    public static final String COLUNA_REGVEIID = "regVeiId";
    public static final String COLUNA_REGPREID = "regPreId";
    //tabela mensagem
    public static final String TABELA_MENSAGEM = "mensagem";
    public static final String COLUNA_MENID = "menId";
    public static final String COLUNA_MENCONTEUDO = "menConteudo";
    public static final String COLUNA_MENREMENTENTE = "menRemetente";
    public static final String COLUNA_MENREGID = "menRegId";

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
               // + COLUNA_CLINOTA + " TEXT NOT NULL, "
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
        // Tabela veiculo (relacionada ao cliente)
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
        // Tabela registro (relacionada ao veículo e ao prestador)
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_REGISTRO + " ("
                + COLUNA_REGID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_REGDESC + " TEXT NOT NULL, "
                + COLUNA_REGDATA + " TEXT NOT NULL, "
                + COLUNA_REGVEIID + " INTEGER NOT NULL, "
                + COLUNA_REGPREID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + COLUNA_REGVEIID + ") REFERENCES " + TABELA_VEICULO + "(" + COLUNA_VEIID + "), "
                + "FOREIGN KEY(" + COLUNA_REGPREID + ") REFERENCES " + TABELA_PRESTADORSERVICO + "(" + COLUNA_PREID + "));"
        );
        // Tabela mensagem (relacionada ao registro)
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_MENSAGEM + " ("
                + COLUNA_MENID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_MENCONTEUDO + " TEXT NOT NULL, "
                + COLUNA_MENREMENTENTE + " TEXT NOT NULL, "
                + COLUNA_MENREGID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + COLUNA_MENREGID + ") REFERENCES " + TABELA_REGISTRO + "(" + COLUNA_REGID + "));"
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

    public List<String> listarClientes() {
        List<String> clientes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CLIENTE, null);
        while (cursor.moveToNext()) {
            String nome = cursor.getString(cursor.getColumnIndex(COLUNA_CLINOME));
            clientes.add(nome);
        }
        cursor.close();
        db.close();
        return clientes;
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

    public List<String> listarVeiculos() {
        List<String> veiculos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_VEICULO, null);
        while (cursor.moveToNext()) {
            String modelo = cursor.getString(cursor.getColumnIndex(COLUNA_VEIMODELO));
            String placa = cursor.getString(cursor.getColumnIndex(COLUNA_VEIPLACA));
            veiculos.add(modelo + " - " + placa);
        }
        cursor.close();
        db.close();
        return veiculos;
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

    public List<String> listarPrestadores() {
        List<String> prestadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PRESTADORSERVICO, null);
        while (cursor.moveToNext()) {
            String nome = cursor.getString(cursor.getColumnIndex(COLUNA_PRENOME));
            prestadores.add(nome);
        }
        cursor.close();
        db.close();
        return prestadores;
    }

    public PrestadorServico buscarPrestadorPorEmailSenha(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PRESTADORSERVICO +
                        " WHERE " + COLUNA_PREEMAIL + " = ? AND " + COLUNA_PRESENHA + " = ?",
                new String[]{email, senha});

        if (cursor.moveToFirst()) {
            PrestadorServico prestador = new PrestadorServico();
            prestador.setPreId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_PREID)));
            prestador.setPreNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PRENOME)));
            prestador.setPreEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PREEMAIL)));
            prestador.setPreSenha(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PRESENHA)));
            prestador.setPreCpf(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PRECNPJ)));
            prestador.setPreNota(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_PRENOTA)));
            cursor.close();
            db.close();
            return prestador;
        }

        cursor.close();
        db.close();
        return null;
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

    //ÁREA DO REGISTRO
    public long inserirRegistro(String descricao, String data, int veiId, int preId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_REGDESC, descricao);
        values.put(COLUNA_REGDATA, data);
        values.put(COLUNA_REGVEIID, veiId);
        values.put(COLUNA_REGPREID, preId);
        long id = db.insert(TABELA_REGISTRO, null, values);
        db.close();
        return id;
    }

    public List<String> listarRegistros() {
        List<String> registros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_REGISTRO, null);
        while (cursor.moveToNext()) {
            String desc = cursor.getString(cursor.getColumnIndex(COLUNA_REGDESC));
            String data = cursor.getString(cursor.getColumnIndex(COLUNA_REGDATA));
            registros.add(data + ": " + desc);
        }
        cursor.close();
        db.close();
        return registros;
    }

    public long inserirMensagem(String conteudo, String remetente, int regId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_MENCONTEUDO, conteudo);
        values.put(COLUNA_MENREMENTENTE, remetente);
        values.put(COLUNA_MENREGID, regId);
        long id = db.insert(TABELA_MENSAGEM, null, values);
        db.close();
        return id;
    }

    public List<String> listarMensagensPorRegistro(int regId) {
        List<String> mensagens = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABELA_MENSAGEM,
                null,
                COLUNA_MENREGID + "=?",
                new String[]{String.valueOf(regId)},
                null, null, null
        );
        while (cursor.moveToNext()) {
            String remetente = cursor.getString(cursor.getColumnIndex(COLUNA_MENREMENTENTE));
            String conteudo = cursor.getString(cursor.getColumnIndex(COLUNA_MENCONTEUDO));
            mensagens.add(remetente + ": " + conteudo);
        }
        cursor.close();
        db.close();
        return mensagens;
    }
}
