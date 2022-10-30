public class Menus {
    // Visando melhor leitura do código, todos os menus impressos
    // se encontram nesta classe que apenas exibe ao usuário as informações
    // necessárias.
    public static void menuInicial() {

        System.out.printf("\n");
        System.out.printf("\t.---------------------------------------.\n");
        System.out.printf("\t|            Cash3!            \n");
        System.out.printf("\t|---------------------------------------|\n");
        System.out.printf("\t|       » Escolha uma das opções «      |\n");
        System.out.printf("\t|---------------------------------------|\n");
        System.out.printf("\t| 1 - Criar uma nova conta              |\n");
        System.out.printf("\t| 2 - Ler conta                         |\n");
        System.out.printf("\t| 3 - Atualizar conta                   |\n");
        System.out.printf("\t| 4 - Transferir                        |\n");
        System.out.printf("\t| 5 - Deletar conta                     |\n");
        System.out.printf("\t| 6 - Sair                              |\n");
        System.out.printf("\t'---------------------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuNovaConta() {
        System.out.printf("\n");
        System.out.printf("\t.----------------------.\n");
        System.out.printf("\t|      Nova Conta      |\n");
        System.out.printf("\t'----------------------'\n");
        System.out.printf("\n");
    }

    public static void menuTransferencia() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|       Transferência       |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuAtualizacao() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|      Atualizar conta      |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuDeletarConta() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|       Deletar conta       |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaEncontrada() {
        System.out.printf("\n");
        System.out.printf("\t.----------------------------.\n");
        System.out.printf("\t|       Conta encontrada     |\n");
        System.out.printf("\t'----------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaNaoEncontrada() {
        System.out.printf("\n");
        System.out.printf("\t.--------------------------------.\n");
        System.out.printf("\t|       Conta não encontrada     |\n");
        System.out.printf("\t'--------------------------------'\n");
        System.out.printf("\n");
    }

    public static void saldoInsuficiente() {
        System.out.printf("\n");
        System.out.printf("\t.------------------------------.\n");
        System.out.printf("\t|       Saldo insuficiente     |\n");
        System.out.printf("\t'------------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaDeletada() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|       Conta deletada      |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }
}