package Funcionalidade;

import Exceptions.ComboJaExisteExcepetion;
import Exceptions.ComboNaoExisteExcepetion;
import Exceptions.CupcakeJaExisteExcepetion;
import Exceptions.CupcakeNaoExisteExcepetion;
import Interface.SistemaVendas;
import Modelo.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaVendasMap implements SistemaVendas {
    private Map<String, Cupcake> cupcakes;
    private Map<String, Combos> combos;
    private GravadorDeDados gravador = new GravadorDeDados();

    public SistemaVendasMap() {
        this.cupcakes = new HashMap<>();
        this.combos = new HashMap<>();
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

    @Override
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
        if (this.combos.containsKey(nome)) {
            throw new ComboJaExisteExcepetion("Já existe este combo");
        } else {
            this.combos.put(nome, new Combos(nome, preco));
            try {
                this.gravador.salvarCombos(this.combos);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Combo cadastrado com sucesso!");
        }
    }

    //Usando streams
    @Override
    public List<Cupcake> pesquisaCupcakesPorSabor(String sabor) {
        return this.cupcakes.values().stream()
                .filter(c -> c.getSabor().equalsIgnoreCase(sabor))
                .collect(Collectors.toList());
    }


    @Override
    public boolean existeSabor(String sabor) {
        return this.cupcakes.containsKey(sabor);
    }

    //Usando streams
    @Override
    public int contaCupcakesDoTipo(TipoCupcake tipo) {
        return (int) this.cupcakes.values().stream()
                .filter(c -> c.ehDoTipo(tipo))
                .count();
    }

    @Override
    public void removeSabor(String sabor) throws CupcakeNaoExisteExcepetion {
        if (this.cupcakes.containsKey(sabor)) {
            this.cupcakes.remove(sabor);
            try {
                this.gravador.salvarCupcakes(this.cupcakes);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Cookie removido com sucesso!");
        } else {
            throw new CupcakeNaoExisteExcepetion("Não foi encontrado este cookie");
        }
    }

    public void removeCombo(String nome) throws ComboNaoExisteExcepetion {
        if (this.combos.containsKey(nome)) {
            this.combos.remove(nome);
            try {
                this.gravador.salvarCombos(this.combos);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Combo removido com sucesso!");
        } else {
            throw new ComboNaoExisteExcepetion("Não foi encontrado este combo");
        }
    }

    //Usando streams
    @Override
    public List<Cupcake> pesquisaValoresPorFaixa(double valorMinimo, double valorMaximo) {
        return this.cupcakes.values().stream()
                .filter(c -> c.getPreco() >= valorMinimo && c.getPreco() <= valorMaximo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeCupcakesDoTipo(TipoCupcake tipo) {
        for (Cupcake c : this.cupcakes.values()) {
            if (c.ehDoTipo(tipo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Combos> pesquisaCombos(String combo) {
        try {
            this.combos = this.gravador.recuperarCombos();
            System.out.println("Recuperando combos: " + this.combos.size());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        List<Combos> list = new ArrayList<>();
        for (Combos c : this.combos.values()) {
            if (c.getNome().equalsIgnoreCase(combo)) {
                list.add(c);
            }
        }
        System.out.println("Combos encontrados: " + list.size());
        return list;
    }
}