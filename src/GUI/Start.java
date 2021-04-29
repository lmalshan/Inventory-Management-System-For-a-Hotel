/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Dean
 */
public class Start {

    public static void main(String[] args) {
        Splash s = new Splash();
        s.setVisible(true);
        LoginPage lp = new LoginPage();
        try {
            for (int i = 0; i <= 100; i++) {

                Thread.sleep(40);
                s.lblProg.setText(Integer.toString(i) + "%");
                s.barProg.setValue(i);

                if (i == 100) {
                    s.setVisible(false);
                    lp.setVisible(true);
                }
            }
        } catch (Exception e) {
        }
    }
}
