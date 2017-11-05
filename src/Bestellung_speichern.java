
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Bestellung_speichern extends Frame implements ActionListener, WindowListener
{
	/* Bestellung_speichern ist ein Fenster, in das die Kundennummer einer Person, welche eine Bestellung ausfuehrt, die Artikelnummer
	 * des Artikels, der bestellt wird, und die Anzahl, wie oft der Artikel bestellt wird, eingegeben werden koennen. 
	 * Wenn der Knopf "Speichern" gedrueckt wird, dann werden diese Daten in einer Datenbank gespeichert*/
	
	//Knopf, Beschriftungen und Textfeld fuer das Fenster werden angelegt
	private Panel knr_panel;
	private Label knr_label;
	private TextField knr_text;
	
	private Panel anr_panel;
	private Label anr_label;
	private TextField anr_text;
		
	private Panel anzahl_panel;
	private Label anzahl_label;
	private TextField anzahl_text;
		
		
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
		
	private Button speichern;
		
	//Konstruktor
	public Bestellung_speichern()
	{
		this.setTitle("Bestellung speichern");
		this.setSize(400,400);
		this.setVisible(true);
			
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
			
		speichern = new Button("Speichern");
			
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Bestellungsdaten eingeben:");
			
		knr_panel = new Panel();
		knr_label = new Label("Kundennummer:");
		knr_text = new TextField();
			
		anr_panel = new Panel();
		anr_label = new Label("Artikelnummer:");
		anr_text = new TextField();
			
			
		anzahl_panel = new Panel();
		anzahl_label = new Label("Anzahl:");
		anzahl_text = new TextField();
			
		//Komponenten platzieren
		knr_panel.setLayout(new GridLayout(2,1));
		knr_panel.add(knr_label);
		knr_panel.add(knr_text);
			
		anr_panel.setLayout(new GridLayout(2,1));
		anr_panel.add(anr_label);
		anr_panel.add(anr_text);
			
			
		anzahl_panel.setLayout(new GridLayout(2,1));
		anzahl_panel.add(anzahl_label);
		anzahl_panel.add(anzahl_text);
			
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
			
			
		this.setLayout(new GridLayout(7,1));
		this.add(ueberschrift_panel);
		this.add(knr_panel);
		this.add(anr_panel);
		this.add(anzahl_panel);
		this.add(speichern);
			
		//Event-Handler registrieren
		this.addWindowListener(this);
		speichern.addActionListener(this);
	}
		
	public void daten_speichern() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
			
		//Verbindung zu mysql wird hergestellt
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Bestellungen","root","");
		
			
		
		//die ins Fenster eingegebenen Daten werden in der Tabelle bestellungen_db gespeichert
		String ins_person = "INSERT INTO bestellungen_db (kundennummer,artikelnummer,anzahl) VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(ins_person);
		pstmt.setString(1, knr_text.getText());
		pstmt.setString(2, anr_text.getText());
		pstmt.setString(3, anzahl_text.getText());
		pstmt.executeUpdate();
		pstmt.close();
			
		//Textfelder werden geleert
		knr_text.setText("");
		anr_text.setText("");
		anzahl_text.setText("");
					
	}
		
	//ActionListener-Implementierungen
		
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		//Wenn der Knopf "Speichern" gedrueckt wird, dann wird die Funktion daten_speichern ausgefuehrt
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



