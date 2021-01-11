import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.*;

class Shoot_01 extends JFrame implements KeyListener
{   Image               img, back_img, img_bullet;
    int                 key_t[] = { 0,0,0,0,0,0 };      //UP, RIGHT, DOWN, LEFT, ACCELL, BULLET
    Bullet              bullet_work;
    ArrayList<Bullet>   list;
    long                nowTime, drawTime, shotTime;
    Point               pos= new Point(270,500);
    Dimension           size;
    Image               back;
    Graphics            buffer;
    int                 pos_back=-1;

    // Main
    public static void main(String args[])
    {   new Shoot_01();
    }

    // Constructor
    public Shoot_01()
    {   super("Image View");
        img = getToolkit().getImage("Player01.png");
        back_img=getToolkit().getImage("back01.jpg");
        img_bullet=getToolkit().getImage("bullet01.jpg");
        list=new ArrayList<Bullet>();
        shotTime=0;
        addKeyListener(this);
        ThreadClass threadcls = new ThreadClass();
        Thread thread = new Thread(threadcls);
        thread.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(590, 640);
        setVisible(true);
        size = getSize();
        back= createImage(size.width, size.height);
        if (back==null) System.out.print("Error");
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
                    Update();
                    repaint();
                }
            }
        }
    }

    public void paint(Graphics g)
    {   if (back==null || pos_back<0)    return;
        buffer= back.getGraphics();
        if (buffer==null)   return;
        size = getSize();
        buffer.drawImage(back_img,0,0,size.width,size.height,0,pos_back+640,size.width,pos_back,this);
        buffer.drawImage(img,pos.x,pos.y,this);
        for(int i=0; i<list.size(); i++)
            buffer.drawImage(img_bullet,(int)list.get(i).pos.x,(int)list.get(i).pos.y,this);
        g.drawImage(back,0,0,this);
    }

    public void Update()
    {   long wk= (System.currentTimeMillis()/30)%640;
        pos_back= (int)wk;
        for(int i=0; i<list.size(); i++)
            list.get(i).Update();
        for(int i=list.size()-1; i>=0; i--)
        {
            if(list.get(i).flag==0)
                list.remove(i);
        }

    }

    public boolean action()
    {   if (key_t[1]==1)     // RIGHT
        {   
            if(pos.x<530) 
            {
                if(key_t[4]==1) 
                { pos.x+= 8; return true; }
                else
                { pos.x+= 4; return true; }
            }
        }
        if (key_t[3]==1)     // LEFT
        {    
            if(pos.x>10)
            {
                if(key_t[4]==1)
                { pos.x-= 8; return true; }
                else
                { pos.x-= 4; return true; }
            }
        }
        if (key_t[0]==1)     // UP
        {    
            if(pos.y>25)
            {
                if(key_t[4]==1) 
                { pos.y-= 8; return true; } 
                else 
                { pos.y-= 4; return true; }
            }
        }
        if (key_t[2]==1)     // DOWN
        {    
            if(pos.y<570)
            {
                if(key_t[4]==1) 
                { pos.y+= 8; return true; }
                else
                { pos.y+= 4; return true; }
            }
        }
        if (key_t[5]==1)    //BULLET
        {
            if (shotTime<nowTime)
            {
                shotTime=nowTime+100;
                bullet_work=new Bullet();
                bullet_work.Set(pos.x+20,pos.y,0,-8);
                list.add(bullet_work);
            }
        }
        return false;
    }

    public void keyPressed(KeyEvent e)
    {   switch(e.getKeyCode( ))
        {   case KeyEvent.VK_ESCAPE     :   System.exit(0); break;
            case KeyEvent.VK_SPACE      :   
                key_t[5]= 1;
                nowTime= System.currentTimeMillis();
                if (shotTime<nowTime)
                {   shotTime= nowTime+100;
                    bullet_work = new Bullet();
                    bullet_work.Set(pos.x+17,pos.y,0,-10);
                    list.add(bullet_work);
                }
                break;            
            case KeyEvent.VK_W          :   key_t[0]= 1;    break;
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
            case KeyEvent.VK_SPACE      :   key_t[5]= 0;    break;
        }
    }
    public void keyTyped(KeyEvent e) { }

    class Bullet
    {
        Point2D.Float pos, vect;
        byte flag;

        //Constructor
        public Bullet()
        {
            pos=new Point2D.Float();
            vect=new Point2D.Float();
            flag=0;
        }

        public void Set(float xp, float yp, float xv, float yv)
        {
            pos.x=xp;
            pos.y=yp;
            vect.x=xv;
            vect.y=yv;
            flag=1;
        }

        public void Update() 
        {
            if(flag==0) return;
            pos.x+=vect.x;
            pos.y+=vect.y;
            if(pos.x>590 || pos.x<0 || pos.y>640 || pos.y<0) flag=0;
        }

        public void Rot(float rt, float len) 
        {
            vect.x=(float)(Math.sin(rt/180*3.14))*len;
            vect.y=(float)(Math.cos(rt/180*3.14))*len;
        }
    }
}
