package amoba;

import amoba.gui.AmobaFrame;
import amoba.model.AmobaLogic;

public class Boot {

   public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new AmobaFrame(new AmobaLogic()).setVisible(true);
            }
        
        });
   }
}
