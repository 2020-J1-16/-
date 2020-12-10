import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Shoot_01 extends JFrame implements KeyListener
{   Image       img, back_img;
    int         key_t[] = { 0,0,0,0 };      //UP, RIGHT, DOWN, LEFT
    Point       pos= new Point(270,500);
    Dimension   size, size_back;
    Image       back;
    Graphics    buffer1, buffer2;
    int         pos_back=-1;

    // Main
    public static void main(String args[])
    {   new Shoot_01();
    }

    // Constructor
    public Shoot_01()
    {   super("Image View");
        img = getToolkit().getImage("Player01.png");
        back_img=getToolkit().getImage("back01.jpg");
        addKeyListener(this);
        ThreadClass1 threadcls1 = new ThreadClass1();
        Thread thread1 = new Thread(threadcls1);
        thread1.start();
        ThreadClass2 threadcls2 = new ThreadClass2();
        Thread thread2 = new Thread(threadcls2);
        thread2.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(590, 640);
        setVisible(true);
        size = getSize();
        back= createImage(size.width, size.height);
        if (back==null) System.out.print("createImage Error");
    }

    //  Runnable Class
    class ThreadClass1 implements Runnable
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

    class ThreadClass2 implements Runnable
    {
        public void run()
        {   long    nowTime,drawTime;

            nowTime= System.currentTimeMillis();
            drawTime= nowTime+500;
            while(true)
            {   nowTime= System.currentTimeMillis();
                if (drawTime<nowTime)
                {   drawTime= nowTime+30;
                    Update();
                    repaint();
                }
            }
        }
    }

    public void paint(Graphics g)
    {   if (back==null || pos_back<0)    return;
        buffer1= back.getGraphics();
        buffer2= back.getGraphics();
        if (buffer1==null)   return;
        size = getSize();
        size_back = getSize();
        buffer1.drawImage(back_img,0,0,size.width,size.height,0,pos_back+640,size.width,pos_back,this);
        buffer2.drawImage(img,pos.x,pos.y,this);
        g.drawImage(back,0,0,this);
    }

    public void Update()
    {   long wk= (System.currentTimeMillis()/30)%640;
        pos_back= (int)wk;
    }

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