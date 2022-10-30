import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class conta // Definição do objeto 'Conta' e suas características.
{
	protected int idConta;
	protected String nomePessoa;
	protected int numeroEmails;
	protected List<String> emails = new ArrayList<>();
	protected String nomeUsuario;
	protected String senha;
	protected String cpf;
	protected String cidade;
	protected short transferenciasRealizadas;
	protected float saldo;

	public conta() // Conta genérica para preenchimento.
	{
		this.idConta = -1;
		this.nomePessoa = "";
		this.numeroEmails = 0;
		this.nomeUsuario = "";
		this.senha = "";
		this.cpf = "";
		this.cidade = "";
		this.transferenciasRealizadas = 0;
		this.saldo = 0F;

	}

	// metodo responsável pela criação da conta
	public conta(String cpf, String nomePessoa, String cidade, float saldo, short transferenciasRealizadas,
			int numeroEmails, String[] emails,
			String nomeUsuario, String senha, int idConta) // Conta com os dados providos
	// pelo usuário.
	{
		this.idConta = idConta;
		this.nomePessoa = nomePessoa;
		this.numeroEmails = numeroEmails;
		for (String e : emails) {
			this.emails.add(e);
		}
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
		this.cpf = cpf;
		this.cidade = cidade;
		this.transferenciasRealizadas = transferenciasRealizadas;
		this.saldo = saldo;
	}

	public void transacao(double valor) // Método para realizar transações.
	{
		this.saldo += valor;
	}

	public String toString() // Formatação para impressão.
	{
		return "\n\t.-----------------------. "
				+ "\n\t| Número da conta       | " + idConta
				+ "\n\t| Nome completo         | " + nomePessoa
				+ "\n\t| Nome usuário          | " + nomeUsuario
				+ "\n\t| Cidade                | " + cidade
				+ "\n\t| CPF                   | " + cpf
				+ "\n\t| Saldo                 | " + Conversor.floatParaString(saldo)
				+ "\n\t| N° de transferências  | " + transferenciasRealizadas
				+ "\n\t'-----------------------' "
				+ "\n";
	}

	// Metodos responsável pela escrita/leitura do arquivo
	public byte[] toByteArray() throws IOException {

		ByteArrayOutputStream gerador = new ByteArrayOutputStream();
		DataOutputStream DOS = new DataOutputStream(gerador);

		DOS.writeInt(idConta);
		DOS.writeUTF(nomePessoa);
		DOS.writeInt(numeroEmails);
		for (String e : emails) {
			DOS.writeUTF(e);
		}
		DOS.writeUTF(nomeUsuario);
		DOS.writeUTF(senha);
		DOS.writeUTF(cpf);
		DOS.writeUTF(cidade);
		DOS.writeShort(transferenciasRealizadas);
		DOS.writeFloat(saldo);

		return gerador.toByteArray();
	}

	public void fromByteArray(byte[] byteContas) throws IOException {
		ByteArrayInputStream leitor = new ByteArrayInputStream(byteContas);
		DataInputStream DIS = new DataInputStream(leitor);

		idConta = DIS.readInt();
		nomePessoa = DIS.readUTF();
		numeroEmails = DIS.readInt();
		for (int i = 0; i < numeroEmails; i++) {
			emails.add(DIS.readUTF());
		}
		nomeUsuario = DIS.readUTF();
		senha = DIS.readUTF();
		cpf = DIS.readUTF();
		cidade = DIS.readUTF();
		transferenciasRealizadas = DIS.readShort();
		saldo = DIS.readFloat();
	}
}
