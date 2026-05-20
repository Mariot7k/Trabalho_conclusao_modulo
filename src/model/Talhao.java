package model;
import java.util.Scanner;

public class Talhao {
    public int idTalhao;
    public String nome; 
    public String variedaCafe;
    public double estimativaProducao;

    // Método estático de fabricação que valida a duplicidade do ID (Chave Primária)
    public static Talhao cadastrarTalhao(Scanner input, Talhao[] talhoes, int qtdTalhoes) {
        Talhao novoTalhao = new Talhao();

        System.out.println("\n=== NOVO CADASTRO DE TALHÃO ===");

        // 1. Entrada e Validação do ID do Talhão (Chave Primária)
        boolean idDuplicado;
        do {
            idDuplicado = false; // Reseta a flag
            System.out.print("Digite o código/ID do talhão (apenas números): ");
            novoTalhao.idTalhao = input.nextInt();
            input.nextLine(); // Limpeza do buffer

            // Varre o vetor de talhões até onde existem dados cadastrados
            for (int i = 0; i < qtdTalhoes; i++) {
                if (talhoes[i].idTalhao == novoTalhao.idTalhao) {
                    idDuplicado = true;
                    System.out.println("[ERRO] Este código de talhão já está cadastrado (" + talhoes[i].nome + ")!");
                    System.out.println("Por favor, digite um código diferente.\n");
                    break; // Interrompe o for ao achar a primeira duplicidade
                }
            }
        } while (idDuplicado);

        // 2. Entrada do Nome do Talhão
        System.out.print("Digite o nome/identificação do talhão: ");
        novoTalhao.nome = input.nextLine();

        // 3. Entrada da Variedade de Café
        System.out.print("Digite a variedade do café (ex: Catuaí, Mundo Novo): ");
        novoTalhao.variedaCafe = input.nextLine();

        // 4. Entrada e Validação da Estimativa de Produção (em litros)
        do {
            System.out.print("Digite a estimativa de produção (em litros): ");
            novoTalhao.estimativaProducao = input.nextDouble();
            input.nextLine(); // Limpeza do buffer após o double

            if (novoTalhao.estimativaProducao <= 0) {
                System.out.println("[ERRO] A estimativa de produção deve ser maior que zero!\n");
            }
        } while (novoTalhao.estimativaProducao <= 0);

        System.out.println("-> Talhão '" + novoTalhao.nome + "' cadastrado com sucesso!");

        return novoTalhao;
    }
}
