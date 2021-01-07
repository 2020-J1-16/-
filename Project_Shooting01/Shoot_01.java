import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random.*;
import java.awt.geom.Point2D;

class Shoot_01 extends JFrame implements KeyListener,ActionListener
{   Image       img, back_img, img_bullet,teki,lose;
    int         key_t[] = { 0,0,0,0,0 },I00,I01,I03,I04;      //UP, RIGHT, DOWN, LEFT ACCELL
    Point       pos= new Point(270,500);
    Dimension   size, size_back;
    Image       back;
    Graphics    buffer1, buffer2;
    int         pos_back=-1;
    Timer       timer;

    // Main
    public static void main(String args[])
    {   new Shoot_01();
    }

    // Constructor
    public Shoot_01()
    {   super("Image View");
        img = getToolkit().getImage("Player01.png");
        back_img=getToolkit().getImage("back01.jpg");
        teki=getToolkit().getImage("enemy2.png");
        lose=getToolkit().getImage("lose.png");
        addKeyListener(this);
        ThreadClass_Player threadcls_player = new ThreadClass_Player();
        Thread thread_player = new Thread(threadcls_player);
        thread_player.start();
        ThreadClass_Background threadcls_background = new ThreadClass_Background();
        Thread thread_background = new Thread(threadcls_background);
        thread_background.start();
        I00=(int)(Math.random()*300);
        I01=(int)(Math.random()*300);
        if(Math.random()>0.5){
            I03=1;
        }else{
            I03=-1;
        }
        if(Math.random()>0.5){
            I04=1;
        }else{
            I04=-1;
        }
        timer=new Timer(25,this);
        timer.start();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(590, 640);
        setVisible(true);
        size = getSize();
        back= createImage(size.width, size.height);
        if (back==null) System.out.print("Error");
    }

    //  Runnable Class
    class ThreadClass_Player implements Runnable
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

    class ThreadClass_Background implements Runnable
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
        
        g.drawImage(teki,I00,I01,this);
        if(I00==pos.x&& I01==pos.y){
            g.drawImage(lose,280,320,this);
        }
        
        
    }
    
    public void actionPerformed(ActionEvent e){
        I00+=I03;
        I01+=I04;
        if(I00>=570||I00<=0){
            I03=-I03;
        }
        if(I01>=620||I01<=0){
            I04=-I04;
        }

            
    }

    public void Update()
    {   long wk= (System.currentTimeMillis()/30)%640;
        pos_back= (int)wk;
    }

    public boolean action()
    {   if (key_t[1]==1)     // RIGHT
        {    
            if(key_t[4]==1) 
            { pos.x+= 8; return true; }
            else
            { pos.x+= 4; return true; }
        }
        if (key_t[3]==1)     // LEFT
        {    
            if(key_t[4]==1)
            { pos.x-= 8; return true; }
            else
            { pos.x-= 4; return true; }
        }
        if (key_t[0]==1)     // UP
        {    
            if(key_t[4]==1) 
            { pos.y-= 8; return true; } 
            else 
            { pos.y-= 4; return true; }
        }
        if (key_t[2]==1)     // DOWN
        {    
            if(key_t[4]==1) 
            { pos.y+= 8; return true; }
            else
            { pos.y+= 4; return true; }
        }
        return false;
    }

    public void keyPressed(KeyEvent e)
    {   switch(e.getKeyCode( ))
        {   case KeyEvent.VK_W          :   key_t[0]= 1;    break;
            case KeyEvent.VK_D          :   key_t[1]= 1;    break;
            case KeyEvent.VK_S          :   key_t[2]= 1;    break;
            case KeyEvent.VK_A          :   key_t[3]= 1;    break;
            case KeyEvent.VK_UP         :   key_t[0]= 1;    break;
            case KeyEvent.VK_RIGHT      :   key_t[1]= 1;    break;
            case KeyEvent.VK_DOWN       :   key_t[2]= 1;    break;
            case KeyEvent.VK_LEFT       :   key_t[3]= 1;    break;
            case KeyEvent.VK_SHIFT      :   key_t[4]= 1;    break;
        }
    }
    public void keyReleased(KeyEvent e)
    {   switch(e.getKeyCode( ))
        {   case KeyEvent.VK_W          :   key_t[0]= 0;    break;
            case KeyEvent.VK_D          :   key_t[1]= 0;    break;
            case KeyEvent.VK_S          :   key_t[2]= 0;    break;
            case KeyEvent.VK_A          :   key_t[3]= 0;    break;
            case KeyEvent.VK_UP         :   key_t[0]= 0;    break;
            case KeyEvent.VK_RIGHT      :   key_t[1]= 0;    break;
            case KeyEvent.VK_DOWN       :   key_t[2]= 0;    break;
            case KeyEvent.VK_LEFT       :   key_t[3]= 0;    break;
            case KeyEvent.VK_SHIFT      :   key_t[4]= 0;    break;
        }
    }
    public void keyTyped(KeyEvent e) { }
}


    