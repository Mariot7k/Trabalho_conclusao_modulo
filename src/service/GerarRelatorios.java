package service;

import model.Colaborador;
import model.RegistroColheita;
import model.Talhao;

public class GerarRelatorios {

    public static void exibirAcertoQuinzena(Colaborador[] equipe, int qtdColab, RegistroColheita[] colheitas, int qtdColheitas) {
        
        System.out.println("\n=====================================================");
        System.out.println("          RELATÓRIO: ACERTO DA QUINZENA");
        System.out.println("=====================================================");

        // Validação de segurança
        if (qtdColab == 0) {
            System.out.println("Nenhum colaborador cadastrado no sistema.");
            System.out.println("=====================================================\n");
            return;
        }

        // Cabeçalho da tabela do relatório
        System.out.println("Matrícula | Nome                 | Contrato | Total Colhido");
        System.out.println("-------------------------------------------------------------");

        double totalGeralFazenda = 0; // Opcional: Soma de todos os funcionários juntos

        // 1º Laço: Percorre a lista de funcionários um por um
        for (int i = 0; i < qtdColab; i++) {
            
            double somaLitrosFuncionario = 0; // Zera o contador para o funcionário atual da fila

            // 2º Laço: Varre TODAS as colheitas procurando as que pertencem a este funcionário
            for (int j = 0; j < qtdColheitas; j++) {
                if (colheitas[j].matriculaFuncionario == equipe[i].matricula) {
                    somaLitrosFuncionario += colheitas[j].quantidadeLitros;
                }
            }

            // Soma no total geral da fazenda
            totalGeralFazenda += somaLitrosFuncionario;

            // Imprime a linha do funcionário atual
            System.out.println(equipe[i].matricula + "      | " + 
                               equipe[i].nome + " | " + 
                               equipe[i].tipoContrato + " | " + 
                               somaLitrosFuncionario + " Litros");
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("TOTAL GERAL DA QUINZENA: " + totalGeralFazenda + " Litros");
        System.out.println("=====================================================\n");
    }

    // Nova função para buscar e exibir o extrato de apenas UM colaborador
    public static void exibirAcertoIndividual(Colaborador[] equipe, int qtdColab, RegistroColheita[] colheitas, int qtdColheitas, int matriculaBuscada) {
        
        System.out.println("\n=====================================================");
        System.out.println("             EXTRATO INDIVIDUAL DE COLHEITA");
        System.out.println("=====================================================");

        // 1. Busca o funcionário na equipe
        Colaborador funcionarioEncontrado = null;
        for (int i = 0; i < qtdColab; i++) {
            if (equipe[i].matricula == matriculaBuscada) {
                funcionarioEncontrado = equipe[i];
                break; // Achou, pode parar de procurar
            }
        }

        // Se não achou, avisa e aborta o relatório
        if (funcionarioEncontrado == null) {
            System.out.println("[ERRO] Matrícula " + matriculaBuscada + " não encontrada no sistema.");
            System.out.println("=====================================================\n");
            return;
        }

        // 2. Se achou, imprime o cabeçalho do recibo
        System.out.println("Funcionário: " + funcionarioEncontrado.nome);
        System.out.println("Matrícula: " + funcionarioEncontrado.matricula);
        System.out.println("Contrato: " + funcionarioEncontrado.tipoContrato);
        System.out.println("-----------------------------------------------------");
        System.out.println("Lançamentos de colheita encontrados:");

        // 3. Varre as colheitas procurando as dessa pessoa específica
        double somaLitros = 0;
        int contagemLancamentos = 0;

        for (int i = 0; i < qtdColheitas; i++) {
            if (colheitas[i].matriculaFuncionario == matriculaBuscada) {
                System.out.println(" -> Data: " + colheitas[i].data + 
                                   " | Talhão: " + colheitas[i].idTalhao + 
                                   " | Volume: " + colheitas[i].quantidadeLitros + "L");
                
                somaLitros += colheitas[i].quantidadeLitros;
                contagemLancamentos++;
            }
        }

        // 4. Rodapé do recibo
        if (contagemLancamentos == 0) {
            System.out.println(" -> Nenhum litro de café registrado para este funcionário nesta quinzena.");
        }
        
        System.out.println("-----------------------------------------------------");
        System.out.println("TOTAL ARRECADADO: " + somaLitros + " Litros");
        System.out.println("=====================================================\n");
    }

    // Método estático atualizado para buscar um Talhão específico
    public static void exibirFechamentoTalhao(Talhao[] talhoes, int qtdTalhoes, RegistroColheita[] colheitas, int qtdColheitas, int idBuscado) {
        
        System.out.println("\n=====================================================");
        System.out.println("          FECHAMENTO DE TALHÃO (ESTIMATIVA VS REAL)");
        System.out.println("=====================================================");

        // 1. Busca o talhão no vetor
        Talhao talhaoEncontrado = null;
        for (int i = 0; i < qtdTalhoes; i++) {
            if (talhoes[i].idTalhao == idBuscado) {
                talhaoEncontrado = talhoes[i];
                break; // Achou, interrompe a busca
            }
        }

        // Se não achou, avisa e aborta
        if (talhaoEncontrado == null) {
            System.out.println("[ERRO] Talhão com ID " + idBuscado + " não foi encontrado no sistema.");
            System.out.println("=====================================================\n");
            return;
        }

        // 2. Se achou, varre as colheitas somando apenas as deste talhão
        double producaoReal = 0;
        int quantidadeLancamentos = 0;

        for (int i = 0; i < qtdColheitas; i++) {
            if (colheitas[i].idTalhao == idBuscado) {
                producaoReal += colheitas[i].quantidadeLitros;
                quantidadeLancamentos++;
            }
        }

        // 3. Lógica matemática de atingimento de meta
        String statusMeta;
        double diferenca = producaoReal - talhaoEncontrado.estimativaProducao;

        if (producaoReal >= talhaoEncontrado.estimativaProducao) {
            statusMeta = "[SUPEROU A META] +" + diferenca + " L";
        } else {
            // Multiplica por -1 para exibir o número positivo na mensagem de falta
            statusMeta = "[ABAIXO DA META] Faltam " + (diferenca * -1) + " L";
        }

        // 4. Exibição limpa e focada no terminal
        System.out.println("Talhão: " + talhaoEncontrado.nome);
        System.out.println("ID: " + talhaoEncontrado.idTalhao);
        System.out.println("Variedade do Café: " + talhaoEncontrado.variedaCafe);
        System.out.println("-----------------------------------------------------");
        System.out.println("Total de lançamentos de carga: " + quantidadeLancamentos);
        System.out.println("Estimativa Inicial: " + talhaoEncontrado.estimativaProducao + " L");
        System.out.println("Produção Realizada: " + producaoReal + " L");
        System.out.println("-----------------------------------------------------");
        System.out.println("RESULTADO FINAL: " + statusMeta);
        System.out.println("=====================================================\n");
    }

    // Método estático para gerar o relatório de Secagem (Dashboard Rápido)
    public static void exibirRelatorioSecagem(RegistroColheita[] colheitas, int qtdColheitas) {
        
        System.out.println("\n=====================================================");
        System.out.println("         RELATÓRIO: DESTINO DE SECAGEM DO CAFÉ");
        System.out.println("=====================================================");

        if (qtdColheitas == 0) {
            System.out.println("Nenhum registro de colheita encontrado no sistema.");
            System.out.println("=====================================================\n");
            return; // Botão de ejetar
        }

        double totalTerreiro = 0;
        double totalSecador = 0;

        // Varre o vetor de colheitas classificando os volumes
        for (int i = 0; i < qtdColheitas; i++) {
            
            // O uso do .equals() é obrigatório para comparar Strings em Java
            if (colheitas[i].destino.equals("Terreiro")) {
                totalTerreiro += colheitas[i].quantidadeLitros;
            } 
            else if (colheitas[i].destino.equals("Secador")) {
                totalSecador += colheitas[i].quantidadeLitros;
            }
        }

        double totalGeral = totalTerreiro + totalSecador;

        // Exibição limpa focada apenas nos totais
        System.out.println("Volume enviado para Terreiro de Cimento: " + totalTerreiro + " L");
        System.out.println("Volume enviado para Secador Mecânico:    " + totalSecador + " L");
        System.out.println("-----------------------------------------------------");
        System.out.println("Volume Total Processado nesta safra:     " + totalGeral + " L");
        
        // Um pequeno bônus analítico usando matemática básica
        if (totalGeral > 0) {
            System.out.println("\nResumo da Operação:");
            if (totalSecador > totalTerreiro) {
                System.out.println("-> A maior parte da safra está dependendo de queima de lenha (Secador).");
            } else if (totalTerreiro > totalSecador) {
                System.out.println("-> A maior parte da safra está secando naturalmente ao sol (Terreiro).");
            } else {
                System.out.println("-> A distribuição de secagem está perfeitamente equilibrada.");
            }
        }
        System.out.println("=====================================================\n");
    }
}