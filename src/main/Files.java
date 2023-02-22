package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import sqls.Scripts;
import java.io.File;

public class Files {

	public static void criarpasta(String system,boolean ddl, boolean scripts){
		
		File pasta = new File("/home/YOUR_FOLDER/" + system);
		pasta.mkdir();	
		if (ddl) {
			File ddlpasta = new File("/home/YOUR_FOLDER/" + system + "/DDL");
			ddlpasta.mkdir();
		};
		if (scripts) {
			File scriptspasta = new File("/home/YOUR_FOLDER/" + system + "/SQLS");
			scriptspasta.mkdir();
			
			try {
				 FileWriter insert = new FileWriter("/home/YOUR_FOLDER/" + system + "/SQLS/STANDARD_SQL_1.sql");
				 try (PrintWriter gravarArq = new PrintWriter(insert)) {
					@SuppressWarnings("unused")
					PrintWriter printf = gravarArq.printf(Scripts.STANDARD_SQL_1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			try {
				 FileWriter insert = new FileWriter("/home/YOUR_FOLDER/" + system + "/SQLS/STANDARD_SQL_2.sql");
				 try (PrintWriter gravarArq = new PrintWriter(insert)) {
					@SuppressWarnings("unused")
					PrintWriter printf = gravarArq.printf(Scripts.STANDARD_SQL_2);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			try {
				 FileWriter insert = new FileWriter("/home/YOUR_FOLDER/" + system + "/SQLS/STANDARD_SQL_3.sql");
				 try (PrintWriter gravarArq = new PrintWriter(insert)) {
					@SuppressWarnings("unused")
					PrintWriter printf = gravarArq.printf(Scripts.STANDARD_SQL_3);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		};
	};
}
