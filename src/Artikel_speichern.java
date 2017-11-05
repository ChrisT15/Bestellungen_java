

import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Artikel_speichern extends Frame implements ActionListener, WindowListener
{
	/*Artikel_speichern ist ein Fenster, in das der Artikelname, die Artikelnummer und der Preis eines 
	 * Artikels eingegeben werden kann. Falls der Knopf "Speichern" gedrueckt wird, werden die eingegebenen Daten in einer
	 * Datenbank gespeichert */
	
	/* Knopf, Beschriftungen und Textfelder werden angelegt*/
	private Panel name_panel;
	private Label name_label;
	private TextField name_text;
	
	private Panel nummer_panel;
	private Label nummer_label;
	private TextField nummer_text;
		
	private Panel preis_panel;
	private Label preis_label;
	private TextField preis_text;
		
		
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
		
	private Button speichern;
		
	//Konstruktor
	public Artikel_speichern()
	{
			
		this.setTitle("Artikel speichern");
		this.setSize(400,400);
		this.setVisible(true);
			
			
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
		speichern = new Button("Speichern");
			
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Artikeldaten eingeben:");
			
		name_panel = new Panel();
		name_label = new Label("Artikelname:");
		name_text = new TextField();
			
		preis_panel = new Panel();
		preis_label = new Label("Preis:");
		preis_text = new TextField();
			
			
		nummer_panel = new Panel();
		nummer_label = new Label("Artikelnummer:");
		nummer_text = new TextField();
			
		//Komponenten platzieren
		name_panel.setLayout(new GridLayout(2,1));
		name_panel.add(name_label);
		name_panel.add(name_text);
			
		preis_panel.setLayout(new GridLayout(2,1));
		preis_panel.add(preis_label);
		preis_panel.add(preis_text);
			
			
		nummer_panel.setLayout(new GridLayout(2,1));
		nummer_panel.add(nummer_label);
		nummer_panel.add(nummer_text);
			
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
			
			
		this.setLayout(new GridLayout(7,1));
		this.add(ueberschrift_panel);
		this.add(name_panel);
		this.add(nummer_panel);
		this.add(preis_panel);
		this.add(speichern);
			
		//Event-Handler registrieren
		this.addWindowListener(this);
		speichern.addActionListener(this);
	}
		
	public void daten_speichern() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		//Verbing zu mysql wird hergestellt		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Bestellungen","root","");

		
			
		//die in die Textfelder des Fensters eingegebenen Daten werden in artikel_db gespeichert
		String ins_person = "INSERT INTO artikel_db (name,nummer,preis) VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(ins_person);
		pstmt.setString(1, name_text.getText());
		pstmt.setString(2, nummer_text.getText());
		pstmt.setString(3, preis_text.getText());
		pstmt.executeUpdate();
		pstmt.close();
			
		//Textfelder werden geleert
		name_text.setText("");
		nummer_text.setText("");
		preis_text.setText("");
					
	}
		
	//ActionListener-Implementierungen
		
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		/*Wenn der Knopf mit der Beschriftung "Speichern" angeklickt wird, dann
		 * wird die Funktion daten_speichern ausgefuehrt
		 */
		if(cmd.equals("Speichern"))
		{
			try
			{
				daten_speichern();
			}
			//Fehlermeldungen werden abgefangen
			catch(ClassNotFoundException ex)
			{
				System.out.println("ClassNotFoundException");
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
				System.out.println(ex.getCause());
			}
					
		}
	}
			
	//WindowListener-Implementierungen
	public void windowOpened(WindowEvent e)
	{
				
	}
			
	public void windowActivated(WindowEvent e)
	{
				
	}
			
	public void windowDeactivated(WindowEvent e)
	{
				
	}
			
	public void windowIconified(WindowEvent e)
	{
				
	}
			
	public void windowDeiconified(WindowEvent e)
	{
				
	}
			
	public void windowClosing(WindowEvent e)
	{
		//Fenster schliessen
		this.dispose();
	}
			
	public void windowClosed(WindowEvent e)
	{
				
	}
			
}


