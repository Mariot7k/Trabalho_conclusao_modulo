package model;
import java.util.Scanner;

public class RegistroColheita {
    public String data;
    public int matriculaFuncionario;
    public int idTalhao;
    public String placaVeiculo;
    public double quantidadeLitros;
    public String destino;

    // Função estática que recebe os 3 vetores base para fazer a validação cruzada
    public static RegistroColheita registrarEntrada(Scanner input, 
                                                    Colaborador[] equipe, int qtdColaboradores, 
                                                    Frota[] frota, int qtdVeiculos, 
                                                    Talhao[] talhoes, int qtdTalhoes) {
                                                        
        RegistroColheita novoRegistro = new RegistroColheita();
        System.out.println("\n=== NOVO REGISTRO DE COLHEITA ===");

        // 0. Data do lançamento
        System.out.print("Data da colheita (ex: 20/05/2026): ");
        novoRegistro.data = input.nextLine();

        // ==========================================
        // REGRA 3 (R3): Validar Funcionário Cadastrado
        // ==========================================
        boolean funcionarioValido = false;
        do {
            System.out.print("Matrícula do funcionário responsável: ");
            novoRegistro.matriculaFuncionario = input.nextInt();
            input.nextLine(); // Limpeza de buffer

            for (int i = 0; i < qtdColaboradores; i++) {
                if (equipe[i].matricula == novoRegistro.matriculaFuncionario) {
                    funcionarioValido = true;
                    System.out.println("-> Funcionário confirmado: " + equipe[i].nome);
                    break;
                }
            }

            if (!funcionarioValido) {
                System.out.println("\n[ERRO - R3] Matrícula não encontrada! O funcionário deve estar cadastrado no sistema.");
                System.out.print("Deseja tentar novamente? (S - Sim / N - Cancelar e voltar ao menu): ");
                
                // Lê a resposta, converte para maiúsculo e pega a primeira letra
                char tentarNovamente = input.nextLine().toUpperCase().charAt(0);
                
                if (tentarNovamente == 'N') {
                    System.out.println("-> Operação cancelada pelo usuário. Retornando ao menu...");
                    return null; // O "Botão de Ejetar"
                }
            }
        } while (!funcionarioValido);

        // ==========================================
        // VALIDAÇÃO EXTRA: Validar Talhão Cadastrado
        // ==========================================
        boolean talhaoValido = false;
        do {
            System.out.print("Código do Talhão de origem: ");
            novoRegistro.idTalhao = input.nextInt();
            input.nextLine(); // Limpeza de buffer

            for (int i = 0; i < qtdTalhoes; i++) {
                if (talhoes[i].idTalhao == novoRegistro.idTalhao) {
                    talhaoValido = true;
                    System.out.println("-> Talhão confirmado: " + talhoes[i].nome);
                    break;
                }
            }

            if (!talhaoValido) {
                System.out.println("\n[ERRO] Código do talhão não encontrado no sistema.");
                System.out.print("Deseja tentar novamente? (S - Sim / N - Cancelar e voltar ao menu): ");
                
                char tentarNovamente = input.nextLine().toUpperCase().charAt(0);
                
                if (tentarNovamente == 'N') {
                    System.out.println("-> Operação cancelada pelo usuário. Retornando ao menu...");
                    return null; // O "Botão de Ejetar"
                }
            }
        } while (!talhaoValido);

        // ==========================================
        // REGRA 1 (R1): Validar Veículo Cadastrado
        // ==========================================
        boolean veiculoValido = false;
        double capacidadeDoVeiculoEncontrado = 0; // Guardaremos a capacidade para usar na R2

        do {
            System.out.print("Placa do veículo (Trator): ");
            novoRegistro.placaVeiculo = input.nextLine().toUpperCase().trim();

            for (int i = 0; i < qtdVeiculos; i++) {
                if (frota[i].placa.equals(novoRegistro.placaVeiculo)) {
                    veiculoValido = true;
                    capacidadeDoVeiculoEncontrado = frota[i].capacidadeMax; // Salva a capacidade
                    System.out.println("-> Veículo confirmado. Capacidade Máx: " + capacidadeDoVeiculoEncontrado + "L");
                    break;
                }
            }

            if (!veiculoValido) {
                System.out.println("\n[ERRO - R1] Carga não pode ser lançada num trator que não existe!");
                System.out.print("Deseja tentar novamente? (S - Sim / N - Cancelar e voltar ao menu): ");
                char tentarNovamente = input.nextLine().toUpperCase().charAt(0);
                
                if (tentarNovamente == 'N') {
                    System.out.println("-> Operação cancelada pelo usuário. Retornando ao menu...");
                    return null; // O "Botão de Ejetar": Cancela a criação e devolve vazio
                }
            }
        } while (!veiculoValido);

        // ==========================================
        // REGRA 2 (R2): Validar Capacidade de Carga
        // ==========================================
        boolean cargaValida = false;
        do {
            System.out.print("Quantidade de litros colhidos exata: ");
            novoRegistro.quantidadeLitros = input.nextDouble();
            input.nextLine(); // Limpeza de buffer

            if (novoRegistro.quantidadeLitros <= 0) {
                System.out.println("[ERRO] A quantidade deve ser maior que zero.");
            } else if (novoRegistro.quantidadeLitros > capacidadeDoVeiculoEncontrado) {
                System.out.println("\n[ERRO - R2] Sobrecarga! O volume (" + novoRegistro.quantidadeLitros + 
                                   "L) é maior que a carreta aguenta (" + capacidadeDoVeiculoEncontrado + "L).");
            } else {
                cargaValida = true; // Só valida se passar por todos os testes
            }

            // Se a carga não for válida por qualquer motivo, oferece a saída
            if (!cargaValida) {
                System.out.print("Deseja corrigir o volume? (S - Sim / N - Cancelar e voltar ao menu): ");
                char tentarNovamente = input.nextLine().toUpperCase().charAt(0);
                
                if (tentarNovamente == 'N') {
                    System.out.println("-> Operação cancelada pelo usuário. Retornando ao menu...");
                    return null; // Aborta o registro de colheita
                }
            }
        } while (!cargaValida);

        // ==========================================
        // VALIDAÇÃO FINAL: Destino do Café
        // ==========================================
        boolean destinoValido = false;
        do {
            System.out.print("Destino (1 - Terreiro de Cimento | 2 - Secador Mecânico): ");
            
            try {
                // Lemos a entrada inteira como texto e tentamos converter para número
                String entradaUsuario = input.nextLine().trim();
                int opcaoDestino = Integer.parseInt(entradaUsuario);

                if (opcaoDestino == 1) {
                    novoRegistro.destino = "Terreiro";
                    destinoValido = true;
                } else if (opcaoDestino == 2) {
                    novoRegistro.destino = "Secador";
                    destinoValido = true;
                } else {
                    System.out.println("\n[ERRO] Número fora das opções. Escolha apenas 1 ou 2.\n");
                }
                
            } catch (NumberFormatException e) {
                // Se o usuário digitar letras, símbolos ou dar Enter vazio, cai aqui e não quebra o sistema!
                System.out.println("\n[ERRO CRÍTICO] Tipo inválido! Por favor, digite apenas um NÚMERO (1 ou 2).\n");
            }

        } while (!destinoValido);

        return novoRegistro;
    }
}