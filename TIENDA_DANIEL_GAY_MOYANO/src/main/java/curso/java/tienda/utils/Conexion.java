package curso.java.tienda.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
	/*
	static String bd = "tienda_curso_serbatic";
	static String login = "root";
	static String password = "";
	static String host = "127.0.0.1";

	static String url = "jdbc:mysql://";
	*/
	static Connection conexion;

	public static boolean crearConexion() {
		String nombre=null, login=null, pass=null, host=null, url=null;
		try {
			String fichero="C:\\Users\\dgay\\OneDrive - Grupo VASS\\Documents\\CursoCC\\TIENDA_DANIEL_GAY_MOYANO\\properties\\bbdd.properties";
			Properties property=new Properties();
			property.load(new FileInputStream(fichero));
			nombre=property.getProperty("bd.nombre");
			login=property.getProperty("bd.login");
			pass=property.getProperty("bd.pass");
			host=property.getProperty("bd.host");
			url=property.getProperty("bd.url");
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion=DriverManager.getConnection(url+host+"/"+nombre,login,pass);
			//conexion = DriverManager.getConnection(url + host + "/" + bd, login, password);
			conexion.setAutoCommit(false);
			System.out.println("conexion establecida");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Connection getConexion() {
		if (conexion == null) {
			crearConexion();
		}
		return conexion;
	}

	public static void desconectar() {
		try {
			conexion.close();
			conexion = null;
			System.out.println("La conexion a la  base de datos ha terminado");

		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
		}
	}
}
