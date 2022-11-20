import java.util.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

public class Funcoes // Módulo das opções possíveis do projeto.
{
	static File arquivo = new File("dadosCompressao_1");
	public static int buscar(int idConta, RandomAccessFile RAF2) throws IOException {
		byte lapide;
		int id;
		long posicao;
		long posicaoRegistro;

		RAF2.seek(0);

		posicao = RAF2.getFilePointer(); // Guarda a posicao do ponteiro no arquivo de indices

		while (posicao < RAF2.length()) {
			try {
				lapide = RAF2.readByte(); // Lê a lapide
				id = RAF2.readInt(); // Lê o ID do registro
				posicaoRegistro = RAF2.readLong(); // Lê posicao do registro

				if (lapide == 0) {
					if (id == idConta) {
						return (int) posicaoRegistro;
					}
				}

				posicao = posicao + 1 + 4 + 8; // Atualiza a posição
				RAF2.seek(posicao); // Vai para a posição
			} catch (EOFException err) {
				err.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	// ... Que por sua vez retorna ao usuário caso não tenha sido encontrada / não
	// existe.

	public static int buscarNoIndice(int idConta, RandomAccessFile RAF2) throws IOException {
		byte lapide;
		int id;
		long posicao;

		RAF2.seek(0);

		posicao = RAF2.getFilePointer();

		while (posicao < RAF2.length()) {
			try {
				lapide = RAF2.readByte(); // Lê a lapide
				id = RAF2.readInt(); // Lê o ID do registro do

				if (lapide == 0) {
					if (id == idConta) {
						return (int) posicao;
					}

				}
				posicao = posicao + 1 + 4 + 8; // Atualiza a posição
				RAF2.seek(posicao); // Vai para a posição
			} catch (EOFException err) {
				err.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	// Metodo de criação de contas que por sua vez faz uma pergunta dos dados da
	// conta em seguida manda para a função que escreve no arquivo, em sequência
	// exibe os dados da conta criada
	public static void cadastro(RandomAccessFile RAF, RandomAccessFile RAF2, Scanner in) throws IOException

	{
		Menus.menuNovaConta();

		System.out.printf("\tCPF: ");
		String cpf = in.nextLine();

		System.out.printf("\tNome: ");
		String nome = in.nextLine();

		System.out.printf("\tNumero de emails: ");
		int numeroEmails = in.nextInt();

		in.nextLine();

		String[] emails = new String[numeroEmails];

		for (int i = 0; i < numeroEmails; i++) {
			System.out.printf("\tEmail %d: ", i + 1);
			emails[i] = in.nextLine();
		}

		System.out.printf("\tNome de usuario: ");
		String nomeUsuario = in.nextLine();

		System.out.printf("\tSenha: ");
		String senha = in.nextLine();

		System.out.printf("\tCidade: ");
		String cidade = in.nextLine();

		System.out.printf("\tSaldo (utilize ','): ");
		float saldo = in.nextFloat();

		System.out.printf("\tTransferências realizadas: ");
		short transferenciasRealizadas = in.nextShort();

		if (RAF.length() == 0) {
			RAF.writeInt(0); // Cabeçalho
		}

		RAF.seek(0); // Volta para o início do arquivo
		int id = RAF.readInt(); // Lê o cabeçalho
		int idProximo = id + 1; // Calcula o próximo ID

		conta C = new conta(cpf, nome, cidade, saldo, transferenciasRealizadas, numeroEmails, emails, nomeUsuario, senha,
				idProximo);

		RAF.seek(0); // Volta para o início do arquivo
		RAF.writeInt(idProximo); // Grava o novo cabeçalho

		byte byteArray[] = C.toByteArray(); // Converte a conta para um array de bytes

		RAF2.seek(RAF2.length()); // Vai para o final do arquivo de indice

		RAF2.writeByte(0); // Grava a lapide no arquivo de indice
		RAF2.writeInt(idProximo); // Grava o id do registro no arquivo de indice
		RAF2.writeLong(RAF.length()); // Grava a posicao do novo registro no arquivo de indice

		RAF.seek(RAF.length()); // Vai para o final do arquivo

		RAF.writeByte(0); // Grava a lapide
		RAF.writeInt(byteArray.length); // Grava o tamanho do registro
		RAF.write(byteArray); // Grava o registro

		System.out.print(C.toString());

		Menus.contaCriada(C.idConta);

	}

	public static conta ler(RandomAccessFile RAF, RandomAccessFile RAF2, Scanner in) throws IOException {

		System.out.printf("\tDigite o ID da conta: "); // Pede o ID da conta
		int idConta = in.nextInt();

		int posicao = buscar(idConta, RAF2);

		if (posicao == -1) {
			Menus.contaNaoEncontrada();
			return null;
		}

		Menus.contaEncontrada();

		RAF.seek(posicao); // Posiciona o ponteiro na posicao do registro

		RAF.readByte(); // Pula a lapide

		int tamanhoRegistro = RAF.readInt(); // Lê o tamanho do registro

		byte byteArray[] = new byte[tamanhoRegistro]; // Cria um array de bytes com o tamanho do registro

		RAF.read(byteArray); // Lê o registro

		conta C = new conta(); // Cria um objeto conta

		C.fromByteArray(byteArray); // Preenche o objeto conta com os dados do registro

		System.out.print(C.toString()); // Exibe os dados da conta

		return C;

	}

	public static String pegaArquivo(){ //Função para transformarmos nosso arquivo de bytes com todas as contas criadas para String
		String textoDoArquivo = "";
		File file = new File("dados.db");
		try {
        	byte[] bytes = Files.readAllBytes(file.toPath());
			
				textoDoArquivo = new String(bytes, "UTF-8");
			}
		 catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
			
			e.printStackTrace();
		}
			
			
			return textoDoArquivo;
        	
	}

	// Função de atualizar conta, que varre o sistema pelo ID informado a fim de
	// alterar alguma informação, após a operação exibe a conta alterada.
	public static boolean atualizar(RandomAccessFile RAF, RandomAccessFile RAF2, Scanner in, String nomeUsuario,
			String cpf,
			String nome,
			int numeroEmails,
			String[] emails, String senha, String cidade, float saldo,
			short transferenciasRealizadas, int idConta) throws IOException {
		Menus.menuAtualizacao();

		long posicao;
		byte byteArray[];
		byte novoArray[];
		int tamanhoRegistro;
		conta newC = new conta();
		conta oldC = new conta();

		RAF.seek(0);
		RAF.readInt();

		posicao = buscar(idConta, RAF2);

		if (posicao == -1) {
			Menus.contaNaoEncontrada();
			return false;
		}

		RAF.seek(posicao); // Posiciona o ponteiro na posicao do registro

		RAF.readByte(); // Lê o byte de remoção

		tamanhoRegistro = RAF.readInt(); // Lê o tamanho do registro

		byteArray = new byte[tamanhoRegistro]; // Cria um array de bytes com o tamanho do registro

		RAF.read(byteArray); // Lê o registro

		oldC.fromByteArray(byteArray); // Cria um objeto conta

		newC = new conta(cpf, nome, cidade, saldo, transferenciasRealizadas, numeroEmails, emails, nomeUsuario, senha,
				idConta);

		System.out.println(newC.toString());

		novoArray = newC.toByteArray(); // Cria um array de bytes com o novo registro

		if (novoArray.length > tamanhoRegistro) {
			deletar(idConta, RAF, RAF2); // Deleta o registro antigo

			RAF2.seek(RAF2.length()); // Posiciona o ponteiro no final do arquivo de indices
			RAF2.writeByte(0); // Escreve o byte de remoção
			RAF2.writeInt(idConta); // Escreve o id do registro
			RAF2.writeLong(RAF.length()); // Escreve posicao do registro

			RAF.seek(RAF.length()); // Posiciona o ponteiro no final do arquivo
			RAF.writeByte(0); // Escreve o byte de remoção
			RAF.writeInt(novoArray.length); // Escreve o tamanho do registro
		} else {
			RAF.seek(posicao); // Posiciona o ponteiro na posicao do registro
			RAF.writeByte(0); // Escreve o byte de remoção
			RAF.readInt(); // Lê o tamanho do registro
		}

		RAF.write(novoArray); // Escreve o novo registro
		return true;
	}

	// Funcao chamada no main para receber o Id da conta que deseja atualizar,
	// receber os novos dados da conta e chama a funçao
	// que atualiza a conta propriamente dito
	public static boolean receberDadosParaAtualizarConta(RandomAccessFile RAF, RandomAccessFile RAF2, Scanner in,
			int idConta)
			throws IOException {
		in.nextLine();

		System.out.printf("\tNome de usuário: "); // Pede o ID da conta
		String nomeUsuario = in.nextLine();

		System.out.printf("\tCPF: ");
		String cpf = in.nextLine();

		System.out.printf("\tNome: ");
		String nome = in.nextLine();

		System.out.printf("\tNumero de emails: ");
		int numeroEmails = in.nextInt();

		in.nextLine();

		String[] emails = new String[numeroEmails];

		for (int i = 0; i < numeroEmails; i++) {
			System.out.printf("\tEmail %d: ", i + 1);
			emails[i] = in.nextLine();
		}

		System.out.printf("\tSenha: ");
		String senha = in.nextLine();

		System.out.printf("\tCidade: ");
		String cidade = in.nextLine();

		System.out.printf("\tSaldo (utilize ','): ");
		float saldo = in.nextFloat();

		System.out.printf("\tTransferências realizadas: ");
		short transferenciasRealizadas = in.nextShort();

		return Funcoes.atualizar(RAF, RAF2, in, nomeUsuario, cpf, nome,
				numeroEmails, emails, senha, cidade,
				saldo,
				transferenciasRealizadas, idConta);
	}

	// Função de Deletar que varre o sistema pelo ID digitado e apaga a conta
	// vinculada a ele adicionando uma lapide no arquivo.
	public static boolean deletar(int idConta, RandomAccessFile RAF, RandomAccessFile RAF2) throws IOException {

		int posicao;
		int posicaoNoIndice;

		posicao = buscar(idConta, RAF2); // Guarda posicao do registro no arquivo principal

		posicaoNoIndice = buscarNoIndice(idConta, RAF2); // Guarda posicao no arquivo de indices

		if (posicao == -1) {
			Menus.contaNaoEncontrada();
			return false;
		}

		RAF.seek(posicao); // Posiciona o ponteiro na posicao do registro
		RAF.writeByte(1); // Escreve a lapide

		RAF2.seek(posicaoNoIndice); // Posiciona o ponteiro no arquivo de indices
		RAF2.writeByte(1); // Escreve a lapide no arquivo de indices

		return true;
	}

	// Metodo de transferencia de saldo entre conta remetente e conta destinatario,
	// realizando uma alteraçao na variavel saldo da conta destino.
	public static boolean transferencia(RandomAccessFile RAF, RandomAccessFile RAF2, Scanner n1) throws IOException {
		Menus.menuTransferencia();

		conta C = new conta(); // Cria um objeto conta
		conta D = new conta(); // Cria um objeto conta

		n1.nextLine();
		System.out.println("Digite o ID da conta de origem:");
		int idConta1 = n1.nextInt();

		int posicao1 = buscar(idConta1, RAF2);

		if (posicao1 == -1) {
			Menus.contaNaoEncontrada();
			return false;
		}

		System.out.println("Digite o ID da conta destinatária:");
		int idConta2 = n1.nextInt();

		int posicao2 = buscar(idConta2, RAF2);

		if (posicao2 == -1) {
			Menus.contaNaoEncontrada();
			return false;
		}

		System.out.println("Digite o valor a ser transferido:");

		double valor = n1.nextDouble();

		RAF.seek(posicao1); // Posiciona o ponteiro no inicio do registro

		RAF.readByte(); // Lê a lapide
		int tamTem = RAF.readInt(); // Lê o tamanho do registro

		byte[] byteArray = new byte[tamTem]; // Cria um array de bytes com o tamanho do registro

		RAF.read(byteArray); // Lê o registro

		C.fromByteArray(byteArray); // Preenche o objeto conta com os dados do registro

		if (valor > C.saldo) {
			Menus.saldoInsuficiente();
			return false;
		}

		RAF.seek(posicao2); // Posiciona o ponteiro no inicio do registro 2

		RAF.readByte(); // Lê a lapide

		tamTem = RAF.readInt(); // Lê o tamanho do registro

		byteArray = new byte[tamTem]; // Cria um array de bytes com o tamanho do registro

		RAF.read(byteArray); // Lê o registro

		D.fromByteArray(byteArray); // Preenche o objeto conta com os dados do registro

		C.transacao(-valor); // Realiza a transação na conta de origem

		D.transacao(valor); // Realiza a transação na conta de destino

		String[] emailsC = new String[C.emails.size()]; // Cria um array de strings com o tamanho do arraylist de emails da
																										// conta de origem
		String[] emailsD = new String[D.emails.size()]; // Cria um array de strings com o tamanho do arraylist de emails da
																										// conta de destino

		for (int i = 0; i < C.emails.size(); i++) {
			emailsC[i] = C.emails.get(i); // Preenche o array de strings com os emails da conta de origem
		}

		for (int i = 0; i < D.emails.size(); i++) {
			emailsD[i] = D.emails.get(i); // Preenche o array de strings com os emails da conta de destino
		}

		Funcoes.atualizar(RAF, RAF2, n1, C.nomeUsuario, C.cpf, C.nomePessoa, C.numeroEmails, emailsC,
				C.senha,
				C.cidade, C.saldo, (short) (C.transferenciasRealizadas + 1), idConta1);

		Funcoes.atualizar(RAF, RAF2, n1, D.nomeUsuario, D.cpf, D.nomePessoa, D.numeroEmails, emailsD,
				D.senha, D.cidade, D.saldo, D.transferenciasRealizadas, idConta2);

		return true;
	}

	//Função para mechermos com compactação e descompactação utilizando o algoritmo de LZW
	public static void LZW(Scanner in){
		
	}
	
	public static List<Integer> codificador(String text) {
		int tamanhoDicionario = 256; //Declaramos o tamanho do dicionario como 2 a oitava para ter uma margem de erro maior.
		
		int n = 1;
		long startCompact = System.currentTimeMillis(); //pega tempo 
		float calculo;
		float tamanhoArquivoCompact = arquivo.length();
		float tamanhoArquivoOriginal = pegaArquivo().length();
		calculo = ((tamanhoArquivoCompact - tamanhoArquivoOriginal)/tamanhoArquivoOriginal) * 100;//Linha responsavel pelo calculo da perda/ganho do arquivo
		Map<String, Integer> dicionario = new HashMap<>(); //Mapeamos o dicionario 
		for (int i = 0; i < tamanhoDicionario ; i++) { //Inserimos espaços em dicionario até batermos a capacidade.
			dicionario.put(String.valueOf((char) i), i);

		}

		String foundChars = ""; //Declaramos uma variavel auxiliar para armazenar os caracteres encontrados para unilos posteriormente
		List<Integer> result = new ArrayList<>(); //Criamos um array para exibir o resultado no fim
		
		for (char caracter : text.toCharArray()) { //Percorremos o dicionario verificando se o caracter existe, se não inserimos diretamente no array de result o codigo daquele caracter se existe armazenamos em "foundChars" e passamos para o proximo para realizar a união
			String charsToAdd = foundChars + caracter;
			if(dicionario.containsKey(charsToAdd)){
				foundChars = charsToAdd;
			
			}
			else{
				result.add(dicionario.get(foundChars));
				dicionario.put(charsToAdd, tamanhoDicionario++);
				foundChars = String.valueOf(caracter);
		}
		
		
	}
	
	//Criação do arquivo compactado para escrita
	try {
		while(arquivo.exists()){
			n++;
			arquivo = new java.io.File("dadosCompressao_"+n);
		}
		FileWriter defineArquivo = new FileWriter(arquivo, false);
		PrintWriter escreveEmArquivo = new PrintWriter(defineArquivo);
		
		
		escreveEmArquivo.print(result.toString());
		escreveEmArquivo.close();
	}catch(IOException e){
		e.printStackTrace();
	}
	//Nas linhas a seguir verificamos o tamanho de ambos arquivos de dados para podermos calcular o ganho ou a perda ao compactar um arquivo.
	if(tamanhoArquivoCompact > tamanhoArquivoOriginal){
		System.out.printf("\t.-------------------------------------.\n");
		System.out.printf("\t.Houve uma perda ao compactar de %.2f" , calculo);
		System.out.println("%!");
		System.out.printf("\t.-------------------------------------.\n");
	}else if(tamanhoArquivoCompact == tamanhoArquivoOriginal){
		System.out.printf("\t.-----------------------------------------.\n");
		System.out.printf("\t.Não houve perda nem ganho na compactação!");
		System.out.printf("\t.-----------------------------------------.\n");
	}else{
		System.out.printf("\t.---------------------------.\n");
		System.out.printf("Houve um ganho de %.2f", calculo);
		System.out.println("%!");
		System.out.printf("\t.---------------------------.\n");
	}

	//Calculo para exibição do tempo de execução do sistema
	long endCompact = System.currentTimeMillis();
	float tempoExec = endCompact - startCompact;

	System.out.printf("\t.-------------------------------------------.\n");
	System.out.printf("\t.Tempo de execução do algoritmo = %.3f ms%n.", tempoExec);
	System.out.printf("\t.-------------------------------------------.\n");
	
	return result;

		
	}

	public static String decodificador(List<Integer> encodedText) { //Em descodificador recebemos como parametro as informações codificadas pela função "codificador"
		Scanner in = new Scanner(System.in);
		int tamanhoDicionario = 256; //Declaramos o tamanho do dicionario como 2 a oitava para ter uma margem de erro maior.
		int n = 1;

		File arquivoSubstituto = new File("dadosDescompressao_1");//Arquivo para substituir os dados originais
		Map<Integer, String> dicionario = new HashMap<>(); //Neste caso precisamos saber os numeros e os caracteres para decodificarmos.
			for (int i = 0; i < tamanhoDicionario; i++){
				dicionario.put(i, String.valueOf((char) i));
			}
	
			String caracteres = String.valueOf((char) encodedText.remove(0).intValue()); //Verificamos caracter por caracter para decodifica-lo e remove-lo do array para constar somente a decodificação
			
			StringBuilder result = new StringBuilder(caracteres);//Construtor de Strings para o resultado
			for(int code : encodedText){ //Verificamos se há ou não o "code" na iteração atual, se sim nós recuperamos a informação dele e jogamos dentro de entry para ficar armazenado, se não pegamos o primeiro caracter da iteração anterior e unimos eles para formar um novo codigo.
				String entry = dicionario.containsKey(code)? dicionario.get(code): caracteres + caracteres.charAt(0);
				result.append(entry); //Atrela o resultado aos valores armazenados em "entry"
				dicionario.put(tamanhoDicionario++, caracteres + entry.charAt(0));//caso necessario aumenta o tamanho do dicionario e concatena os caracteres com os "entry"
				caracteres = entry; //caracteres recebe os valores de "entry"
			}
			
			arquivo.renameTo(arquivoSubstituto);
			try {
				FileWriter defineArquivo;
				defineArquivo = new FileWriter(arquivoSubstituto, false);
				PrintWriter escreveEmArquivo = new PrintWriter(defineArquivo);
				//Parte responsavel por sobrepor as informações do arquivo original.
				escreveEmArquivo.print(result.toString());
				escreveEmArquivo.close();
	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		return result.toString(); //o resultado é transformado em String para vizualização
	}
	
}