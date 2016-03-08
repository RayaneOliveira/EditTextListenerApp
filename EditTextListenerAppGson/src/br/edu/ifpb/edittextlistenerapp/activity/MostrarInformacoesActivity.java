package br.edu.ifpb.edittextlistenerapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import br.edu.ifpb.edittextlistenerapp.R;

public class MostrarInformacoesActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_informacoes);

		Intent intent = getIntent();
		Bundle informacoes = intent.getExtras();

		TextView nome = (TextView) findViewById(R.id.nome);
		nome.setText(informacoes.getString("Nome"));
		
		TextView email = (TextView) findViewById(R.id.email);
		email.setText(informacoes.getString("Email"));
		
		TextView inscricao = (TextView) findViewById(R.id.inscricao);
		inscricao.setText(informacoes.getString("Inscricao"));
		
		TextView entregue = (TextView) findViewById(R.id.entregue);
		
		if(informacoes.getBoolean("Entregue") == true){
		
			entregue.setText("Entregue");
			
		} else {
			
			entregue.setText("Não entregue");
		}
	}
}
