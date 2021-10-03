package BancoDeDados;

import java.sql.*;
public class onePixelDAO {

	public pixelGetSet pixel;
	public connectionDB bd;
	public PreparedStatement statement;
	public ResultSet resultado;

	public static final byte INCLUSAO = 1;
	public static final byte ALTERACAO = 2;
	public static final byte EXCLUSAO = 3;

	private String sql = "", men;

	public onePixelDAO() {
		pixel = new pixelGetSet();
		bd = new connectionDB();
	}

	public boolean selectAll() {
		sql = "SELECT * FROM user";
		try {
			statement = bd.c.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();

			System.out.println("Tudo Selecionado");
			return true;
		} catch (SQLException erro) {
			System.out.println("ERRO");
			return false;
		}
	}

	public boolean buscarIdPlayer() {
		sql = "SELECT user_id FROM user WHERE user_name = ?";
		try {
			statement = bd.c.prepareStatement(sql);
			statement.setString(1, pixel.getName());
			resultado = statement.executeQuery();
			resultado.next();

			// Colocando o resultado da busca no get e set

			pixel.setId(resultado.getString(1));

			System.out.println("ID do Player Coletado");
			return true;
		} catch (SQLException erro) {
			System.out.println("ERRO");
			return false;
		}
	}

	public boolean buscar() {
		// select * from user INNER JOIN inventario on inventario.user_id = user.user_id
		// WHERE user.user_id = 1 ;
		buscarIdPlayer();
		sql = "SELECT * FROM user INNER JOIN inventario on inventario.user_id = user.user_id WHERE user.user_id = ?";
		try {
			statement = bd.c.prepareStatement(sql);
			statement.setString(1, pixel.getId());
			resultado = statement.executeQuery();
			resultado.next();

			// Colocando o resultado da busca no get e set

			pixel.setId(resultado.getString(1));
			pixel.setName(resultado.getString(2));
			pixel.setGenero(resultado.getString(3));
			pixel.setCheckpoint(resultado.getString(4));
			pixel.setPixelR(resultado.getInt(6));
			pixel.setPixelG(resultado.getInt(7));
			pixel.setPixelB(resultado.getInt(8));
			pixel.setAliado1(resultado.getInt(9));
			pixel.setAliado2(resultado.getInt(10));

			System.out.println("Dados do Player Coletados com Sucesso");
			return true;
		} catch (SQLException erro) {
			System.out.println("ERRO");
			return false;
		}
	}

	public String atualizar(int operacao) {
		men = "Operação realizada com sucesso ";
		try {
			if (operacao == INCLUSAO) {
				sql = "INSERT INTO user(user_name,user_genero,checkpoint) values(?,?,?)";
				statement = bd.c.prepareStatement(sql);
				statement.setString(1, pixel.getName());
				statement.setString(2, pixel.getGenero());
				statement.setString(3, pixel.getCheckpoint());
				System.out.println("Inserido com sucesso");
				
			} else if (operacao == ALTERACAO) {
				sql = "UPDATE user set checkpoint = ? WHERE user_id = ? "; // , aliado1
																											// = ? ,
																											// aliado2 =
																											// ?
				statement = bd.c.prepareStatement(sql);
				statement.setString(1, pixelGetSet.getUpdateCheck());
				statement.setString(2, pixel.getId());
				System.out.println("Alteração user");
			} else if (operacao == EXCLUSAO) {
				sql = "DELETE from user WHERE user_id  = ?";
				statement = bd.c.prepareStatement(sql);
				statement.setString(1, pixel.getId());
			}
			if (statement.executeUpdate() == 0) {
				men = "Falha na operação";
			}
		} catch (SQLException erro) {
			men = "Falha na operação " + erro.toString();
		}
		if (operacao == INCLUSAO) {
			atualizarInventario(INCLUSAO);
		}
		return men;

	}

	public String atualizarInventario(int operacao) {
		men = "Operação realizada com sucesso (Inventário)";
		try {
			if (operacao == INCLUSAO) {
				sql = "INSERT INTO inventario(user_id,pixel_R,pixel_G,pixel_B,aliado1,aliado2) values((SELECT max(user_id) from user),?,?,?,?,?)";
				statement = bd.c.prepareStatement(sql);
//    			statement.setString(1,pixel.getId());
				statement.setInt(1, pixel.getPixelR());
				statement.setInt(2, pixel.getPixelG());
				statement.setInt(3, pixel.getPixelB());
				statement.setInt(4, pixel.getAliado1());
				statement.setInt(5, pixel.getAliado2());
				System.out.println("Inventario inserido com sucesso");

			} else if (operacao == ALTERACAO) {
				sql = "UPDATE inventario set pixel_R = ?,pixel_G = ?,pixel_B = ?,aliado1 = ?,aliado2 = ? WHERE user_id = ? ";
				statement = bd.c.prepareStatement(sql);
				statement.setInt(1, pixel.getPixelR());
				statement.setInt(2, pixel.getPixelG());
				statement.setInt(3, pixel.getPixelB());
				statement.setInt(4, pixel.getAliado1());
				statement.setInt(5, pixel.getAliado2());
				statement.setString(6, pixel.getId());
				System.out.println("Update no inventario feito com Sucesso!");
				
			} else if (operacao == EXCLUSAO) {
				sql = "DELETE from inventario WHERE user_id = ?";
				statement = bd.c.prepareStatement(sql);
				statement.setString(1, pixel.getId());
			}
			if (statement.executeUpdate() == 0) {
				men = "Falha na operação";
			}
		} catch (SQLException erro) {
			// TODO: handle exception
		}
		return men;
	}

}