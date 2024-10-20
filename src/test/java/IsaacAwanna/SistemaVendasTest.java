package IsaacAwanna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Exceptions.*;
import Funcionalidade.SistemaVendasMap;
import Modelo.*;

import java.util.List;

public class SistemaVendasTest {

    private SistemaVendasMap sistema;

    @BeforeEach //Assegura que cada teste começe com o mesmo estado inicial.
    public void setUp() {
        sistema = new SistemaVendasMap();
        try {
            sistema.removeCombo("Fim do dia");
        } catch (Exception e) {}

        try {
            sistema.removeSabor("uva");
        } catch (Exception e) {}
    }

    @Test
    public void testaCadastroCupcake() {
        try {
            List<Cupcake> sabores = sistema.pesquisaCupcakesPorSabor("Morango");
            System.out.println("Inicial: " + sabores.size()); // Log de verificação
            assertTrue(sabores.isEmpty(), "A lista de sabores deveria estar vazia inicialmente.");

            sistema.cadastarCupcake("Morango", 10, TipoCupcake.RECHEADO);
            sabores = sistema.pesquisaCupcakesPorSabor("Morango");
            System.out.println("Após cadastro: " + sabores.size()); // Log de verificação
            assertTrue(sabores.size() == 1, "A lista de sabores deveria conter 1 cupcake após o cadastro.");
        } catch (CupcakeJaExisteExcepetion e) {
            fail("Não deveria haver falha!");
        }
    }

    @Test
    public void testaCadastraCombo() {
        SistemaVendasMap testa = new SistemaVendasMap();
        try {
            // Remove combos existentes antes do teste
            List<Combos> existingCombos = testa.pesquisaCombos("Duo");
            if (!existingCombos.isEmpty()) {
                for (Combos combo : existingCombos) {
                    testa.removeCombo(combo.getNome());
                }
            }

            // Teste de cadastro
            List<Combos> combos = testa.pesquisaCombos("Duo");
            assertTrue(combos.size() == 0);
            testa.cadastraCombos("Duo", 20);
            combos = testa.pesquisaCombos("Duo");
            assertTrue(combos.size() == 1);
        } catch (ComboJaExisteExcepetion | ComboNaoExisteExcepetion e) {
            fail("Não pode falhar");
        }
    }

    @Test
    public void testaRemocaoDeCupcake() throws CupcakeNaoExisteExcepetion, CupcakeJaExisteExcepetion {
        SistemaVendasMap testa = new SistemaVendasMap();
        // Remove cupcakes existentes com sabor Morango antes do teste
        List<Cupcake> existingCupcakes = testa.pesquisaCupcakesPorSabor("Morango");
        if (!existingCupcakes.isEmpty()) {
            testa.removeSabor("Morango");
        }

        // Executa o teste
        List<Cupcake> sabores = testa.pesquisaCupcakesPorSabor("Morango");
        assertTrue(sabores.size() == 0);
        testa.cadastarCupcake("Morango", 10, TipoCupcake.RECHEADO);
        sabores = testa.pesquisaCupcakesPorSabor("Morango");
        assertTrue(sabores.size() == 1);
        testa.removeSabor("Morango");
        sabores = testa.pesquisaCupcakesPorSabor("Morango");
        assertTrue(sabores.size() == 0);
    }

    @Test
    public void testaRemocaoDeCombos() throws ComboJaExisteExcepetion, ComboNaoExisteExcepetion {
        List<Combos> combo = sistema.pesquisaCombos("Fim do dia");
        System.out.println("Antes da remoção: " + combo.size());
        assertTrue(combo.isEmpty(), "A lista de combos deveria estar vazia inicialmente.");

        sistema.cadastraCombos("Fim do dia", 25);
        combo = sistema.pesquisaCombos("Fim do dia");
        System.out.println("Após adicionar: " + combo.size());
        assertTrue(combo.size() == 1, "A lista de combos deveria conter 1 combo após o cadastro.");

        sistema.removeCombo("Fim do dia");
        combo = sistema.pesquisaCombos("Fim do dia");
        System.out.println("Após remover: " + combo.size());
        assertTrue(combo.isEmpty(), "A lista de combos deveria estar vazia após a remoção.");
    }

    @Test
    public void testaPesquisas() throws CupcakeJaExisteExcepetion {
        SistemaVendasMap testa = new SistemaVendasMap();
        List<Cupcake> valores = testa.pesquisaValoresPorFaixa(1, 4);
        System.out.println("Inicialmente, tamanho da lista: " + valores.size()); // Adiciona log
        assertTrue(valores.isEmpty(), "A lista de valores deveria estar vazia inicialmente.");

        testa.cadastarCupcake("uva", 3, TipoCupcake.CLÁSSICO);
        valores = testa.pesquisaValoresPorFaixa(1, 4);
        System.out.println("Após adicionar cupcake, tamanho da lista: " + valores.size()); // Adiciona log
        assertTrue(valores.size() == 1, "A lista de valores deveria conter 1 cupcake após o cadastro.");

        assertTrue(testa.existeCupcakesDoTipo(TipoCupcake.CLÁSSICO), "O tipo de cupcake deveria existir.");
    }
}