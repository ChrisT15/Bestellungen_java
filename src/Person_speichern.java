import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Person_speichern extends Frame implements ActionListener, WindowListener
{
	/* Person_speichern ist ein Fenster, in dem man den Vornamen und Nachnamen einer Person, die Strasse und den Wohnort,
	 * in denen diese Person wohnt, und die Kundennummer dieser Person eingeben kann
	 * Wenn der Knopf "Speichern" gedrueckt wird, dann werden diese Daten in einer Datenbank gespeichert*/
	
	//Knopf, Beschriftungen und Textfelder fuer das Fenster werden angelegt
	private Panel vorname_panel;
	private Label vorname_label;
	private TextField vorname_text;
	
	private Panel nachname_panel;
	private Label nachname_label;
	private TextField nachname_text;
	
	private Panel strasse_panel;
	private Label strasse_label;
	private TextField strasse_text;
	
	private Panel wohnort_panel;
	private Label wohnort_label;
	private TextField wohnort_text;
	
	private Panel nummer_panel;
	private Label nummer_label;
	private TextField nummer_text;
	
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
	
	private Button speichern;
	
	
	//Konstruktor
	public Person_speichern()
	{
		this.setTitle("Person speichern");
		this.setSize(400,400);
		this.setVisible(true);
		
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
		
		speichern = new Button("Speichern");
		
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Personendaten eingeben:");
		
		vorname_panel = new Panel();
		vorname_label = new Label("Vorname:");
		vorname_text = new TextField();
		
		nachname_panel = new Panel();
		nachname_label = new Label("Nachname:");
		nachname_text = new TextField();
		
		strasse_panel = new Panel();
		strasse_label = new Label("Strasse:");
		strasse_text = new TextField();
		
		wohnort_panel = new Panel();
		wohnort_label = new Label("Wohnort:");
		wohnort_text = new TextField();
		
		nummer_panel = new Panel();
		nummer_label = new Label("Kundennummer:");
		nummer_text = new TextField();
		
		//Komponenten platzieren
		vorname_panel.setLayout(new GridLayout(2,1));
		vorname_panel.add(vorname_label);
		vorname_panel.add(vorname_text);
		
		nachname_panel.setLayout(new GridLayout(2,1));
		nachname_panel.add(nachname_label);
		nachname_panel.add(nachname_text);
		
		strasse_panel.setLayout(new GridLayout(2,1));
		strasse_panel.add(strasse_label);
		strasse_panel.add(strasse_text);
		
		wohnort_panel.setLayout(new GridLayout(2,1));
		wohnort_panel.add(wohnort_label);
		wohnort_panel.add(wohnort_text);
		
		nummer_panel.setLayout(new GridLayout(2,1));
		nummer_panel.add(nummer_label);
		nummer_panel.add(nummer_text);
		
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
		
		
		this.setLayout(new GridLayout(7,1));
		this.add(ueberschrift_panel);
		this.add(vorname_panel);
		this.add(nachname_panel);
		this.add(strasse_panel);
		this.add(wohnort_panel);
		this.add(nummer_panel);
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
		
		//die Daten, die in das Fenster eingegeben wurden, werden in der Tabelle personen_db eingegeben
		String ins_person = "INSERT INTO personen_db (vorname,nachname,strasse,wohnort,kundennummer) VALUES (?, ?, ?, ?,?)";
		PreparedStatement pstmt = conn.prepareStatement(ins_person);
		pstmt.setString(1, vorname_text.getText());
		pstmt.setString(2, nachname_text.getText());
		pstmt.setString(3, strasse_text.getText());
		pstmt.setString(4, wohnort_text.getText());
		pstmt.setString(5, nummer_text.getText());
		pstmt.executeUpdate();
		pstmt.close();
		
		//Textfelder werden geleert
		vorname_text.setText("");
		nachname_text.setText("");
		strasse_text.setText("");
		wohnort_text.setText("");
		nummer_text.setText("");
				
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
