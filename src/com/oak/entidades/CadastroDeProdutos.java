package com.oak.entidades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CadastroDeProdutos {
    private final List<Produto> produtos = new ArrayList<>();
    private JFrame frameCadastro;
    private JFrame frameListagem;

    public void mostrarTelaCadastro() {
        frameCadastro = new JFrame("Cadastro de Produto");
        frameCadastro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCadastro.setSize(400, 300);
        frameCadastro.setLayout(new GridLayout(5, 2, 10, 10));

        JTextField nomeField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField valorField = new JTextField();
        JComboBox<String> disponivelBox = new JComboBox<>(new String[]{"Sim", "Não"});

        JButton btnCadastrar = new JButton("Cadastrar");

        frameCadastro.add(new JLabel("Nome do Produto:"));
        frameCadastro.add(nomeField);
        frameCadastro.add(new JLabel("Descrição do Produto:"));
        frameCadastro.add(descricaoField);
        frameCadastro.add(new JLabel("Valor do Produto:"));
        frameCadastro.add(valorField);
        frameCadastro.add(new JLabel("Disponível para Venda:"));
        frameCadastro.add(disponivelBox);
        frameCadastro.add(new JLabel());
        frameCadastro.add(btnCadastrar);

        frameCadastro.setLocationRelativeTo(null);
        frameCadastro.setVisible(true);

        btnCadastrar.addActionListener(e -> {
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            double valor;
            try {
                valor = Double.parseDouble(valorField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameCadastro, "Insira um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean disponivel = disponivelBox.getSelectedItem().equals("Sim");

            produtos.add(new Produto(nome, descricao, valor, disponivel));
            JOptionPane.showMessageDialog(frameCadastro, "Produto cadastrado com sucesso!");
            frameCadastro.dispose();
            mostrarTelaListagem();
        });
    }

    public void mostrarTelaListagem() {
        frameListagem = new JFrame("Listagem de Produtos");
        frameListagem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameListagem.setSize(600, 400);

        produtos.sort(Comparator.comparingDouble(Produto::getValor));

        DefaultTableModel model = new DefaultTableModel(new String[]{"Nome", "Valor"}, 0);
        for (Produto produto : produtos) {
            model.addRow(new Object[]{produto.getNome(), produto.getValor()});
        }
        JTable tabela = new JTable(model);

        JButton btnNovoProduto = new JButton("Novo Produto");
        btnNovoProduto.addActionListener(e -> {
            frameListagem.dispose();
            mostrarTelaCadastro();
        });

        frameListagem.setLayout(new BorderLayout());
        frameListagem.add(new JScrollPane(tabela), BorderLayout.CENTER);
        frameListagem.add(btnNovoProduto, BorderLayout.SOUTH);

        frameListagem.setLocationRelativeTo(null);
        frameListagem.setVisible(true);
    }
}

