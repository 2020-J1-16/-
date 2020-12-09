import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Shoot_01 extends JFrame implements KeyListener
{   Image       img;
    int         key_t[] = { 0,0,0,0 };      //UP, RIGHT, DOWN, LEFT
    Point       pos= new Point(100,80);
    Dimension   size;
    Image       back;
    Graphics    buffer;

    // Main
    public static void main(String args[])
    {   new Shoot_01();
    }

    // Constructor
    public Shoot_01()
    {   super("Image View");
        img = getToolkit().getImage("Player01.png");
        addKeyListener(this);
        ThreadClass threadcls = new ThreadClass();
        Thread thread = new Thread(threadcls);
        thread.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setBackground(Color.gray);
        setVisible(true);
        size = getSize();
        back= createImage(size.width, size.height);
        if (back==null) System.out.print("createImage Error");
    }

    //  Runnable Class
    class ThreadClass implements Runnable
    {
        public void run()
        {   long    nowTime,drawTime;

            nowTime= System.currentTimeMillis();
            drawTime= nowTime+500;
            while(true)
            {   nowTime= System.currentTimeMillis();
                if (drawTime<nowTime)
                {   drawTime= nowTime+30;
                    if (action())   repaint();
                }
            }
        }
    }

    // Paint Method
    public void paint(Graphics g)
    {   if (back==null)     return;
        buffer= back.getGraphics();
        if (buffer==null)   return;
        size = getSize();
        buffer.setColor(getBackground());
        buffer.fillRect(0, 0, size.width, size.height);
        buffer.drawImage(img,pos.x,pos.y,this);
        g.drawImage(back,0,0,this);
    }

    // シップの移動
    public boolean action()
    {   if (key_t[0]==1)     // UP
        {    pos.y-= 4; return true;  }
        if (key_t[1]==1)     // RIGHT
        {    pos.x+= 4; return true;  }
        if (key_t[2]==1)     // DOWN
        {    pos.y+= 4; return true;  }
        if (key_t[3]==1)     // LEFT
        {    pos.x-= 4; return true;  }
        return false;
    }

    // KeyEvent Listener
    public void keyPressed(KeyEvent e)
    {   switch(e.getKeyCode( ))
        {   case KeyEvent.VK_W  :   key_t[0]= 1;    break;
            case KeyEvent.VK_D  :   key_t[1]= 1;    break;
            case KeyEvent.VK_S  :   key_t[2]= 1;    break;
            case KeyEvent.VK_A  :   key_t[3]= 1;    break;
        }
    }
    public void keyReleased(KeyEvent e)
    {   switch(e.getKeyCode( ))
        {   case KeyEvent.VK_W :    key_t[0]= 0;    break;
            case KeyEvent.VK_D :    key_t[1]= 0;    break;
            case KeyEvent.VK_S :    key_t[2]= 0;    break;
            case KeyEvent.VK_A :    key_t[3]= 0;    break;
        }
    }
    public void keyTyped(KeyEvent e) { }
}