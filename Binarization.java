import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class Binarization {
	public static void main(String args[])
		       throws Exception
		   {
		       /* 画像を２値化する */
		       BufferedImage img = ImageIO.read(new File("/Users/sachi/Desktop/SF.jpg"));

		       /* グレースケールに変換 */
		       WritableRaster wr = img.getRaster();
		       int buf[] = new int[wr.getNumDataElements()];
		       for(int ly=0;ly<wr.getHeight();ly++){
		           for(int lx=0;lx<wr.getWidth();lx++){
		               wr.getPixel(lx, ly, buf);

		               int maxval = Math.max(Math.max(buf[0], buf[1]), buf[2]);
		               int minval = Math.min(Math.min(buf[0], buf[1]), buf[2]);
		               buf[0] = buf[1] = buf[2] = (maxval+minval)/2;

		               wr.setPixel(lx, ly, buf);
		           }
		       }

		       /* lookupデータ作成 */
		       byte dat[] = new byte[256];
		       for(int di=0;di<256;di++){
		           dat[di] = di>256*0.45?(byte)255:(byte)0;
		       }
		       LookupOp lo = new LookupOp(new ByteLookupTable(0, dat), null);
		       BufferedImage img2 = lo.filter(img, null);

		       ImageIO.write(img2, "jpg", new File("/Users/sachi/Desktop/sample531a.jpg"));
		   }
}
