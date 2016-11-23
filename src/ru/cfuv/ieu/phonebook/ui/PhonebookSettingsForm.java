package ru.cfuv.ieu.phonebook.ui;

import ru.cfuv.ieu.phonebook.settings.PhonebookSettings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings({"unused", "Convert2Lambda"})
public class PhonebookSettingsForm extends JDialog {
    public static final int YES = 0;
    public static final int NO = -1;

    private final PhonebookSettings settings;
    private int result = NO;

    private JPanel root;
    private JCheckBox formatNumbers;
    private JTextField dbPath;
    private JButton okayBtn;
    private JButton cancelBtn;
    private JSlider scaleSlider;
    private JTextField scaleSliderText;
    private JLabel fontScaleLabel;
    private JLabel dbPathLabel;

    private double scale;
    private final static int TFIELD = 0;
    private final static int BUTTON = 0;
    private final static int LABEL = 0;
    private final Font tfieldFont = dbPath.getFont();
    private final Font btnFont = okayBtn.getFont();
    private final Font labelFont = formatNumbers.getFont();

    private void setScale(JComponent c, int type) {
        if (type == TFIELD) {
            c.setFont(new Font(tfieldFont.getName(),
                    tfieldFont.getStyle(),
                    (int) (tfieldFont.getSize() * scale)));
        } else if (type == BUTTON) {
            c.setFont(new Font(btnFont.getName(),
                    btnFont.getStyle(), (int) (btnFont.getSize() * scale)));
        } else if (type == LABEL) {
            c.setFont(new Font(labelFont.getName(),
                    labelFont.getStyle(), (int) (labelFont.getSize() * scale)));
        }
    }

    private void refreshScales() {
        scale = ((double) scaleSlider.getValue()) / 100;

        setScale(formatNumbers, LABEL);
        setScale(dbPath, TFIELD);
        setScale(okayBtn, BUTTON);
        setScale(cancelBtn, BUTTON);
        setScale(scaleSliderText, TFIELD);
        setScale(fontScaleLabel, LABEL);
        setScale(dbPathLabel, LABEL);

        this.repaint();
        this.pack();
    }

    public PhonebookSettingsForm(PhonebookSettings settings) {
        this.settings = settings;

        formatNumbers.setSelected(settings.getFormatting());
        dbPath.setText(settings.getDBPath());
        scaleSlider.setValue(clampScale(settings.getFontScale()));
        scaleSliderText.setText(String.valueOf(scaleSlider.getValue()));

        refreshScales();

        okayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setFormatting(formatNumbers.isSelected());
                settings.setDBPath(dbPath.getText());
                settings.setFontScale(scaleSlider.getValue());
                settings.write();
                result = YES;
                close();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        scaleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                scaleSliderText.setText(String.valueOf(scaleSlider.getValue()));
                refreshScales();
            }
        });
        scaleSliderText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = clampScale(Integer.parseInt(
                        scaleSliderText.getText()));
                scaleSlider.setValue(value);
                refreshScales();
            }
        });
    }

    private void close() {
        this.setVisible(false);
        this.dispose();
    }

    private int clampScale(int value) {
        return Math.min(200, Math.max(100, value));
    }

    public int display() {
        this.getContentPane().add(root);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("Настройки");
        this.pack();
        this.setModal(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        return result;
    }
}
