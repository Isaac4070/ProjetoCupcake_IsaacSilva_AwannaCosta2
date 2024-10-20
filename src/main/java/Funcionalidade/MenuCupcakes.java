package Funcionalidade;

import Exceptions.ComboNaoExisteExcepetion;
import Exceptions.ComboJaExisteExcepetion;
import Exceptions.CupcakeNaoExisteExcepetion;
import Exceptions.CupcakeJaExisteExcepetion;
import Interface.SistemaVendas;
import Modelo.Cupcake;
import Modelo.TipoCupcake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class MenuCupecakes {
    private JFrame frame;
    private SistemaVendas sistema;

    public MenuCupecakes() {
        sistema = new SistemaVendasMap();
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        frame = new JFrame("Sistema gerenciador dos cupcakes");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastra = createMenu("Cadastrar", new String[]{"Cadastrar cupcake", "Cadastrar combos"}, this::cadastraItemAction);
        JMenu menuPesquisa = createMenu("Pesquisar", new String[]{"Pesquisar cupcake por sabor", "Pesquisar por faixa de preço"}, this::pesquisaItemAction);
        JMenu menuConta = createMenu("Estoque", new String[]{"Quantidade de cupcake do tipo"}, this::contaItemAction);
        JMenu menuRemove = createMenu("Remover", new String[]{"Remover sabor", "Remover combo"}, this::removeItemAction);
        JMenu menuExiste = createMenu("Existe", new String[]{"Existe cupcake do tipo", "Existe cupcake com sabor"}, this::existeItemAction);

        menuBar.add(menuCadastra);
        menuBar.add(menuPesquisa);
        menuBar.add(menuConta);
        menuBar.add(menuRemove);
        menuBar.add(menuExiste);

        return menuBar;
    }

    private JMenu createMenu(String name, String[] items, ActionListener listener) {
        JMenu menu = new JMenu(name);
        for (String item : items) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(listener);
            menu.add(menuItem);
        }
        return menu;
    }

    private void cadastraItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Cadastrar cupcake":
                cadastraCupcake();
                break;
            case "Cadastrar combos":
                cadastraCombo();
                break;
        }
    }

    private void cadastraCupcake() {
        ImageIcon icon = new ImageIcon("C:\\Users\\Isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor do cupcake:"));

        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        panel.add(new JLabel("Digite o preço do cupcake: "));
        JTextField precoField = new JTextField(10);
        precoField.setPreferredSize(new Dimension(200, 20));
        precoField.setMaximumSize(new Dimension(200, 20));
        panel.add(precoField);

        panel.add(new JLabel("Escolha o tipo do cupcake:"));
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "TEMÁTICO", "ALCOÓLICO"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);
        tipoBox.setMaximumSize(new Dimension(200, 20));
        panel.add(tipoBox);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Cadastrar Cupcake");
        dialog.pack(); // Ajusta o tamanho da janela para caber os componentes
        dialog.setLocationRelativeTo(frame); // Centraliza a janela em relação ao frame
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            String tipo = (String) tipoBox.getSelectedItem();
            try {
                double precoStr = 0;
                sistema.cadastarCupcake(sabor, precoStr, TipoCupcake.valueOf(tipo));
                JOptionPane.showMessageDialog(frame, "Cupcake cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (CupcakeJaExisteExcepetion ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Cupcake já existe!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void cadastraCombo() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o nome do combo:"));
        JTextField nomeField = new JTextField(10);
        nomeField.setPreferredSize(new Dimension(200, 20));
        nomeField.setMaximumSize(new Dimension(200, 20));
        panel.add(nomeField);
        panel.add(new JLabel("Digite o preço do combo:"));
        JTextField precoField = new JTextField(10);
        precoField.setPreferredSize(new Dimension(200, 20));
        precoField.setMaximumSize(new Dimension(200, 20));
        panel.add(precoField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Cadastrar Combo");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            double preco = Double.parseDouble(precoField.getText());
            try {
                sistema.cadastraCombos(nome, preco);
                JOptionPane.showMessageDialog(frame, "Combo cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (ComboJaExisteExcepetion ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Combo já existe!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void pesquisaItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Pesquisar cupcake por sabor":
                pesquisaCupcakePorSabor();
                break;
            case "Pesquisar por faixa de preço":
                pesquisaPorFaixaDePreco();
                break;
        }
    }
    private void pesquisaCupcakePorSabor() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor para pesquisar:"));
        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Pesquisar Cupcake por Sabor");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            try {
                List<Cupcake> lista = new ArrayList<>(sistema.pesquisaCupcakesPorSabor(sabor));
                lista.forEach(c -> JOptionPane.showMessageDialog(frame, c.toString(), "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE, icon));
            } catch (CupcakeNaoExisteExcepetion ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Cupcake não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void pesquisaPorFaixaDePreco() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o valor mínimo:"));

        JTextField minimoField = new JTextField(5);
        minimoField.setPreferredSize(new Dimension(200, 20));
        minimoField.setMaximumSize(new Dimension(200, 20));
        panel.add(minimoField);

        panel.add(new JLabel("Digite o valor máximo:"));

        JTextField maximoField = new JTextField(5);
        maximoField.setPreferredSize(new Dimension(200, 20));
        maximoField.setMaximumSize(new Dimension(200, 20));
        panel.add(maximoField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Pesquisar por Faixa de Preço");
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            try {
                double minimo = Double.parseDouble(minimoField.getText());
                double maximo = Double.parseDouble(maximoField.getText());
                List<Cupcake> lista = sistema.pesquisaValoresPorFaixa(minimo, maximo);
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nenhum cupcake encontrado nessa faixa de preço.", "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE, icon);
                } else {
                    StringBuilder resultado = new StringBuilder();
                    for (Cupcake c : lista) {
                        resultado.append(c.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, resultado.toString(), "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void contaItemAction(ActionEvent e) {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "TEMÁTICO", "ALCOÓLICO"};
        String tipoStr = (String) JOptionPane.showInputDialog(frame, "Escolha o tipo do cupcake:", "Contar Cupcake do Tipo",
                JOptionPane.QUESTION_MESSAGE, icon, tipos, tipos[0]);
        JOptionPane.showMessageDialog(frame, sistema.contaCupcakesDoTipo(TipoCupcake.valueOf(tipoStr)), "Resultado da Contagem", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void removeItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Remover sabor":
                removeSabor();
                break;
            case "Remover combo":
                removeCombo();
                break;
        }
    }

    private void removeSabor() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor para remover:"));

        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Remover Sabor");
        dialog.setLocationRelativeTo(frame);
//        dialog.setSize(900, 700);
//        dialog.setLocation(300, 200);
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            try {
                sistema.removeSabor(sabor);
                JOptionPane.showMessageDialog(frame, "Cupcake removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (CupcakeNaoExisteExcepetion ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Sabor não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void removeCombo() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o combo para remover:"));
        JTextField comboField = new JTextField(10);
        comboField.setPreferredSize(new Dimension(200, 20));
        comboField.setMaximumSize(new Dimension(200, 20));
        panel.add(comboField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Remover Combo");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String combo = comboField.getText();
            try {
                sistema.removeCombo(combo);
                JOptionPane.showMessageDialog(frame, "Combo removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (ComboNaoExisteExcepetion ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Combo não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void existeItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Existe cupcake do tipo":
                existeCupcakeDoTipo();
                break;
            case "Existe cupcake com sabor":
                existeCupcakeComSabor();
                break;
        }
    }

    private void existeCupcakeDoTipo() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");;
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "TEMÁTICO", "ALCOÓLICO"};
        String tipoStr = (String) JOptionPane.showInputDialog(frame, "Escolha o tipo do cupcake:", "Existe Cupcake do Tipo",
                JOptionPane.QUESTION_MESSAGE, icon, tipos, tipos[0]);
        JOptionPane.showMessageDialog(frame, sistema.existeCupcakesDoTipo(TipoCupcake.valueOf(tipoStr)), "Resultado da Verificação", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void existeCupcakeComSabor() {
        ImageIcon icon = new ImageIcon("C:\\Users\\isaac\\OneDrive\\Imagens\\cupcake.jpg");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor do cupcake:"));
        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Existe Cupcake com Sabor");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            JOptionPane.showMessageDialog(frame, sistema.existeSabor(sabor), "Resultado da Verificação", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    public static void main(String[] args) {
        SistemaVendasMap cardapio = new SistemaVendasMap();
        MenuCupecakes menu = new MenuCupecakes();
    }
}