package IsaacAwanna;

import org.junit.Test;
import static org.junit.Assert.*;
import Exceptions.ComboJaExisteExcepetion;
import Exceptions.ComboNaoExisteExcepetion;
import Exceptions.CupcakeJaExisteExcepetion;
import Exceptions.CupcakeNaoExisteExcepetion;
import Funcionalidade.SistemaVendasMap;
import Modelo.Combos;
import Modelo.Cupcake;
import Modelo.TipoCupcake;
import java.util.List;

public class Testes {

    @Test
    public void testaCadastroCupcake() {
        SistemaVendasMap testa = new SistemaVendasMap();
        try {
            List<Cupcake> sabores = testa.pesquisaCupcakesPorSabor("Morango");
            assertTrue(sabores.size() == 0);
            testa.cadastarCupcake("Morango", 10, TipoCupcake.RECHEADO);
            sabores = testa.pesquisaCupcakesPorSabor("Morango");
            assertTrue(sabores.size() == 1);
        } catch (CupcakeJaExisteExcepetion e) {
            fail("Não deveria haver falha!");
        }

        @Test
        public void testaCadastraCombo() {
            SistemaVendasMap testa = new SistemaVendasMap();
            try {
                List<Combos> combos = testa.pesquisaCombos("Duo");
                assertTrue(combos.size() == 0);
                testa.cadastraCombos("Duo", 20);
                combos = testa.pesquisaCombos("Duo");
                assertTrue(combos.size() == 1);
            } catch (ComboJaExisteExcepetion e) {
                fail("Não pode falhar")
            }
        }

        @Test
        public void testaRemocaoDeCupcake () throws CupcakeNaoExisteExcepetion, CupcakeJaExisteExcepetion {
            SistemaVendasMap testa = new SistemaVendasMap();
            List<Cupcake> sabores = testa.pesquisaCupcakesPorSabor(" ");
            assertTrue(sabores.size() == 0);
            testa.cadastarCupcake("Morango", 10, TipoCupcake.RECHEADO);
            sabores = testa.pesquisaCupcakesPorSabor("Morango");
            assertTrue(sabores.size() == 1);
            testa.removeSabor("Morango");
            sabores = testa.pesquisaCupcakesPorSabor("Morango");
            assertTrue(sabores.size() == 0);
        }
        @Test
        public void testaRemocaoDeCombos () throws ComboJaExisteExcepetion, ComboNaoExisteExcepetion {
            SistemaVendasMap testa = new SistemaVendasMap();
            List<Combos> combo = testa.pesquisaCombos("Fim do dia");
            assertTrue(combo.size() == 0);
            testa.cadastraCombos("Fim do dia", 25);
            combo = testa.pesquisaCombos("Fim do dia");
            assertTrue(combo.size() == 1);
            testa.removeCombo("Fim do dia");
            combo = testa.pesquisaCombos("Fim do dia");
            assertTrue(combo.size() == 0);

        }

    }
}