import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//scoreとlevelを表示するプログラム
//弾が発射されたとき常に弾に当たったかどうかをチェックし、当たっていたらscoreup(scoreが1000超えるごとにplayerlevelup)
//弾に当たっていたら同時に弾と敵を消す

class score extends JFrame {
    private int score = 0;
    private int level = 1;
    int tx,ty;      //弾座標
    int ex,ey;      //敵の座標
    boolean eflag=false;      //敵が出現したときに挙げるフラグ
    boolean tflag=false;      //弾が発射されたときに挙げるフラグ
                                //初期状態では弾は出ていないのでfalse
    public static void main(String argv[]){
        new score();
    }
    public void setScore(int i){
        //成績をset
        this.score = this.score+50;
        if(score%1000==0){
            this.level++;
        }
        tflag = false;
        eflag = false;
        ex = -1;
        ey = -1;
    }
    public int getScore(){
        return this.score;
    }
    public int getLevel(){
        return this.level;
    }
    public score(){ 
        /*if(i==0){
            s = s+50;
        }*/
        this.setTitle("score");
        this.setSize(640,480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel Score = new JLabel("SCORE: "+getScore()+"  LEVEL: "+getLevel(), JLabel.CENTER);
        if(tflag==true){
            if(((tx+8)>ex) && (tx<(ex+32))){
                if(((ty+8)>ey) && (ty<(ey+32))){
                    setScore(50);
                }
            }
        }
        Score = new JLabel("SCORE: "+getScore()+"  LEVEL: "+getLevel(), JLabel.CENTER);
        Score.setFont(new Font("Arial", Font.PLAIN, 20));
        Score.setForeground(Color.BLUE);    //文字の色
        this.add(Score,BorderLayout.NORTH);
        this.setVisible(true);
    }
}
