package OnePixel;

import BancoDeDados.*;

public class controleFase {
	
	onePixelDAO dao = new onePixelDAO();
	
	public controleFase() {
		if (dao.pixel.getCheckpoint() != "0") {
			new jogo2SalaPrinc().setVisible(true);
		}else {
			new introdução().setVisible(true);
		}
	}
}
