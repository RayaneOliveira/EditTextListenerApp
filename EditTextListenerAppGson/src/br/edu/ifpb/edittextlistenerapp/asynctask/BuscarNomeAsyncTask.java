package br.edu.ifpb.edittextlistenerapp.asynctask;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.edittextlistenerapp.callback.BuscarPessoaCallBack;
import br.edu.ifpb.edittextlistenerapp.entidade.Pessoa;
import br.edu.ifpb.edittextlistenerapp.util.HttpService;
import br.edu.ifpb.edittextlistenerapp.util.Response;

/**
 * Created by Rhavy on 24/02/2016.
 */
public class BuscarNomeAsyncTask extends AsyncTask<JSONObject, Void, Response> {

    private BuscarPessoaCallBack buscarNomeCallBack;

    public BuscarNomeAsyncTask(BuscarPessoaCallBack buscarNomeCallBack) {

        this.buscarNomeCallBack = buscarNomeCallBack;
    }

    @Override
    protected Response doInBackground(JSONObject... jsons) {

        Response response = null;

        JSONObject json = jsons[0];
        Log.i("EditTextListener", "doInBackground (JSON): " + json);

        try {

            response = HttpService.sendJSONPostResquest("get-byname", json);

        } catch (IOException e) {

            Log.e("EditTextListener", e.getMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response response) {

        int codeHttp = response.getStatusCodeHttp();

        Log.i("EditTextListener", "Código HTTP: " + codeHttp
                + " Conteúdo: " + response.getContentValue());

        if (codeHttp != HttpURLConnection.HTTP_OK) {

            buscarNomeCallBack.errorBuscarNome(response.getContentValue());

        } else {

            Gson gson = new Gson();
            List<Pessoa> pessoas = gson.fromJson(response.getContentValue(),
                    new TypeToken<ArrayList<Pessoa>>(){}.getType());

            buscarNomeCallBack.backBuscarNome(pessoas);
        }
    }
}