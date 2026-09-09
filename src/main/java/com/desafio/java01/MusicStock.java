package com.desafio.java01;

import java.util.Scanner;

public class MusicStock {
    private static final int CAPACIDADE_MAXIMA_PRODUTOS = 100;
    private static final Scanner scanner = new Scanner(System.in);

    private static int[] codigos = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static String[] nomes= new String[CAPACIDADE_MAXIMA_PRODUTOS];
    private static double[] precos = new double[CAPACIDADE_MAXIMA_PRODUTOS];
    private static int[] quantidades = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static int indiceAtual;


    public static void main(String[] args) {
        String opcao;

        do {
            opcao = menuPrincipal().toUpperCase().trim();

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
                case String s when s.equals("7") || s.contains("MOVIMENTAÇÕES") -> {
                    System.out.println("-> Executando: MOVIMENTAÇÕES");
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
        String[] menu = new String[8];
        menu[1] = "Cadastrar instrumento";
        menu[2] = "Listar instrumentos";
        menu[3] = "Buscar instrumento";
        menu[4] = "Realizar venda";
        menu[5] = "Repor estoque";
        menu[6] = "Relatório do estoque";
        menu[7] = "Movimentações";
        menu[0] = "Sair";

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
