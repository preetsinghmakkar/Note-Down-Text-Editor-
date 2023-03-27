import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

    ImageIcon note_down_image;
    JTextArea textarea;
    JScrollPane scrollpane;
    JSpinner fontspinner;
    JLabel fontlabel,fontStyleLabel;
    JButton fontcolor;
    JComboBox fontpicker;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem open;
    JMenuItem save;
    JMenuItem exit;

    File file;

    TextEditor(){
        JFrame Frame = new JFrame();

        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(500,530);
        Frame.setLayout(new FlowLayout());
        Frame.setLocationRelativeTo(null);
        Frame.setTitle("Hello! Welcome to Note Down");

        note_down_image = new ImageIcon("src\\note_down_icon.png");

        Frame.setIconImage(note_down_image.getImage());

        textarea = new JTextArea();


        textarea.setFont(new Font("Consolas",Font.PLAIN,35));
        textarea.setForeground(new Color(0x00FF00));
        textarea.setBackground(Color.white);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);

        Frame.add(textarea);

        scrollpane = new JScrollPane(textarea);
        scrollpane.setPreferredSize(new Dimension(450,430));
        scrollpane.getVerticalScrollBarPolicy();
        Frame.add(scrollpane);

        fontlabel = new JLabel("Font SIze");

        fontspinner = new JSpinner();
        fontspinner.setSize(50,25);
        fontspinner.setValue(20);
        fontspinner.setVisible(true);
        fontspinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textarea.setFont(new Font(textarea.getFont().getFamily(),Font.PLAIN,(int) fontspinner.getValue()));
            }
        });

        fontcolor = new JButton("Color Picker");
        fontcolor.addActionListener(this);

        //We need all Font Styles then we are going to add a String Array.

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontpicker = new JComboBox(fonts);
        fontpicker.addActionListener(this);
        fontpicker.setPreferredSize(new Dimension(130,25));
        fontpicker.setSelectedItem("Arial");
        fontStyleLabel = new JLabel("Font Style");

        //Menu Bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        open.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        Frame.setJMenuBar(menuBar);
        Frame.add(fontStyleLabel);
        Frame.add(fontpicker);
        Frame.add(fontlabel);
        Frame.add(fontspinner);
        Frame.add(fontcolor);
        Frame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource()==fontcolor){
          JColorChooser colorchooser = new JColorChooser();

          //To show the colors we need to show the Dialog Box (cuz when we are gonna click on color button It will
          // open a dialog box of all type of colors)

          Color color = colorchooser.showDialog(null,"Choose a Color!",Color.BLACK);
          textarea.setForeground(color);
      }

      if(e.getSource()==fontpicker){
          textarea.setFont(new Font((String)fontpicker.getSelectedItem(),Font.PLAIN,textarea.getFont().getSize()));
      }

      if(e.getSource()==open){

          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File("c:\\"));
          FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text Files","txt");
          fileChooser.setFileFilter(extensionFilter);

          int response = fileChooser.showOpenDialog(null);

          if(response == JFileChooser.APPROVE_OPTION){
              File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
              Scanner FileIn = null;
              try {
                  FileIn = new Scanner(file);
                  if(file.isFile()){
                      while(FileIn.hasNextLine()){
                        String line = FileIn.nextLine()+"\n";
                        textarea.append(line);
                      }
                  }
              } catch (FileNotFoundException ex) {
                  throw new RuntimeException(ex);
              }finally{
                  FileIn.close();
              }
          }

      }
      if(e.getSource()==save){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("c:\\"));

        int response = fileChooser.showSaveDialog(null);

          PrintWriter fileOut = null;

        if(response == JFileChooser.APPROVE_OPTION)

        file = new File(fileChooser.getSelectedFile().getAbsolutePath());
          try {
              fileOut = new PrintWriter(file);
              fileOut.println(textarea.getText());
          } catch (FileNotFoundException ex) {
              throw new RuntimeException(ex);
          }finally {
              fileOut.close();
          }

      }
      if(e.getSource()==exit){
            System.exit(0);
      }

    }
}
