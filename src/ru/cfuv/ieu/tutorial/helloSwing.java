package ru.cfuv.ieu.tutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class helloSwing extends JFrame {
    private JTextField number1;
    private JTextField number2;
    private JButton dif;
    private JTextField result;
    private JButton sum;
    private JPanel root;

    public helloSwing(){
        this.setTitle("Swing tutorial");
        this.getContentPane().add(root);
        sum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    double res = Double.parseDouble(number1.getText()) + Double.parseDouble(number2.getText());
                    result.setText(Double.toString(res));
                }catch (NumberFormatException e1){
                    result.setText("Неверный формат чисел");
                }
            }
        });

        dif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    double res = Double.parseDouble(number1.getText()) - Double.parseDouble(number2.getText());
                    result.setText(Double.toString(res));
                }catch (NumberFormatException e1){
                    result.setText("Неверный формат чисел");
                }
            }
        });
    }
    public static void main(String[] args){
        helloSwing test = new helloSwing();
        test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        test.pack();
        //test.setSize(new Dimension(300, 500));
        test.setVisible(true);
        test.setLocationRelativeTo(null);
    }
}
