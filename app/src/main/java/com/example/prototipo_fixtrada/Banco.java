package com.example.prototipo_fixtrada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Banco extends SQLiteOpenHelper {
    private static final String BANCO = "fixtrada.db";
    private static final int VERSAO = 1;

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
    public static final String COLUNA_PRESENHA = "preSenha";
    public static final String COLUNA_PRECNPJ = "preCnpj";
    public static final String COLUNA_PREDATANASC = "preDataNasc";

    //tabela veiculo
    public static final String TABELA_VEICULO = "veiculo";
    public static final String COLUNA_VEIID = "veiId";
    public static final String COLUNA_VEIMODELO = "veiModelo";
    public static final String COLUNA_VEIPLACA = "veiPlaca";
    public static final String COLUNA_VEICOR = "veiCor";
    public static final String COLUNA_VEIANO = "veiAno";
    public static final String COLUNA_VEIKM = "veiKm";
    public static final String COLUNA_VEICLIID = "veiClieId";


    //tabela registro
    public static final String TABELA_REGISTRO = "registro";
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
                + COLUNA_CLISENHA + " TEXT NOT NULL, "
                + COLUNA_CLICPF + " TEXT NOT NULL, "
                + COLUNA_CLIDATANASC + " TEXT NOT NULL);"
        );

        // Tabela prestadorSevico
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_PRESTADORSERVICO + " ("
                + COLUNA_PREID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_PRENOME + " TEXT NOT NULL, "
                + COLUNA_PREEMAIL + " TEXT NOT NULL, "
                + COLUNA_PRESENHA + " TEXT NOT NULL, "
                + COLUNA_PRECNPJ + " TEXT NOT NULL, "
                + COLUNA_PREDATANASC + " TEXT NOT NULL);"
        );

        // Tabela veiculo (relacionada ao cliente)
        sqLiteDatabase.execSQL("CREATE TABLE " + TABELA_VEICULO + " ("
                + COLUNA_VEIID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_VEIMODELO + " TEXT NOT NULL, "
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
    }

    public boolean checkUser(String usuario, String senha){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUNA_USUARIO + " = ? AND " + COLUNA_SENHA + " = ?";
        String[] selectionArgs = {usuario, senha};
        Cursor cursor = db.query(TABELA_USUARIOS, new String[]{COLUNA_USUARIOS_ID}, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public Usuario getUsuario(String usuario, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUNA_USUARIO + " = ? AND " + COLUNA_SENHA + " = ?";
        String[] selectionArgs = {usuario, senha};
        Cursor cursor = db.query(TABELA_USUARIOS,
                new String[]{COLUNA_USUARIOS_ID, COLUNA_USERNOME, COLUNA_USUARIO, COLUNA_SENHA},
                selection, selectionArgs, null, null, null);

        Usuario user = null;
        if (cursor.moveToFirst()) {
            user = new Usuario();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_USUARIOS_ID)));
            user.setUserNome(cursor.getString(cursor.getColumnIndex(COLUNA_USERNOME)));
            user.setUsuario(cursor.getString(cursor.getColumnIndex(COLUNA_USUARIO)));
            user.setSenha(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));
        }

        cursor.close();
        db.close();
        return user;
    }

    public boolean addUser(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_USUARIO, user.getUsuario());
        values.put(COLUNA_SENHA, user.getSenha());
        values.put(COLUNA_USERNOME, user.getUserNome());
        long result = db.insert(TABELA_USUARIOS, null, values);
        db.close();
        return result != -1;
    }

    public void salvarResposta(Resposta resposta){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_ENTNOME, resposta.getEntNome());
        values.put(COLUNA_ORIGEM, resposta.getOrigem());
        values.put(COLUNA_DESTINO, resposta.getDestino());
        values.put(COLUNA_NOME, resposta.getNome());
        values.put(COLUNA_TELEFONE, resposta.getTelefone());
        values.put(COLUNA_LOCAL, resposta.getLocal());
        if (resposta.getHora() != null && !resposta.getHora().isEmpty()) {
            values.put(COLUNA_HORA, resposta.getHora());
        }

        db.insert(TABELA_RESPOSTA, null, values);
        db.close();
    }

    public List<Resposta> getTodasRespostas() {
        List<Resposta> respostas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_RESPOSTA, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Resposta resposta = new Resposta();
                resposta.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_RESPOSTA_ID)));
                resposta.setEntNome(cursor.getString(cursor.getColumnIndex(COLUNA_ENTNOME)));
                resposta.setOrigem(cursor.getString(cursor.getColumnIndex(COLUNA_ORIGEM)));
                resposta.setDestino(cursor.getString(cursor.getColumnIndex(COLUNA_DESTINO)));
                resposta.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                resposta.setTelefone(cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE)));
                resposta.setLocal(cursor.getString(cursor.getColumnIndex(COLUNA_LOCAL)));
                resposta.setHora(cursor.getString(cursor.getColumnIndex(COLUNA_HORA)));

                respostas.add(resposta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return respostas;
    }

    public List<Resposta> getRespostasPorEntrevistador(String entrevistador) {
        List<Resposta> respostas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUNA_ENTNOME + " = ?";
        String[] selectionArgs = {entrevistador};

        Cursor cursor = db.query(TABELA_RESPOSTA, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Resposta resposta = new Resposta();
                resposta.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_RESPOSTA_ID)));
                resposta.setEntNome(cursor.getString(cursor.getColumnIndex(COLUNA_ENTNOME)));
                resposta.setOrigem(cursor.getString(cursor.getColumnIndex(COLUNA_ORIGEM)));
                resposta.setDestino(cursor.getString(cursor.getColumnIndex(COLUNA_DESTINO)));
                resposta.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                resposta.setTelefone(cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE)));
                resposta.setLocal(cursor.getString(cursor.getColumnIndex(COLUNA_LOCAL)));
                resposta.setHora(cursor.getString(cursor.getColumnIndex(COLUNA_HORA)));

                respostas.add(resposta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return respostas;
    }

    public void limparRespostas() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_RESPOSTA, null, null);
        db.close();
    }
}
