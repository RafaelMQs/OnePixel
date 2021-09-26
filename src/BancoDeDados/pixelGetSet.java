package BancoDeDados;

public class pixelGetSet {

	static String id, name, genero, checkpoint, updateCheck;
	static int aliado1 = 0, aliado2 = 0, pixelR = 0, pixelG = 0 , pixelB = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}

	public static String getUpdateCheck() {
		return updateCheck;
	}

	public static void setUpdateCheck(String updateCheck) {
		pixelGetSet.updateCheck = updateCheck;
	}

	public int getAliado1() {
		return aliado1;
	}

	public void setAliado1(int aliado1) {
		this.aliado1 = aliado1;
	}

	public int getAliado2() {
		return aliado2;
	}

	public void setAliado2(int aliado2) {
		this.aliado2 = aliado2;
	}

	public int getPixelR() {
		return pixelR;
	}

	public void setPixelR(int pixelR) {
		this.pixelR = pixelR;
	}

	public int getPixelG() {
		return pixelG;
	}

	public void setPixelG(int pixelG) {
		this.pixelG = pixelG;
	}

	public int getPixelB() {
		return pixelB;
	}

	public void setPixelB(int pixelB) {
		this.pixelB = pixelB;
	}

}
