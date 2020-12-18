//import java.awt.*;
import javax.swing.*;
//import java.awt.event.*;

class shootingFrame extends JFrame {
    public static void main(String argv[]){
        new shootingFrame();
    }

    public shootingFrame(){
        this.setTitle("Shooting game");
        this.setSize(600,400);
        new Shoot_01();
        new Shoot_02();
        this.setVisible(true);
    }
}
