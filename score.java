import java.awt.*;
import javax.swing.*;
//import java.awt.event.*;

//デフォルトのscoreしか表示しない状態のFrame
//敵がいなくなったときのプログラムと結びつける必要あり
class score extends JFrame {
    private int score = 0;
    public static void main(String argv[]){
        //new score(i);
        new score();
    }
    public void setScore(int i){
        //成績をset
        this.score = this.score+50;
    }
    public int getScore(){
        return this.score;
    }
    //public score(int i){
    public score(){ 
        //int score = 0;
        //int level = 1;
        //int character1 = 0;
        /*if(i==0){
            s = s+50;
        }*/
        this.setTitle("score");
        this.setSize(640,480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel Score = new JLabel("SCORE: "+getScore(), JLabel.CENTER);
        //JLabel Level = new JLabel("LEVEL: "+level, JLabel.CENTER);
        Score.setFont(new Font("Arial", Font.PLAIN, 20));
        Score.setForeground(Color.BLUE);    //文字の色
        //Level.setForeground(Color.BLUE);
        this.add(Score,BorderLayout.NORTH);
        //this.add(Level,BorderLayout.NORTH);
        this.setVisible(true);
    }
}
