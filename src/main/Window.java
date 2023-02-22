package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sqls.Function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	    //BOTÃO
	    JButton mapear = new JButton("Map");
	    
	    //TEXTOS
		JLabel texthost = new JLabel("Host:");
		JLabel textport = new JLabel("Port:");
		JLabel textuser = new JLabel("User:");
		JLabel textpassw = new JLabel("Password:");
		JLabel textdbase = new JLabel("DBase:");
		JLabel textschema = new JLabel("Schema:");
		JLabel textsystem = new JLabel("System:");
		JLabel sgbd = new JLabel("SGBD:");
		
		//CAMPOS DE TEXTO
		JTextField host = new JTextField(30);
		JTextField port = new JTextField(30);
		JTextField user = new JTextField(30);
		JTextField passw = new JTextField(30);
		JTextField dbase = new JTextField(30);
		JTextField schema = new JTextField(30);
		JTextField system = new JTextField(30);
		
		//CHECKBOX
		JCheckBox DDL = new JCheckBox("Generates DDL File.",false);
		JCheckBox models = new JCheckBox("Generates sql models.",false);
		JCheckBox postgre = new JCheckBox("Postgres",false);
		JCheckBox mysql = new JCheckBox("MySQL",false);
		JCheckBox mssql = new JCheckBox("MSSQL",false);
			
		public Window() {
			
			//ADICIONA AS CAIXAS DE TEXTO E OS BOTÕES
			setTitle("MigrationTool");
			setSize(540,710);
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //FECHA O PROGRAMA AO FECHAR JANELA
			setLocationRelativeTo(null); //CENTRALIZA
			setResizable(false); //NÃO PERMITE REDIMENSIONAR
			setVisible(true);
			
			//PERMITE QUE VOCÊ MESMO REDIMENSIONE OS OBJETOS
			setLayout(null);
			
			//ADICIONA O BOTÃO
			mapear.setBounds(250, 590, 100, 60);
			add(mapear);
			mapear.addActionListener(this);
			
			//CONFIGURA AS CAIXAS DE TEXTO
			host.setBounds(180,50,250,30);
			port.setBounds(180,100,250,30);
			user.setBounds(180,150,250,30);
			passw.setBounds(180,200,250,30);
			dbase.setBounds(180,250,250,30);
			schema.setBounds(180,300,250,30);
			system.setBounds(180,350,250,30);
			
			//ADICIONA AS CAIXAS DE TEXTO
			add(host);
			add(port);
			add(user);
			add(passw);
			add(dbase);
			add(schema);
			add(system);
			
			//REDIMENSIONA OS CHECKBOX
			DDL.setBounds(180, 390, 250, 30);
			models.setBounds(180, 420, 250, 30);
			postgre.setBounds(180, 490, 250, 30);
			mysql.setBounds(180, 515, 250, 30);
			mssql.setBounds(180, 540, 250, 30);
			
			//ADICIONA OS CHECKBOX
			add(DDL);
			add(models);
			add(postgre);
			add(mysql);
			add(mssql);
			
			//REDIMENSIONA TEXTO
			texthost.setBounds(100,50,250,30);
			textport.setBounds(100,100,250,30);
			textuser.setBounds(100,150,250,30);
			textpassw.setBounds(100,200,250,30);
			textdbase.setBounds(100,250,250,30);
			textschema.setBounds(100,300,250,30);
			textsystem.setBounds(100,350,250,30);
			sgbd.setBounds(100,490,250,30);
			
			//ADICIONA TEXTO
			add(texthost);
			add(textport);
			add(textuser);
			add(textpassw);
			add(textdbase);
			add(textschema);
			add(textsystem);	
			add(sgbd);	
		}	
		
		//DEFINE O QUE ACONTECE QUANDO CLICADO NO BOTÃO MAPEAR
		public void actionPerformed(ActionEvent e) {
		//SE CONECTAR AO BANCO INFORMADO
			//SE FOR POSTGRESQL
			if (postgre.isSelected()) {
				Connection conexao = ConnectionDB.getConexao(host.getText(), user.getText(), passw.getText(), port.getText(), dbase.getText(), "postgresql");
				Statement stmt;
				try {
					stmt = conexao.createStatement();
					stmt.execute(Function.getFunction());
					String sql = "SELECT mapping_tool('" + schema.getText() + "');";
					stmt.execute(sql);
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			} //SE FOR MYSQL
			else if (mysql.isSelected()) {
				Connection conexao = ConnectionDB.getConexao(host.getText(), user.getText(), passw.getText(), port.getText(), dbase.getText(), "mysql");
				Statement stmt;
				try {
					stmt = conexao.createStatement();
					stmt.execute(Function.getFunction());
					String sql = "SELECT mapping_tool('" + schema.getText() + "');";
					stmt.execute(sql);
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			} //SE FOR MSSQL
			else if (mssql.isSelected()) {
				Connection conexao = ConnectionDB.getConexao(host.getText(), user.getText(), passw.getText(), port.getText(), dbase.getText(), "sqlserver");
				Statement stmt;
				try {
					stmt = conexao.createStatement();
					stmt.execute(Function.getFunction());
					String sql = "SELECT mapping_tool('" + schema.getText() + "');";
					stmt.execute(sql);
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			};
		
		//CASO O USUÁRIO SELECIONE ALGUM DOS TRÊS, CRIAR PASTA:
			if (DDL.isSelected() || models.isSelected()) {
				//CRIAR PASTA
				Files.criarpasta(system.getText().toUpperCase(),DDL.isSelected(),models.isSelected());
			};
			if (DDL.isSelected()) {
				Connection conexao = ConnectionDB.getConexao(host.getText(), user.getText(), passw.getText(), port.getText(), dbase.getText(), "postgresql");
				Statement stmt = null;
				try {
					stmt = conexao.createStatement();
					String sql = "SELECT * FROM mapping_tool.ddl";
					ResultSet resultado = stmt.executeQuery(sql);
					String ddl = resultado.getString("ddl");
					try {
						 FileWriter ddltxt = new FileWriter("/home/YOUR_FOLDER/" + system.getText() + "/DDL/DDL.txt");
						 @SuppressWarnings("resource")
						 PrintWriter gravarArq = new PrintWriter(ddltxt);
						 gravarArq.printf(ddl);
					} catch (IOException e2) {
						e2.printStackTrace();
					}	
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			};
		//FINALIZAR
		JOptionPane.showMessageDialog(null, "Mapped!");
		}
}
