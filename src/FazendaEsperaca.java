import java.util.Scanner;
import model.Colaborador;
import model.Frota;
import model.RegistroColheita;
import model.Talhao;
import service.ArquivoService;
import service.GerarRelatorios;
import service.Diferencial;
import view.SeletorMenu;

public class FazendaEsperaca {
    public static void main(String[] args) {
        //Cria o sistema de arquivos
        ArquivoService.inicializarSistemaDeArquivos();

        boolean sistemaPronto = ArquivoService.inicializarSistemaDeArquivos();

        // Se a resposta for falsa (deu erro na pasta), aborta a missão!
        if (!sistemaPronto) {
            System.out.println("Encerrando o sistema por segurança para evitar perda de dados...");
            return; // O programa morre aqui. Não lê mais nada para baixo.
        }
        
        Scanner input = new Scanner(System.in);
        
        //Cria os vetores de objetos
        Colaborador[] equipe = new Colaborador[100];
        Frota[] frota = new Frota[100];
        Talhao[] talhoes = new Talhao[100];
        RegistroColheita[] colheitas = new RegistroColheita[500];

        // CARREGA OS DADOS
        // Atribuímos o retorno da função aos nossos contadores principais
        int qtdColaboradores = ArquivoService.carregarColaboradores(equipe);
        int qtdVeiculos = ArquivoService.carregarFrota(frota);
        int qtdTalhoes = ArquivoService.carregarTalhoes(talhoes);
        int qtdColheitas = ArquivoService.carregarColheitas(colheitas);

        System.out.println("Sistema carregado:");
        System.out.println("->  " + qtdColaboradores + " colaboradores prontos.");
        System.out.println("->  " + qtdVeiculos + " veiculos prontos.");
        System.out.println("->  " + qtdTalhoes + " talhões prontos.");
        System.out.println("->  " + qtdColheitas + " colheitas prontas.");
        
        int opcao = 0;
        while (opcao != 9) {
            opcao = SeletorMenu.funcaoMenu(input);

            switch (opcao) {
                case 1:
                    System.out.print("\nQuantos colaboradores você deseja cadastrar de uma vez: ");
                    int qtdCadastrarColaborador = input.nextInt();
                    input.nextLine();

                    // VALIDAÇÃO CRUCIAL: O vetor tem espaço para essa quantidade toda?
                    if (qtdColaboradores + qtdCadastrarColaborador <= equipe.length) {
                        for (int i = 0; i < qtdCadastrarColaborador; i++) {
                            System.out.println("\n--- Cadastro " + (i + 1) + " de " + qtdCadastrarColaborador + " ---");
                            
                            // Chama a função estática para criar 1 colaborador
                            Colaborador recemCadastrado = Colaborador.cadastrarColaborador(input, equipe, qtdColaboradores);
                            
                            // Guarda na posição atual e atualiza o contador principal da Main
                            equipe[qtdColaboradores] = recemCadastrado;
                            qtdColaboradores++; 
                        }
                        
                        System.out.println("\n-> Sucesso! " + qtdCadastrarColaborador + " colaboradores cadastrados.");
                        System.out.println("-> Total na equipe agora: " + qtdColaboradores + "/" + equipe.length);

                    } else {
                        // Se o usuário pedir para cadastrar 30, mas só tiver 10 vagas
                        int espacoLivre = equipe.length - qtdColaboradores;
                        System.out.println("[ERRO] Espaço insuficiente no sistema!");
                        System.out.println("Você tentou cadastrar " + qtdCadastrarColaborador + ", mas só há " + espacoLivre + " vagas disponíveis.");
                    }
                    break;

                case 2:
                    System.out.print("\nQuantos veículos você deseja cadastrar de uma vez: ");
                    int qtdCadastrarVeiculo = input.nextInt();
                    input.nextLine(); // Limpeza de buffer

                    // Validação de limite: o vetor de 100 posições suporta essa quantidade?
                    if (qtdVeiculos + qtdCadastrarVeiculo <= frota.length) {
                        
                        // Loop para realizar a quantidade de cadastros solicitada
                        for (int i = 0; i < qtdCadastrarVeiculo; i++) {
                            System.out.println("\n--- Veículo " + (i + 1) + " de " + qtdCadastrarVeiculo + " ---");
                            
                            // Chama a função estática passando o Scanner, o vetor e o contador atual
                            Frota recemCadastrado = Frota.cadastrarVeiculo(input, frota, qtdVeiculos);
                            
                            // Armazena no vetor e atualiza o índice do contador da Main
                            frota[qtdVeiculos] = recemCadastrado;
                            qtdVeiculos++;
                        }
                        
                        System.out.println("\n-> Sucesso! " + qtdCadastrarVeiculo + " veículos adicionados à frota.");
                        System.out.println("-> Total de veículos no sistema: " + qtdVeiculos + "/" + frota.length);

                    } else {
                        // Alerta caso o usuário tente estourar o tamanho do vetor (100)
                        int vagasDisponiveis = frota.length - qtdVeiculos;
                        System.out.println("[ERRO] Espaço insuficiente no sistema da frota!");
                        System.out.println("Você tentou cadastrar " + qtdCadastrarVeiculo + ", mas o sistema só possui " + vagasDisponiveis + " vagas livres.");
                    }
                    break;

                    case 3:
                        System.out.print("\nQuantos talhões você deseja cadastrar de uma vez: ");
                        int qtdParaCadastrar = input.nextInt();
                        input.nextLine(); // Limpeza de buffer

                        // Validação de limite: o vetor de 100 posições suporta essa quantidade?
                        if (qtdTalhoes + qtdParaCadastrar <= talhoes.length) {
                            
                            // Loop para realizar a quantidade de cadastros solicitada
                            for (int i = 0; i < qtdParaCadastrar; i++) {
                                System.out.println("\n--- Talhão " + (i + 1) + " de " + qtdParaCadastrar + " ---");
                                
                                // Chama a função estática passando o Scanner, o vetor e o contador atual
                                Talhao recemCadastrado = Talhao.cadastrarTalhao(input, talhoes, qtdTalhoes);
                                
                                // Armazena no vetor e atualiza o índice do contador da Main
                                talhoes[qtdTalhoes] = recemCadastrado;
                                qtdTalhoes++;
                            }
                            
                            System.out.println("\n-> Sucesso! " + qtdParaCadastrar + " talhões adicionados ao sistema.");
                            System.out.println("-> Total de talhões no sistema: " + qtdTalhoes + "/" + talhoes.length);

                        } else {
                            // Alerta caso o usuário tente estourar o tamanho do vetor (100)
                            int vagasDisponiveis = talhoes.length - qtdTalhoes;
                            System.out.println("[ERRO] Espaço insuficiente no sistema para talhões!");
                            System.out.println("Você tentou cadastrar " + qtdParaCadastrar + ", mas o sistema só possui " + vagasDisponiveis + " vagas livres.");
                        }
                        break;

                    case 4:
                        if (qtdColheitas < colheitas.length) {
                            
                            // Verifica se há pelo menos 1 item cadastrado em cada categoria base para evitar travamento em loop infinito
                            if (qtdColaboradores == 0 || qtdVeiculos == 0 || qtdTalhoes == 0) {
                                System.out.println("[ERRO] Você precisa cadastrar pelo menos 1 funcionário, 1 trator e 1 talhão antes de registrar colheitas!");
                                break;
                            }

                            RegistroColheita novoLancamento = RegistroColheita.registrarEntrada(
                                input, 
                                equipe, qtdColaboradores, 
                                frota, qtdVeiculos, 
                                talhoes, qtdTalhoes
                            );
                            
                            if (novoLancamento != null) {
                                colheitas[qtdColheitas] = novoLancamento;
                                qtdColheitas++;
                                System.out.println("\n-> Registro de colheita salvo com sucesso!");
                            } 
                            // Se for null, ele apenas sai do 'case' silenciosamente e mostra o menu de novo
                        } else {
                            System.out.println("[ERRO] Memória cheia para novos registros de colheita.");
                        }
                        break;
                    
                    case 5:
                        // Chama a view para mostrar o menu e pegar a escolha do usuário
                        int tipoRelatorio = SeletorMenu.definirTipoRelatorio(input);

                        // Avalia qual relatório foi escolhido e chama o método correspondente
                        switch (tipoRelatorio) {
                            case 1:
                                // 1 - Acerto Geral
                                GerarRelatorios.exibirAcertoQuinzena(equipe, qtdColaboradores, colheitas, qtdColheitas);
                                break;
                                
                            case 2:
                                // 2 - Acerto Individual
                                System.out.print("\nDigite a matrícula do funcionário para a busca: ");
                                try {
                                    int matriculaBuscada = Integer.parseInt(input.nextLine());
                                    GerarRelatorios.exibirAcertoIndividual(equipe, qtdColaboradores, colheitas, qtdColheitas, matriculaBuscada);
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERRO] Matrícula inválida! Digite apenas números.");
                                }
                                break;
                                
                            case 3:
                                // 3 - Fechamento do Talhão (Busca Individual)
                                System.out.print("\nDigite o ID do Talhão que deseja analisar: ");
                                try {
                                    int idTalhaoBusca = Integer.parseInt(input.nextLine());
                                    GerarRelatorios.exibirFechamentoTalhao(talhoes, qtdTalhoes, colheitas, qtdColheitas, idTalhaoBusca);
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERRO] ID inválido! Digite apenas números inteiros.");
                                }
                                break;
                                
                            case 4:
                                // 4 - Relatório de Secagem (Destinos do Café)
                                GerarRelatorios.exibirRelatorioSecagem(colheitas, qtdColheitas);
                                break;
                                
                            case 0:
                                // 0 - Voltar
                                System.out.println("\n-> Voltando ao menu principal...");
                                break;
                                
                            case -1:
                                // Erro de formatação capturado pelo catch no SeletorMenu
                                break;
                                
                            default:
                                System.out.println("\n[ERRO] Opção de relatório inexistente.");
                                break;
                        }
                        break;
                    
                    case 6:
                        Diferencial.exibirRankingColaboradores(equipe, qtdColaboradores, colheitas, qtdColheitas);
                        break;
                        
                    case 9:
                        System.out.println("Encerrando as atividades da Fazenda Esperança...");
                        System.out.println("\n[SISTEMA] Iniciando salvamento de dados...");
                        
                        // Chama a função mestre de salvamento passando todos os vetores e contadores
                        ArquivoService.salvarTodosOsDados(
                            equipe, qtdColaboradores, 
                            frota, qtdVeiculos, 
                            talhoes, qtdTalhoes, 
                            colheitas, qtdColheitas
                        );
                        
                        System.out.println("[SISTEMA] Salvamento concluído com sucesso!");
                        System.out.println("Até a próxima safra!");
                        break;
            }
        }
    }
}
