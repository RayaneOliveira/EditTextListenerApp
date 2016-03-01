package br.edu.ifpb.edittextlistenerapp.callback;

import java.util.List;

import br.edu.ifpb.edittextlistenerapp.entidade.Pessoa;

/**
 * Created by Rerisson Daniel on 25/02/16.
 */
public interface BuscarPessoaCallBack {

    void backBuscarNome(List<Pessoa> names);

    void errorBuscarNome(String error);
}