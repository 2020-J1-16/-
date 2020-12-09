import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Shoot_02 extends JFrame
{   Image       img;
    Dimension   size;
    Image       back;
    Graphics    buffer;
    int         pos= -1;

    // Main
    public static void main(String args[])
    {   new Shoot_02();
    }

    // Constructor
    public Shoot_02()
    {   super("Image View");
        img = getToolkit().getImage("back01.jpg");
        ThreadClass threadcls = new ThreadClass();
        Thread thread = new Thread(threadcls);
        thread.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(590, 640);
        setVisible(true);
        size = getSize();
        back= createImage(size.width, size.height);
        if (back==null) System.out.println("Error");
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
                    Update();
                    repaint();
                }
            }
        }
    }

    // Paint Method
    public void paint(Graphics g)
    {   if (back==null || pos<0)    return;
        buffer= back.getGraphics();
        if (buffer==null)   return;
        size = getSize();
        buffer.drawImage(img,0,0,size.width,size.height,0,pos+640,size.width,pos,this);
        g.drawImage(back,0,0,this);
    }

    // 更新処理
    public void Update()
    {   long wk= (System.currentTimeMillis()/30)%640;
        pos= (int)wk;
    }
}