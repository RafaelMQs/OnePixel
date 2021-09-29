package OnePixel;

import BancoDeDados.*;

public class controleFase {
	
	onePixelDAO dao = new onePixelDAO();
	
	public controleFase() {
		switch (dao.pixel.getCheckpoint()) {
		
		case "0": new introdução().setVisible(true);
		break;
		
		case "1": new jogo2SalaPrinc().setVisible(true);
		break;
		
		}
	}
}
