import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Random.*;
import javax.swing.Timer;
import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import java.lang.InterruptedException;

//enemy,ship,bullet,score,gamestart,gameover

//the size of enemy is 48 × 48, r = 35 pixel(body) (r = 43 pixel)
//the size of bullet is 15 × 15, r = 7.5 pixel

class Shoot_new3 extends JFrame implements KeyListener,ActionListener
{   Image               img, back_img, img_bullet,teki,lose,gameclear,gameover,gamestart,explanation;
    int                 key_t[] = { 0,0,0,0,0,0,0 };      //UP, RIGHT, DOWN, LEFT, ACCELL, BULLET, explanation
    int                 I00,I01,I02,I03,I04;    //I00=the coordinate of the enemy
                                                //I01=the Y coordinate of the enemy
    Bullet              bullet_work;
    ArrayList<Bullet>   list;
    long                nowTime, drawTime, shotTime;
    Point               pos= new Point(270,500);
    Dimension           size;
    Image               back;
    Graphics            buffer;
    int                 pos_back=-1;
    Timer               timer,time;
    int                 score=0;
    JLabel              Score; 
    int                 flag=0;
    Image               g_board[] = { },g_number[] = { };
    final static int    SCORE_X = 390;  //スコアボード文字を書く際のx座標
    int                 num0,num1,num2,num3,score2=0;
    int                 deadflag = 0, clearflag = 0, exflag = 0;
    Clip                clip, shoot;
    /*Image               anime = null;
    boolean             anim = true;*/

    // Main
    public static void main(String args[])
    {   new Shoot_new3();
    }

    // Constructor
    public Shoot_new3()
    {   super("Image View");
        img = getToolkit().getImage("Player01.png");
        back_img=getToolkit().getImage("back01.jpg");
        img_bullet=getToolkit().getImage("bullet01.jpg");
        teki=getToolkit().getImage("enemy2.png");
        lose=getToolkit().getImage("lose.png");
        g_board = new Image[1];
        g_number = new Image[10];
        g_board[0] = getToolkit().getImage("score.png");
        g_number[0] = getToolkit().getImage("number0.png");
        g_number[1] = getToolkit().getImage("number1.png");
        g_number[2] = getToolkit().getImage("number2.png");
        g_number[3] = getToolkit().getImage("number3.png");
        g_number[4] = getToolkit().getImage("number4.png");
        g_number[5] = getToolkit().getImage("number5.png");
        g_number[6] = getToolkit().getImage("number6.png");
        g_number[7] = getToolkit().getImage("number7.png");
        g_number[8] = getToolkit().getImage("number8.png");
        g_number[9] = getToolkit().getImage("number9.png");
        gamestart=getToolkit().getImage("text_start1.png");
        gameclear=getToolkit().getImage("text_gameclear_e.png");
        gameover=getToolkit().getImage("text_gameover_e.png");
        explanation=getToolkit().getImage("explanation2.png");

        clip = createClip(new File("BGM225-201031-jigokunoroulette-wav.wav"));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        list=new ArrayList<Bullet>();
        shotTime=0;
        addKeyListener(this);
        ThreadClass threadcls = new ThreadClass();
        Thread thread = new Thread(threadcls);
        thread.start();

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

    public void setScore(int score, int val){
        this.score += val;
    }

    public int getScore(){
        return score;
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
        buffer.drawImage(teki,I00,I01,this);
        if(I00==pos.x&& I01==pos.y){
            buffer.drawImage(lose,280,320,this);
        }

        if(score>=1000){
            buffer.drawImage(gameclear,0,0,this);
            clearflag = 1;
        }
        if((deadflag == 1) && (score<1000)){
            buffer.drawImage(gameover,0,0,this);
        }
        buffer.drawImage(g_board[0],SCORE_X,25,this);

        score2 = score;
        num3 = score2/1000;
        num2 = (score2%1000)/100;      //num2 = 100の位
        num1 = ((score2%1000)%100)/10;     //num1 = 10の位
        num0 = ((score2%1000)%100)%10;     //num0 = 1の位

        buffer.drawImage(g_number[num3], SCORE_X+20+0*19, 50, this);
        buffer.drawImage(g_number[num2], SCORE_X+20+1*19, 50, this);
        buffer.drawImage(g_number[num1], SCORE_X+20+2*19, 50, this);
        buffer.drawImage(g_number[num0], SCORE_X+20+3*19, 50, this);
        if(exflag == 0){
            buffer.drawImage(explanation, 20, 30, this);
        }
        g.drawImage(back, 0, 0, this);
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

    public boolean CircleCollision(float cx1, int cx2, float cy1, int cy2){
        float hlength = 35;         //計算上はもっと大きい
        float xlength = cx1-cx2;
        float ylength = cy1-cy2;

        if(hlength*hlength>=xlength*xlength+ylength*ylength){
            return true;    //hit
        }else{
            return false;   //not hit
        }
    }

    public void Update()
    {   long wk= (System.currentTimeMillis()/30)%640;
        pos_back= (int)wk;
        for(int i=0; i<list.size(); i++){
            list.get(i).Update();
            if(CircleCollision(list.get(i).pos.x, I00, list.get(i).pos.y, I01)){
                if((deadflag == 0) && (clearflag == 0)){
                    score = score +15;
                    list.remove(i);
                }
            }
            /*if(CircleCollision(pos.x,I00,pos.y,I01)){
                deadflag = 1;
            }*/
        }
        if(CircleCollision(pos.x,I00,pos.y,I01)){
            deadflag = 1;
        }
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
            shoot = createClip(new File("tama1.wav"));
            shoot.start();
            if (shotTime<nowTime)
            {
                shotTime=nowTime+100;
                bullet_work=new Bullet();
                bullet_work.Set(pos.x+20,pos.y,0,-8);
                list.add(bullet_work);
            }
        }
        if(key_t[6]==1)     //SHIFTキーでdelete explanation
        {
            exflag = 1;
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
            case KeyEvent.VK_ENTER      :   key_t[6]= 1;    break;
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

    public Clip createClip(File path)
    {
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(path);
            AudioFormat af = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, af);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);
            //clip.loop(0);
            clip.flush();
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }finally {
            try {
                ais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

