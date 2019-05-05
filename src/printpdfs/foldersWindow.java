package printpdfs;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class foldersWindow extends JFrame{
    
    static JPanel contentPane;
    static JTextArea textArea;
    static JButton addFolder, print;
    static JScrollPane scroll;
    static JFileChooser FileChooser;
    static File folder;
  
    public foldersWindow() {
 
        // Parameters associated with the window
        super("TEST APP   'PRINT FILES PDF'");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
 
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(40, 30, 360, 160);
 
        scroll=new JScrollPane(textArea);
        scroll.setBounds(40, 30, 360, 160);
        contentPane.add(scroll);
        
        addFolder = new JButton("ADD FOLDER");
        addFolder.setBounds(40, 200, 120, 23);
        contentPane.add(addFolder);
        
        print = new JButton("PRINT");
        print.setBounds(172, 200, 120, 23);
        contentPane.add(print);
 
        addFolder.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                addFolder();  
            }
        });
        
        
 
    }
    
    public void addFolder(){
        
        FileChooser=new JFileChooser();
        FileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //We indicate what we can select (folders)
        int select=FileChooser.showOpenDialog(contentPane);

        if(select==JFileChooser.APPROVE_OPTION){
            folder=FileChooser.getSelectedFile();                   
            textArea.setText(folder.getAbsolutePath());
        }
    }
    
    public String print(){
        
        String Folder = textArea.getText();
         
         if(Folder.equals("")){
            JOptionPane.showMessageDialog(null, "You must select a folder");
         }
        return Folder;
    }
}
