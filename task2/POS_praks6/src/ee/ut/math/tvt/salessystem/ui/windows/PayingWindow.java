package ee.ut.math.tvt.salessystem.ui.windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

public class PayingWindow {

    private static final Logger log = Logger.getLogger(PayingWindow.class);

    private static JFrame payingWindow;
    private static double totalPrice;
    
    private static JButton okButton;
    private static JButton cancelButton;
    private static JButton commitButton;
    
    private static JTextField priceTextField;
    private static JTextField paidMoneyTextField;
    private static JTextField exchangeMoneyTextField;
    
    private static PurchaseTab parent;
    
    // Is the init() method been called already?
    private static boolean initialized = false;
    
    // Builds the window (only once on the first call to show() ). 
    private static void init() {
        
        if (!initialized) {
                
            // Create and set up the window:
            payingWindow = new JFrame("Paying");
            payingWindow.setAlwaysOnTop(true);
            payingWindow.setResizable(false);
            payingWindow.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    cancelPaying();
                }
            });
    
            // Size and location of the window:
            int width = 300;
            int height = 200;
            payingWindow.setSize(width, height);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            payingWindow.setLocation((screen.width - width) / 2,
                    (screen.height - height) / 2);
            
            // Create the panel:
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2));
            panel.setBorder(BorderFactory.createTitledBorder("Payment"));
    
    
            // Initialize the fields:
            priceTextField = new JTextField(16);
            paidMoneyTextField = new JTextField(16);
            exchangeMoneyTextField = new JTextField(16);
            
            priceTextField.setEditable(false);
            exchangeMoneyTextField.setEditable(false);
            
            initOkButton();
            initCancelButton();
            initCommitButton();
            
            // Addition of components:
            panel.add(new JLabel("Sum"));
            panel.add(priceTextField);
            panel.add(new JLabel("Cash"));
            panel.add(paidMoneyTextField);
            panel.add(new JLabel("Change"));
            panel.add(exchangeMoneyTextField);
            panel.add(okButton);
            panel.add(cancelButton);
            panel.add(commitButton);
    
            // Adding panel to the frame:
            payingWindow.getContentPane().add(panel);
            
            initialized = true;
        }
        
    }
    
    private static void initOkButton() {
        // Confirmation of the given amount of cash:
        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double cash = 0.0;
                try {
                    cash = Double.parseDouble(paidMoneyTextField.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null,
                            "Cash has to be in number format.");
                    return;
                }
                double returnMoney = cash - totalPrice;

                exchangeMoneyTextField.setText(Double.toString(returnMoney));
                if (returnMoney >= 0) {
                    commitButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Not enough cash entered.");
                }
            }
        });
    }

    private static void initCancelButton() {
        // Buttons and their actions:
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelPaying();
            }
        });        
    }
    
    private static void initCommitButton() {
        
        // Confirmation button:
        commitButton = new JButton("Commit");
        commitButton.setEnabled(false);
        commitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.endPurchaseAfterPaying();
                payingWindow.setVisible(false);
            }
        });        
    }
    
    
    public static void show(final double _totalPrice, final PurchaseTab _parent) {
        parent = _parent;
        totalPrice = _totalPrice;
        
        init();
        
        // Update the text fields 
        priceTextField.setText(String.valueOf(totalPrice));
        paidMoneyTextField.setText("");
        exchangeMoneyTextField.setText("");
        
        // And make sure the "Commit" button is always unavailable at first
        commitButton.setEnabled(false);

        // Display the window:
        payingWindow.pack();
        payingWindow.setVisible(true);
        
    }
    
    
    private static void cancelPaying() {
        log.info("Checkout process cancelled");
        parent.cancelPaying();
        payingWindow.setVisible(false);
    }

}
