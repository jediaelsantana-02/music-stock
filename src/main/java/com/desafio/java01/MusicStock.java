package com.desafio.java01;

import java.util.Scanner;

public class MusicStock {
    private static final int CAPACIDADE_MAXIMA_ESTOQUE = 100;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String opcao;

        do {
            opcao = menuPrincipal().toUpperCase().trim();
            System.out.println("--> " + opcao);

            switch (opcao) {
                case String s when s.equals("1") || s.contains("CADASTRAR") -> {
                    System.out.println("-> Executando: Cadastrar instrumento");
                }
                case String s when s.equals("2") || s.contains("LISTAR") -> {
                    System.out.println("-> Executando: Listar instrumentos");
                }
                case String s when s.equals("3") || s.contains("BUSCAR") -> {
                    System.out.println("-> Executando: Buscar instrumento");
                }
                case String s when s.equals("4") || s.contains("VENDA") -> {
                    System.out.println("-> Executando: Realizar venda");
                }
                case String s when s.equals("5") || s.contains("REPOR") || s.contains("ESTOQUE") && !s.contains("RELATÓRIO") -> {
                    System.out.println("-> Executando: Repor estoque");
                }
                case String s when s.equals("6") || s.contains("RELATÓRIO") || s.contains("RELATORIO") -> {
                    System.out.println("-> Executando: Relatório do estoque");
                }
                case String s when s.equals("0") || s.contains("SAIR") -> {
                    System.out.println("-> Saindo do sistema...");
                    scanner.close();
                    System.exit(0);
                }
                case null -> {
                    System.out.println("Entrada inválida (nula)!");
                }
                default -> {
                    System.out.println("Opção incorreta! Tente novamente.");
                }
            }
        } while (true);
    }

    public static String menuPrincipal() {
        String[] menu = new String[7];
        menu[0] = "Sair";
        menu[1] = "Cadastrar instrumento";
        menu[2] = "Listar instrumentos";
        menu[3] = "Buscar instrumento";
        menu[4] = "Realizar venda";
        menu[5] = "Repor estoque";
        menu[6] = "Relatório do estoque";

        System.out.println("--------------------------------------------------------------");

        System.out.println("OPÇÃO    DESCRIÇÃO ");
        for (int i = 1; i <= menu.length; i++) {
            int indice = i % menu.length;
            System.out.println("(" + indice + ")" + " — " + menu[indice]);
        }
        System.out.println("--------------------------------------------------------------");

        System.out.println("Digite uma das opções do MENU");
        return scanner.nextLine();
    }
}
