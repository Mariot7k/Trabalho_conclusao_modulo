package view;

import java.util.Scanner;

public class SeletorMenu {
    public static int funcaoMenu(Scanner input){
            System.out.println("\n--- MENU FAZENDA ESPERANÇA ---");
            System.out.println("1 - Cadastrar Colaborador");
            System.out.println("2 - Cadastrar Frota");
            System.out.println("3 - Cadastrar Talhão");
            System.out.println("4 - Registrar colheita");
            System.out.println("5 - Gerar Relatórios");
            System.out.println("6 - Rankig de Colaboradores");
            System.out.println("9 - Sair e Salvar");
            System.out.print("Escolha uma opção: ");
       
     try {
       int opcao = Integer.parseInt(input.nextLine());

                switch(opcao) {
                    case 1:
                        System.out.println("Opção selecionada: Cadastrar Colaborador");
                        return opcao;
                    case 2:
                        System.out.println("Opção selecionada: Cadastrar Frota");
                        return opcao;
                    case 3:
                        System.out.println("Opção selecionada: Cadastrar Talhão");
                        return opcao;
                    case 4:
                        System.out.println("Opção selecionada: Registrar colheita");
                        return opcao;
                    case 5:
                        System.out.println("Opção selecionada: Gerar Relatórios");
                        return opcao;
                    case 6:
                        System.out.println("Opcão selecionda: Ranking de colaboradores");
                        return opcao;
                    case 9:
                        System.out.println("Opção selecionada: Sair do sistema");
                        return opcao;
                    default:
                        System.out.println("Opção inválida.");
                        return opcao = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        return 0;
    }

    public static int definirTipoRelatorio(Scanner input) {
        System.out.println("\n--- SUBMENU DE RELATÓRIOS ---");
        System.out.println("1 - Acerto da Quinzena (Geral / Todos os Funcionários)");
        System.out.println("2 - Extrato Individual de Colheita (Por Funcionário)");
        System.out.println("3 - Fechamento do Talhão (Estimativa vs. Produção)");
        System.out.println("4 - Relatório de Secagem (Destinos do Café)");
        System.out.println("5 - Consultar Lista de Talhões (ID e Nome)");
        System.out.println("6 - Consultar Lista da Frota (Placa e Capacidade Max)");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha o relatório desejado: ");

        try {
            int opcao = Integer.parseInt(input.nextLine());
            return opcao; 
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] Digite um número inteiro válido.");
            return -1; 
        }
    }
}
