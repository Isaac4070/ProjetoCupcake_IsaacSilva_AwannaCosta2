package Funcionalidade;

import Exceptions.ComboJaExisteExcepetion;
import Exceptions.ComboNaoExisteExcepetion;
import Exceptions.CupcakeJaExisteExcepetion;
import Exceptions.CupcakeNaoExisteExcepetion;
import Interface.SistemaVendas;
import Modelo.*;

import java.io.IOException;
import java.util.*;

public class SistemaVendasMap implements SistemaVendas {

    private Map<String, Cupcake> cupcakes;
    private Map<String, Combos> combos;
    private GravadorDeDados gravador = new GravadorDeDados();

    public SistemaVendasMap() {
        this.cupcakes = new HashMap<String, Cupcake>();
        this.combos = new HashMap<String, Combos>();
        recuperarDadosCupcake();
        recuperarDadosCombos();
    }

    public void recuperarDadosCupcake() {
        try {
            this.cupcakes = this.gravador.recuperarCupcakes();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void recuperarDadosCombos() {
        try {
            this.combos = this.gravador.recuperarCombos();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void cadastarCupcake(String sabor, double preco, TipoCupcake tipo) throws CupcakeJaExisteExcepetion {
        if (this.cupcakes.containsKey(sabor)) {
            throw new CupcakeJaExisteExcepetion("Já existe este Cupcake cadastrado");
        } else {
            this.cupcakes.put(sabor, new Cupcake(sabor, preco, tipo));
            try {
                this.gravador.salvarCupcakes(this.cupcakes);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Capcake cadastrado com sucesso!");
        }
    }

    @Override
    public void cadastraCombos(String nome, double preco) throws ComboJaExisteExcepetion {
        if (this.combos.containsKey(nome)){
            throw new ComboJaExisteExcepetion("Já existe este combo");
        } else {
            this.combos.put(nome, new Combos(nome, preco));
            try {
                this.gravador.salvarCombos(this.combos);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
            System.out.println("Combo cadastrado com sucesso!");
        }
    }

    @Override
    public List<Cupcake> pesquisaCupcakesPorSabor(String sabor){
        List<Cupcake> lista = new ArrayList<>();
        for (Cupcake c: this.cupcakes.values()){
            if (c.getSabor().equals(sabor)) {
                lista.add(c);
            }
        }
        return lista;
    }

    @Override
    public boolean existeSabor(String sabor) {
        return this.cupcakes.containsKey(sabor);
    }

    @Override
    public int contaCupcakesDoTipo(TipoCupcake tipo) {
        int quantCupcakes = 0;
        for (Cupcake c: this.cupcakes.values()){
            if (c.ehDoTipo(tipo)){
                quantCupcakes++;
            }
        }
        return quantCupcakes;
    }

    @Override
    public void removeSabor(String sabor) throws CupcakeNaoExisteExcepetion {
        if (this.cupcakes.containsKey(sabor)){
            this.cupcakes.remove(sabor);
            System.out.println("Cookie removido com sucesso!");
        } else {
            throw new CupcakeNaoExisteExcepetion("Não foi encontrado este cookie");
        }
    }
    public void removeCombo(String nome) throws ComboNaoExisteExcepetion {
        if (this.combos.containsKey(nome)){
            this.combos.remove(nome);
            System.out.println("Combo removido com sucesso!");
        } else {
            throw new ComboNaoExisteExcepetion("Não foi encontrado este combo");
        }
    }

    @Override
    public List<Cupcake> pesquisaValoresPorFaixa(double valorMinimo, double valorMaximo) {
        List<Cupcake> listinha = new ArrayList<>();
        for (Cupcake c: this.cupcakes.values()){
            if (c.getPreco()>=valorMinimo && c.getPreco()<=valorMaximo){
                listinha.add(c);
            }
        }
        return listinha;
    }


    @Override
    public boolean existeCupcakesDoTipo(TipoCupcake tipo) {
        return this.cupcakes.containsKey(tipo);
    }

    @Override
    public List<Combos> pesquisaCombos() {
        List<Combos> listaa = new ArrayList<>();
        for (Combos cb: this.combos.values()){
            if (cb.getNome().equals(combos)){
                listaa.add(cb);
            }
        }
        return listaa;
    }
}