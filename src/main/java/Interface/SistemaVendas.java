package Interface;

import Exceptions.ComboJaExisteExcepetion;
import Exceptions.ComboNaoExisteExcepetion;
import Exceptions.CupcakeJaExisteExcepetion;
import Exceptions.CupcakeNaoExisteExcepetion;
import Modelo.Combos;
import Modelo.Cupcake;
import Modelo.TipoCupcake;

import java.util.List;

public interface SistemaVendas {

    void cadastarCupcake(String sabor, double preco, TipoCupcake tipo) throws CupcakeJaExisteExcepetion;

    void cadastraCombos(String nome, double preco) throws ComboJaExisteExcepetion;

    List<Cupcake> pesquisaCupcakesPorSabor(String sabor) throws CupcakeNaoExisteExcepetion;

    boolean existeSabor(String sabor);

    int contaCupcakesDoTipo(TipoCupcake tipo);

    void removeSabor(String sabor) throws CupcakeNaoExisteExcepetion;

    void removeCombo(String combo) throws ComboNaoExisteExcepetion;

    List<Cupcake> pesquisaValoresPorFaixa(double valorMinimo, double valorMaximo);

    boolean existeCupcakesDoTipo(TipoCupcake tipo);

    List<Combos> pesquisaCombos();

    void recuperarDadosCupcake();

    void recuperarDadosCombos();
}
