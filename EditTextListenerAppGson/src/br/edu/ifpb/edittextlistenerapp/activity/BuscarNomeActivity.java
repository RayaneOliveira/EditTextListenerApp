package br.edu.ifpb.edittextlistenerapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.edu.ifpb.edittextlistenerapp.R;
import br.edu.ifpb.edittextlistenerapp.adapter.PessoasCustomAdapter;
import br.edu.ifpb.edittextlistenerapp.asynctask.BuscarNomeAsyncTask;
import br.edu.ifpb.edittextlistenerapp.callback.BuscarPessoaCallBack;
import br.edu.ifpb.edittextlistenerapp.entidade.Pessoa;

public class BuscarNomeActivity extends Activity
        implements TextWatcher, OnItemClickListener, BuscarPessoaCallBack {

    // Define o tamanho m�nimo do texto para consulta no servidor.
    private static int TAMANHO_MINIMO_TEXTO = 4;

    
    private EditText nomeEditText;
    List<Pessoa> pessoas;
    PessoasCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Inicializa��o da activity e defini��o do layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_nome);

        // Recuperando o EditText e ListView.
        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        nomeEditText.addTextChangedListener(this);

        ListView nomesListView = (ListView) findViewById(R.id.nomesListView);
        pessoas = new ArrayList<Pessoa>();
        adapter = new PessoasCustomAdapter(this, pessoas);

        // Adapter modificado.
        nomesListView.setAdapter(adapter);

        // Evento de OnItemClickListener.
        nomesListView.setOnItemClickListener(this);
    }

    // TextWatcher
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        Log.i("EditTextListener", "beforeTextChanged: " + charSequence);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        Log.i("EditTextListener", "onTextChanged: " + charSequence);
        String nome = charSequence.toString();
        Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);

        if (nome.length() >= TAMANHO_MINIMO_TEXTO) {
			
			// Converte objeto Java para JSON por meio da biblioteca Gson e aciona AsyncTask.
			
			Gson gson = new Gson();
			String json = gson.toJson(pessoa);
		    
		    BuscarNomeAsyncTask buscarNomeAsyncTask = new BuscarNomeAsyncTask(this);
		    buscarNomeAsyncTask.execute(json);
		}
    }

    @Override
    public void afterTextChanged(Editable editable) {

        Log.i("EditTextListener","afterTextChanged: " + editable);
    }

    // OnItemClickListener.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	
    	Pessoa pessoa = new Pessoa();
		pessoa = pessoas.get(position);
		adapter.getItem(position);
	   
		// Aciona a pr�xima activity respons�vel por Mostrar as informa��es.
		Intent intent = new Intent(this, MostrarInformacoesActivity.class);
		Bundle informacoes = new Bundle();
		
		informacoes.putString("Nome", pessoa.getNome());
		informacoes.putString("Email", pessoa.getEmail());
		informacoes.putString("Inscricao", pessoa.getDescricao());
		informacoes.putBoolean("Entregue", pessoa.isEntregue());
		intent.putExtras(informacoes);
		
		startActivity(intent);

        Log.i("EditTextListener", "Position: " + position);

        Toast toast = Toast.makeText(this, "Item " + (position + 1) + ": " 
        							 + pessoas.get(position), Toast.LENGTH_LONG);
        toast.show();
    }

    // BuscarPessoaCallBack. 
    @Override
    public void backBuscarNome(List<Pessoa> pessoas) {

        this.pessoas.clear();
        this.pessoas.addAll(pessoas);
        adapter.notifyDataSetChanged();
    }

    // BuscarPessoaCallBack. 
    @Override
    public void errorBuscarNome(String error) {

        pessoas.clear();
        adapter.notifyDataSetChanged();

        Toast.makeText(this, error, Toast.LENGTH_LONG);
    }
}