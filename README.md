
## 🤖 Diário da IA (Relatório de Desenvolvimento)

Nesta seção, documentamos o processo de cocriação do sistema com o auxílio de Inteligência Artificial, destacando o alinhamento com as restrições pedagógicas da disciplina.

---

### 1. Quais ferramentas e bibliotecas proibidas a IA tentou incluir?

A IA tentou utilizar estruturas de dados e classes de manipulação de arquivos que otimizariam o código, mas que violavam as restrições do projeto por não terem sido lecionadas em sala. As principais foram:

* **Tabelas Hash (`HashSet`):** A IA sugeriu o uso de `HashSet` para verificar a duplicidade da chave primária (como matrícula e placa) com complexidade $O(1)$.
* **`java.io.BufferedWriter`:** Para a persistência de dados, a IA recomendou inicialmente esta classe por ser mais eficiente em termos de performance de escrita.
* **Busca Binária (*Binary Search*):** Foi sugerida como uma forma mais rápida de busca em vetores, porém exigiria uma lógica complexa de ordenação prévia a cada inserção.

#### Por que fizemos essas alterações? (Justificativa)
* **Limitação Técnica Pedagógica:** O regulamento do projeto exige o desenvolvimento exclusivo com a lógica e o paradigma ensinados. Usar ferramentas prontas (como `HashSet` ou `List`) "pula" a etapa essencial de aprendizado de algoritmos estruturados.
* **Complexidade Desnecessária:** Embora estruturas avançadas ofereçam melhor desempenho em larga escala, o projeto trabalha com vetores pequenos e de tamanho fixo. Nesses casos, a busca linear com um laço `for` atende perfeitamente aos requisitos e demonstra o domínio sobre o controle de índices na memória.
* **Restrição de Escopo:** A biblioteca `BufferedWriter` não consta nos materiais de apoio disponibilizados ao longo do semestre.

---

### 2. Como foi o prompt usado para obrigar a IA a refatorar o código para o padrão da nossa disciplina?

Para garantir a aderência do código aos critérios de avaliação da banca, utilizamos comandos específicos de restrição de escopo técnico.

**Exemplo de Prompt de Alinhamento Utilizado:**
> "Refatore o código de cadastro de colaboradores. É proibido o uso de Collections (como HashSet ou List) e algoritmos de busca avançados. Utilize apenas vetores de tamanho fixo, laços de repetição (for/while) e busca linear. Para a saída de arquivos, substitua BufferedWriter por PrintWriter, conforme ensinado em sala."

---

### 3. Qual regra de negócio a IA falhou em fazer e o grupo precisou debugar na mão?

Antes do início da geração de código pela IA, o grupo forneceu o levantamento de requisitos realizado em sala, as diretrizes do PDF do trabalho e as principais bibliotecas lecionadas. 

Por se tratar de um sistema baseado estritamente em estruturas básicas, **não houve falhas críticas de lógica por parte da IA nas regras de negócio**. Praticamente não houve necessidade de depuração (*debug*) manual de erros de processamento; a maior parte dos ajustes solicitados concentrou-se na refatoração para decisões de design arquitetural, usabilidade no terminal e adequação restritiva às bibliotecas permitidas.