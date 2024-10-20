package IsaacAwanna;

import Funcionalidade.GravadorDeDados;
import Modelo.Combos;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenciaTest {

    @Test
    public void testaPersistenciaCombos() {
        GravadorDeDados gravador = new GravadorDeDados();

        // Crie um mapa de exemplo para salvar
        Map<String, Combos> combosSalvo = new HashMap<>();
        combosSalvo.put("Fim do dia", new Combos("Fim do dia", 25));

        // Salvar os dados
        try {
            gravador.salvarCombos(combosSalvo);
        } catch (IOException e) {
            fail("Falha ao salvar combos: " + e.getMessage());
        }

        // Recuperar os dados
        Map<String, Combos> combosRecuperado = null;
        try {
            combosRecuperado = gravador.recuperarCombos();
        } catch (IOException e) {
            fail("Falha ao recuperar combos: " + e.getMessage());
        }

        // Verificar se os dados recuperados estão corretos
        assertNotNull(combosRecuperado, "O mapa recuperado não deve ser nulo");
        assertEquals(combosSalvo.size(), combosRecuperado.size(), "O tamanho do mapa deve ser igual");
        assertEquals(combosSalvo.get("Fim do dia").getNome(), combosRecuperado.get("Fim do dia").getNome(), "O nome do combo deve ser igual");
        assertEquals(combosSalvo.get("Fim do dia").getPreco(), combosRecuperado.get("Fim do dia").getPreco(), "O preço do combo deve ser igual");
    }
}
