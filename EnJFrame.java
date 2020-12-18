import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class EnJFrame extends JFrame {
    private final static int FRAME_WIDTH=270;
    private final static int FRAME_LENGTH=500;
    

	private final static String IMAGE_FILE_NAME ="enemy2.png";

	private BufferedImage bufferedImage = null;

	/** 星 位置 */
	private int starX = 0;
	private int starY = 0;

	public static void main(String[] args) {
		new EnJFrame();
	}

	public EnJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(FRAME_WIDTH, FRAME_LENGTH);

		// 星を読み込む
		
        try (InputStream inputStream = this.getClass().getResourceAsStream(IMAGE_FILE_NAME);){
			bufferedImage = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 星の位置初期化
		starX =(int)(Math.random()*270);
        starY =(int)(Math.random()*300);

		// タイマー
        
        
            Timer timer = new java.util.Timer();
        timer.schedule(new MyTimeTask(),1l,20l);
        
        Timer timer2 = new java.util.Timer();
        timer2.schedule(new MyTimeTask(),3l,10l);
		// 表示
		setVisible(true);
	}

	public void paint(Graphics g) {
	    g.drawImage(getScreen(), 0, 0,this);
	}

	private Image getScreen() {
		Image screen = createImage(FRAME_WIDTH,FRAME_LENGTH);

	    // 星の移動
	    if (starX <= FRAME_WIDTH && starY <= FRAME_LENGTH ) {
		    starX ++;
		    starY ++;
		    Graphics g = screen.getGraphics();
		    g.drawImage(bufferedImage, starX, starY, this);
	    } else {
			starX =(int)(Math.random()*270);
            starY =(int)(Math.random()*300);
	    }

	    return screen;
	}

	private class MyTimeTask extends TimerTask {

		@Override
		public void run() {
			repaint();
		}

	}

}