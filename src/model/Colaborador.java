package model;

import java.util.Scanner;

public class Colaborador {
    public String nome;
    public int matricula;   
    public String tipoContrato;

    // Adicionamos o vetor (equipe) e o contador (qtdColaboradores) nos parâmetros
    public static Colaborador cadastrarColaborador(Scanner input, Colaborador[] equipe, int qtdColaboradores) {
        Colaborador novoColaborador = new Colaborador();
        
        System.out.println("\n=== NOVO CADASTRO DE COLABORADOR ===");

        // 1. Entrada do Nome
        System.out.print("Digite o nome do colaborador: ");
        novoColaborador.nome = input.nextLine();

        // 2. Entrada e Validação da Matrícula (Chave Primária)
        boolean matriculaDuplicada;
        do {
            matriculaDuplicada = false; // Reseta a flag a cada tentativa
            
            System.out.print("Digite a matrícula (apenas números): ");
            novoColaborador.matricula = input.nextInt();
            input.nextLine(); // Limpeza de buffer

            // Percorre o vetor APENAS até a quantidade de pessoas já cadastradas
            for (int i = 0; i < qtdColaboradores; i++) {
                if (equipe[i].matricula == novoColaborador.matricula) {
                    matriculaDuplicada = true;
                    System.out.println("[ERRO] Matrícula já cadastrada para o colaborador: " + equipe[i].nome);
                    System.out.println("Por favor, digite uma matrícula diferente.\n");
                    break; // Interrompe o FOR, pois já achou a duplicidade
                }
            }
        } while (matriculaDuplicada); // Fica preso no loop enquanto a matrícula for repetida

        // 3. Entrada e Validação do Tipo de Contrato
        String tipo;
        boolean tipoValido = false;

        do {
            System.out.print("Digite o Tipo de Contrato (Diarista ou Fixo): ");
            tipo = input.nextLine();

            // Ignora maiúsculas e minúsculas para facilitar para o usuário
            if (tipo.equalsIgnoreCase("Diarista") || tipo.equalsIgnoreCase("Fixo")) {
                tipoValido = true;
                // Padroniza a primeira letra maiúscula antes de salvar
                novoColaborador.tipoContrato = tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase();
            } else {
                System.out.println("[ERRO] Contrato inválido! Digite apenas 'Diarista' ou 'Fixo'.\n");
            }

        } while (!tipoValido);

        System.out.println("-> Colaborador(a) " + novoColaborador.nome + " cadastrado(a) com sucesso!");
        
        return novoColaborador;
    }
}


