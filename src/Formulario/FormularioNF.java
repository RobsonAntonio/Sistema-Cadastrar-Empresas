package Formulario;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Conexao.Conexao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FormularioNF extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmpresa;
	private JTextField txtId;
	private JTextField txtBuscar;
	private JTable table;
	private JTextField txtNotaFiscal;
	private JTextField txtValorPago;
	private JButton btnGerarRelatorio;
	private JButton btnExcluirItem;
	private JTextField txtData;
	private JTextField txtValorTotal;
	private JButton btnBuscar;
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private JButton btnAlterarItem;
	private JButton btnGerarRelatorioTotal;
	private JLabel lblPontuaçao;
	private int linhaSelecionada;
	Connection conexao = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormularioNF frame = new FormularioNF();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormularioNF() {
		setTitle("RELAT\u00D3RIOS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 153, 522, 106);
		contentPane.add(scrollPane);

		conexao = Conexao.conectar();

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				linhaSelecionada = table.getSelectedRow();
				txtId.setText(table.getValueAt(linhaSelecionada, 0).toString());
				txtEmpresa.setText(table.getValueAt(linhaSelecionada, 1).toString());
				txtNotaFiscal.setText(table.getValueAt(linhaSelecionada, 2).toString());
				txtData.setText(table.getValueAt(linhaSelecionada, 3).toString());
				txtValorTotal.setText(table.getValueAt(linhaSelecionada, 4).toString());
				txtValorPago.setText(table.getValueAt(linhaSelecionada, 5).toString());

				btnCadastrar.setEnabled(false);
				btnGerarRelatorio.setEnabled(true);
				btnExcluirItem.setEnabled(true);
				btnBuscar.setEnabled(false);
				btnGerarRelatorio.setEnabled(false);

			}
		});

		table.setModel(listarDados());
		scrollPane.setViewportView(table);

		txtEmpresa = new JTextField();
		txtEmpresa.setBounds(145, 54, 392, 20);
		contentPane.add(txtEmpresa);
		txtEmpresa.setColumns(10);

		JLabel lblNewLabel = new JLabel("EMPRESA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(145, 29, 94, 20);
		contentPane.add(lblNewLabel);

		JLabel lblIdEmpresa = new JLabel("ID");
		lblIdEmpresa.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIdEmpresa.setBounds(15, 29, 125, 20);
		contentPane.add(lblIdEmpresa);

		JLabel lblData = new JLabel("DATA");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblData.setBounds(145, 80, 77, 20);
		contentPane.add(lblData);

		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setBounds(15, 54, 125, 20);
		contentPane.add(txtId);

		JLabel lblValorTotalNf = new JLabel("VALOR TOTAL NF");
		lblValorTotalNf.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblValorTotalNf.setBounds(290, 80, 107, 20);
		contentPane.add(lblValorTotalNf);

		txtBuscar = new JTextField();
		txtBuscar.setColumns(10);
		txtBuscar.setBounds(145, 5, 392, 20);
		contentPane.add(txtBuscar);

		JLabel lblNNota = new JLabel("NOME DA EMPRESA");
		lblNNota.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNNota.setBounds(15, 6, 185, 20);
		contentPane.add(lblNNota);

		btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = txtBuscar.getText();
				try {
					Conexao conexao = new Conexao();
					String sql = "SELECT * FROM cadastro WHERE Nome_Empresa = ?";
					PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
					pstmt.setString(1, nome);

					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						txtEmpresa.setText(rs.getString("Nome_Empresa"));
						txtId.setText(rs.getString("ID_Cadastro"));

					}
					JOptionPane.showMessageDialog(null, "Busca com sucesso!");

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				btnCadastrar.setEnabled(false);
				btnExcluirItem.setEnabled(true);
				btnGerarRelatorio.setEnabled(true);
				btnAlterarItem.setEnabled(false);
				btnExcluirItem.setEnabled(false);
				btnGerarRelatorioTotal.setEnabled(false);
				table.setModel(listarDados());

			}

		});
		btnBuscar.setBounds(422, 28, 115, 20);
		contentPane.add(btnBuscar);

		JLabel lblNotaFiscal = new JLabel("NOTA FISCAL");
		lblNotaFiscal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNotaFiscal.setBounds(15, 79, 125, 20);
		contentPane.add(lblNotaFiscal);

		txtNotaFiscal = new JTextField();
		txtNotaFiscal.setColumns(10);
		txtNotaFiscal.setBounds(15, 104, 100, 20);
		contentPane.add(txtNotaFiscal);

		JLabel lblValorPago = new JLabel("VALOR PAGO");
		lblValorPago.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblValorPago.setBounds(440, 80, 97, 20);
		contentPane.add(lblValorPago);

		txtValorPago = new JTextField();
		txtValorPago.setColumns(10);
		txtValorPago.setBounds(440, 104, 97, 20);
		contentPane.add(txtValorPago);

		btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double id = Double.parseDouble(txtId.getText());
				String empresa = txtEmpresa.getText();
				String data = txtData.getText();
				double nota = Double.parseDouble(txtNotaFiscal.getText());
				double valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
				double valorTotal = Double.parseDouble(txtValorTotal.getText().replace(",", "."));

				try {
					Conexao conexao = new Conexao();
					String sql = "INSERT INTO cadastro (ID_Cadastro, Nome_Empresa, Nota_fiscais, Data, Valor_NF, Valor_Pago) VALUES (?,?,?,?,?,?)";
					PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
					SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date d = sdt.parse(data); 
					java.sql.Date sqlDate = new java.sql.Date(d.getTime());
					pstmt.setDouble(1, id);
					pstmt.setString(2, empresa);
					pstmt.setDouble(3, nota);
					pstmt.setDate(4, sqlDate);
					pstmt.setDouble(5, valorTotal);
					pstmt.setDouble(6, valorPago);
					pstmt.execute();
					JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Falha ao cadastrar usuário!");
				}
				txtId.setText("");
				txtEmpresa.setText("");
				txtNotaFiscal.setText("");
				txtValorPago.setText("");
				txtData.setText("");
				txtValorTotal.setText("");
				table.setModel(listarDados());

			}

		});
		btnCadastrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCadastrar.setBounds(15, 128, 125, 20);
		contentPane.add(btnCadastrar);

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnCadastrar.setEnabled(true);
				btnExcluirItem.setEnabled(true);
				btnGerarRelatorio.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnAlterarItem.setEnabled(true);
				btnGerarRelatorioTotal.setEnabled(true);
				
				txtBuscar.setText("");
				txtId.setText("");
				txtEmpresa.setText("");
				txtNotaFiscal.setText("");
				txtValorPago.setText("");
				txtData.setText("");
				txtValorTotal.setText("");
			}

		});
		btnCancelar.setBounds(145, 128, 115, 20);
		contentPane.add(btnCancelar);

		btnGerarRelatorio = new JButton("GERAR RELAT\u00D3RIO EMPRESA");
		btnGerarRelatorio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					try {

						HashMap<String, Object> filtro = new HashMap<String, Object>();
						filtro.put("ID_Cadastro", Integer.parseInt(txtId.getText()));

						JasperPrint print = JasperFillManager
								.fillReport("C:/Projetos Jaspersoft/MyReports/Formulario.jasper", filtro, conexao);

						JasperViewer.viewReport(print, false);

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}

			}
		});
		btnGerarRelatorio.setBounds(265, 128, 272, 20);
		contentPane.add(btnGerarRelatorio);

		btnExcluirItem = new JButton("EXCLUIR ITEM");
		btnExcluirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opçao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?");
				if (opçao == 0) {
					int codigo = Integer.parseInt(txtNotaFiscal.getText());
					try {
						Conexao conexao = new Conexao();
						String sql = "DELETE FROM cadastro WHERE Nota_Fiscais = ?";
						PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
						pstmt.setInt(1, codigo);
						pstmt.execute();

						JOptionPane.showMessageDialog(null, "Usuário Excluido com sucesso!");

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Falha ao Excluir o susuário!");
					}

					txtId.setText("");
					txtEmpresa.setText("");
					txtNotaFiscal.setText("");
					txtValorPago.setText("");
					txtData.setText("");
					txtValorTotal.setText("");
					table.setModel(listarDados());

				}
				txtId.setText("");
				txtEmpresa.setText("");
				txtNotaFiscal.setText("");
				txtValorPago.setText("");
				txtData.setText("");
				txtValorTotal.setText("");
				table.setModel(listarDados());

			}
		});
		btnExcluirItem.setBounds(251, 263, 137, 20);
		contentPane.add(btnExcluirItem);

		txtData = new JTextField();
		txtData.setColumns(10);
		txtData.setBounds(145, 104, 115, 20);
		contentPane.add(txtData);

		txtValorTotal = new JTextField();
		txtValorTotal.setColumns(10);
		txtValorTotal.setBounds(290, 104, 115, 20);
		contentPane.add(txtValorTotal);

		btnAlterarItem = new JButton("ALTERAR ITEM");
		btnAlterarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opçao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Alterar?");
				if (opçao == 0) {

					double id = Double.parseDouble(txtId.getText());
					String empresa = txtEmpresa.getText();
					String data = txtData.getText();
					double nota = Double.parseDouble(txtNotaFiscal.getText());
					double valorPago = Double.parseDouble(txtValorPago.getText().substring(3, txtValorPago.getText().length()).replace(",", "."));
					double valorTotal = Double.parseDouble(txtValorTotal.getText().substring(3, txtValorTotal.getText().length()).replace(",", "."));

					try {
						Conexao conexao = new Conexao();
						String sql = "UPDATE cadastro SET ID_Cadastro = ?, Nome_Empresa = ?, Nota_fiscais = ?, Data = ?, Valor_NF = ?, Valor_Pago = ? WHERE ID_Cadastro = ?";
						PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						java.util.Date x = sdf.parse(data);
						java.sql.Date y = new Date(x.getTime());
						pstmt.setDouble(1, id);
						pstmt.setString(2, empresa);
						pstmt.setDouble(3, nota);
						pstmt.setDate(4, y);
						pstmt.setDouble(5, valorTotal);
						pstmt.setDouble(6, valorPago);
						pstmt.setDouble(7, id);
						pstmt.execute();
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null,e2.getMessage());
					}
					txtId.setText("");
					txtEmpresa.setText("");
					txtNotaFiscal.setText("");
					txtValorPago.setText("");
					txtData.setText("");
					txtValorTotal.setText("");
					table.setModel(listarDados());

				}

			}
		});
		btnAlterarItem.setBounds(393, 263, 144, 20);
		contentPane.add(btnAlterarItem);

		lblPontuaçao = new JLabel("");
		lblPontuaçao.setHorizontalAlignment(SwingConstants.LEFT);
		lblPontuaçao.setBounds(216, 80, 54, 20);
		contentPane.add(lblPontuaçao);
		
		btnGerarRelatorioTotal = new JButton("GERAR RELAT\u00D3RIO TOTAL");
		btnGerarRelatorioTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					try {

						JasperPrint print = JasperFillManager
								.fillReport("C:/Projetos Jaspersoft/MyReports/total.jasper", null, conexao);

						JasperViewer.viewReport(print, false);

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}

			}
			
			
		});
		btnGerarRelatorioTotal.setBounds(15, 263, 233, 20);
		contentPane.add(btnGerarRelatorioTotal);

	}

	private static DefaultTableModel listarDados() {

		DefaultTableModel dados = new DefaultTableModel();

		dados.addColumn("ID CADASTRO");
		dados.addColumn("EMPRESA");
		dados.addColumn("NF");
		dados.addColumn("DATA");
		dados.addColumn("VALOR NF");
		dados.addColumn("VALOR PAGO");

		try {
			Conexao conexao = new Conexao();
			String sql = "select * from cadastro";
			Statement stmt = conexao.conectar().createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat df = new DecimalFormat("0.00");

			while (rs.next()) {
				dados.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), sdf.format(rs.getDate(4)),
						"R$ " + df.format(rs.getDouble(5)), "R$ " + df.format(rs.getDouble(6)) });
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Nao foi possivel carregar dados da tabela!");
		}

		return dados;
	}
	private double resultadoSoma() {

		double pago = Double.parseDouble(txtValorPago.getText());
		double total = Double.parseDouble(txtValorTotal.getText());
		double resto = total - pago;
		return resto;

	}
	private void valor() {

		double pago = Double.parseDouble(txtValorPago.getText());
		double total = Double.parseDouble(txtValorTotal.getText());
		double valorAcumulado = 0;
		double pontosPositivos = 2;
		double pontosNegativos = 4;
		double pontosMax = 100;

		if (pago == total) {
			for (int i = 50; i < pontosMax; i++) {

				valorAcumulado = valorAcumulado + resultadoSoma();
				valorAcumulado = valorAcumulado + (valorAcumulado * pontosPositivos) / 100;

				DecimalFormat formato = new DecimalFormat("#.00");
			}
		} else if (pago < total) {

			valorAcumulado = valorAcumulado + resultadoSoma();
			valorAcumulado = valorAcumulado + (valorAcumulado * pontosNegativos) / 100;
			DecimalFormat formato = new DecimalFormat("#.00");

			lblPontuaçao.setText(formato.format(valorAcumulado));

		}

	}
}
