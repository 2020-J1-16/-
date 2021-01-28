import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Random.*;
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
import java.lang.InterruptedException;

//enemy,ship,bullet,score,gamestart,gameover

//the size of enemy is 48 × 48, r = 35 pixel(body) (r = 43 pixel)
//the size of bullet is 15 × 15, r = 7.5 pixel

class Shoot_new5 extends JFrame implements KeyListener
{   Image               img, back_img, img_bullet,teki,gameclear,gameover,gamestart,explanation;
    int                 key_t[] = { 0,0,0,0,0,0,0 };      //UP, RIGHT, DOWN, LEFT, ACCELL, BULLET, explanation
    int                 I03,I04;    //I00=the coordinate of the enemy
    int[] I00=new int[10];
    int[] I01=new int[10];                                    //I01=the Y coordinate of the enemy
    Bullet              bullet_work;
    boolean[]  appear=new boolean[10]; //敵が存在するかどうか
    int next;
    ArrayList<Bullet>   list;
   
    
    long                nowTime, drawTime, shotTime;
    Point               pos= new Point(270,500);
    Dimension           size;
    Image               back;
    Graphics            buffer;
    int                 pos_back=-1;
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
    {   new Shoot_new5();

    }

    // Constructor
    public Shoot_new5()
    {   super("Image View");
        img = getToolkit().getImage("Player01.png");
        back_img=getToolkit().getImage("back01.jpg");
        img_bullet=getToolkit().getImage("bullet01.jpg");
        teki=getToolkit().getImage("enemy2.png");
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
        explanation=getToolkit().getImage("explanation.png");

        clip = createClip(new File("BGM225-201031-jigokunoroulette-wav.wav"));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        list=new ArrayList<Bullet>();
        shotTime=0;
       
        
        for(int i=0; i<10; i=i+1)
        {
            // 敵の座標を初期化する
            I00[i] =(int)(Math.random()*300) ;
            I01[i] =(int)(Math.random()*300);
            // 敵が生きているかどうかのフラグを初期化
            appear[i] = false;
        }
        
        // 次に有効にする敵の番号を初期化
        next= 0;

        // 時間を表す変数を初期化
        
        ThreadClass threadcls = new ThreadClass();
        Thread thread = new Thread(threadcls);
        thread.start();
        addKeyListener(this);
        
        if(Math.random()>0.5){
            I03=20;
        }else{
            I03=-20;
        }
        if(Math.random()>0.5){
            I04=20;
        }else{
            I04=-20;
        }
        /*timer=new Timer(15,this);
        timer.start();*/
        
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
                    if (action())  repaint();
                    if(drawTime%9==0){
                        tekiaction();
                        Update();
                    }else{
                       
                       
                    Update();
                    }
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
        for(int j=0;j<10;j++){   
         if(appear[j]){
        buffer.drawImage(teki,I00[j],I01[j],this);
            }
        
        if(score>=1000){
            buffer.drawImage(gameclear,0,0,this);
            clearflag = 1;
        }
        if((appear[j])&&(deadflag == 1) && (score<1000)){
            buffer.drawImage(gameover,0,0,this);
        }
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
        for(int j=0;j<10;j++){
        for(int i=0; i<list.size(); i++){
            list.get(i).Update();
            
            if(CircleCollision(list.get(i).pos.x, I00[j], list.get(i).pos.y, I01[j])){
                if((deadflag == 0) && (clearflag == 0)){
                    score = score +15;
                    list.remove(i);
                    appear[j]=false;
                }
            }
            
            /*if(CircleCollision(pos.x,I00,pos.y,I01)){
                deadflag = 1;
            }*/
        }
        if(CircleCollision(pos.x,I00[j],pos.y,I01[j])){
            deadflag = 1;
        }
        for(int i=list.size()-1; i>=0; i--)
        {
            if(list.get(i).flag==0)
                list.remove(i);
        }
    }
    }
public void tekiaction(){
   
        // 敵を有効に
        appear[next] = true;
        // 次の敵の番号へ。
        next++;

        // 敵は10個までなので、それを超えたら0に戻る(使い回す)
        if(next >= 10)
        {
            // 次の敵の番号を先頭の 0 へ
            next= 0;
        }
    
     // 敵更新用のfor文
     for(int i=0; i<10; i=i+1)
     {
         // 有効(true)な敵のみ実行
         if(appear[i])
         {
            I00[i]+=I03;
            I01[i]+=I04;
             // 敵が画面の下を越えた場合
             if(I00[i]>=570||I00[i]<=0){
                I03=-I03;
            }
            if(I01[i]>=620||I01[i]<=0){
                I04=-I04;
         }
     }
     }
}
    public boolean action()
    {   
        shoot = createClip(new File("tama1.wav"));
      
       
        if (key_t[1]==1)     // RIGHT
        {   
            if(pos.x<530) 
            {
                if(key_t[4]==1) 
                { pos.x+= 8; }
                else
                { pos.x+= 4; }

                if(key_t[5]==1)
                { shoot.start(); return true; }
                else
                { return true; }
            }
        }
        if (key_t[3]==1)     // LEFT
        {    
            if(pos.x>10)
            {
                if(key_t[4]==1) 
                { pos.x-= 8; }
                else
                { pos.x-= 4; }

                if(key_t[5]==1)
                { shoot.start(); return true; }
                else
                { return true; }
            }
            
        }
        if (key_t[0]==1)     // UP
        {    
            if(pos.y>25)
            {
                if(key_t[4]==1) 
                { pos.y-= 8; }
                else
                { pos.y-= 4; }

                if(key_t[5]==1)
                { shoot.start(); return true; }
                else
                { return true; }
            }
        }
        if (key_t[2]==1)     // DOWN
        {    
            if(pos.y<570)
            {
                if(key_t[4]==1) 
                { pos.y+= 8; }
                else
                { pos.y+= 4; }
                if(key_t[5]==1)
                { shoot.start(); return true; }
                else
                { return true; }
            }
        }
        if (key_t[5]==1)    //BULLET
        {
            //shoot = createClip(new File("tama1.wav"));
            shoot.start();
            if (shotTime<nowTime)
            {
                shotTime=nowTime+100;
                bullet_work=new Bullet();
                bullet_work.Set(pos.x+20,pos.y,0,-8);
                list.add(bullet_work);
            }
           
            return true;
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

