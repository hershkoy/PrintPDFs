package printpdfs;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import static printpdfs.foldersWindow.print;
 
public class PrintPDFs {

    static PDDocument document;
    static PrintService myPrintService;
    static PrinterJob printerJob;
    static PageFormat pageFormat, pageFormat2, pageFormat3;
    static Paper paper;
    static Book Book;
    static foldersWindow frame;
    static String Folder;
    static int ficherosToPrint = 0;
    
    public static void configurePrinter(){
        
        myPrintService = PrintServiceLookup.lookupDefaultPrintService();//Default Printer
        System.out.println(" ---- PRINTER: " + myPrintService.getName());
        printerJob = PrinterJob.getPrinterJob();//Manipulate the printer interface

        // Custom Page Format
        // Customize the pages, which will allow entering of the properties of the print.
        pageFormat  = printerJob.defaultPage();
        pageFormat2 = (PageFormat) pageFormat.clone();
        paper = pageFormat.getPaper();//Assign Sheet Sizes 
        paper.setImageableArea(0, 0,pageFormat.getWidth(), pageFormat.getHeight());
        pageFormat2.setPaper(paper);
        pageFormat3 = printerJob.validatePage(pageFormat2);
    }
    public static void readFolder(String readFolder) throws IOException, PrinterException{
        
        Folder = readFolder+'\\';
        File dir = new File(Folder);
        String[] ficheros = dir.list();
        
        System.out.println("Folder: "+Folder);

        if (ficheros.length > 0){
            String type = ".pdf";
            for (int x=0;x<ficheros.length;x++){
                if(ficheros[x].indexOf(type) != -1){
                    System.out.println(ficheros[x]);
                    printFiles(ficheros[x]);
                    ficherosToPrint = 1;
                }
            }
        } else { 
            JOptionPane.showMessageDialog(null, "There are no files in the specified directory");
            System.out.println("There are no files in the specified directory");
        }
        
        if(ficherosToPrint == 0){
            JOptionPane.showMessageDialog(null, "There are no files in the specified directory");
            System.out.println("There are no files in the specified directory");
        }
       
    }
    public static void printFiles(String file) throws IOException, PrinterException{
        
        document = PDDocument.load(new File(Folder+file));
        
        Book = new Book ();
        Book.append (new PDFPrintable (document,Scaling.SHRINK_TO_FIT),pageFormat3,1);//Print only one page
        
        printerJob.setPageable(Book);
        printerJob.setPrintService(myPrintService);
        printerJob.print();
    }

    public static void main(String[] args) {

        configurePrinter();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new foldersWindow();
                    frame.setVisible(true);
                    
                    frame.print.addActionListener(new ActionListener(){
                        public void actionPerformed (ActionEvent e){
                            String Folder = frame.print();  
                            
                            if(!Folder.equals("")){
                                try {
                                    readFolder(Folder);
                                    System.exit(0);
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(PrintPDFs.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (PrinterException ex) {
                                    Logger.getLogger(PrintPDFs.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }//if
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
     
    }
}
    

