import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Person_suchen extends Frame implements ActionListener, WindowListener
{
	/* Person_suchen ist ein Fenster, in das der Vorname und der Nachname einer Person eingegeben werden kann
	 * Wenn der Knopf "Suchen" gedrueckt wird, dann werden in einer Datenbank alle Personen gesucht, die den
	 * eingegebenen Vornamen oder Nachnamen haben*/
	
	//Knopf, Beschriftungen und Textfelder fuer das Fenster werden angelegt
	private Panel vorname_panel;
	private Label vorname_label;
	private TextField vorname_text;
	
	private Panel nachname_panel;
	private Label nachname_label;
	private TextField nachname_text;
	
	
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
	
	private Panel ergebnis_panel;
	private TextArea ergebnis_text;
	private Label ergebnis_label;
	
	private Button suchen;
	
	//Konstruktor
	public Person_suchen()
	{
		this.setTitle("Person suchen");
		this.setSize(800,500);
		this.setVisible(true);
		
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
		
		suchen = new Button("Suchen");
		
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Personendaten eingeben:");
		
		vorname_panel = new Panel();
		vorname_label = new Label("Vorname:");
		vorname_text = new TextField();
		
		nachname_panel = new Panel();
		nachname_label = new Label("Nachname:");
		nachname_text = new TextField();
		
		ergebnis_panel = new Panel();
		ergebnis_text = new TextArea();
		ergebnis_text.setSize(1,5);
		ergebnis_label = new Label("Suchergebnisse:");
		
		
		
		//Komponenten platzieren
		vorname_panel.setLayout(new GridLayout(2,1));
		vorname_panel.add(vorname_label);
		vorname_panel.add(vorname_text);
		
		nachname_panel.setLayout(new GridLayout(2,1));
		nachname_panel.add(nachname_label);
		nachname_panel.add(nachname_text);
		
		
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
		
		ergebnis_panel.setLayout(new GridLayout(2,1));
		ergebnis_panel.add(ergebnis_label);
		ergebnis_panel.add(ergebnis_text);
		
		
		this.setLayout(new FlowLayout());
		this.add(ueberschrift_panel);
		this.add(vorname_panel);
		this.add(nachname_panel);
		this.add(suchen);
		this.add(ergebnis_panel);
		
		//Event-Handler registrieren
		this.addWindowListener(this);
		suchen.addActionListener(this);
	}
	
	public void person_suchen() throws ClassNotFoundException, SQLException
	{
		
		Class.forName("com.mysql.jdbc.Driver");
	
		//Verbindung zu mysql wird hergestellt
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Bestellungen","root","");
		Statement st = conn.createStatement();
		
		
		String vorname = vorname_text.getText();
		String nachname = nachname_text.getText();
		
		//Wenn nur ein Vorname eingegeben wurde, werden alle Personen in personen_db gesucht, welche diesen Vornamen haben
		if(!vorname.equals("") && nachname.equals(""))
		{
			String sup = "SELECT vorname, nachname,strasse, wohnort, kundennummer FROM personen_db where vorname = '" +
					vorname + "'";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			while(rs.next())
			{
				Person p = new Person(rs.getString("vorname"),rs.getString("nachname"),rs.getString("strasse"),rs.getString("wohnort"),rs.getString("kundennummer"));
				ergebnis = ergebnis + p.to_string() + System.getProperty("line.separator");
			
				
			}
			//Suchergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis);
		}
		
		//Wenn nur ein Nachname eingegeben wurde, werden alle Personen in personen_db gesucht, die diesen Nachnamen haben
		if(vorname.equals("") && !nachname.equals(""))
		{
			String sup = "SELECT vorname, nachname,strasse, wohnort, kundennummer FROM personen_db where nachname = '" +
					nachname + "'";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			while(rs.next())
			{
				Person p = new Person(rs.getString("vorname"),rs.getString("nachname"),rs.getString("strasse"),rs.getString("wohnort"),rs.getString("kundennummer"));
				ergebnis = ergebnis + p.to_string() + System.getProperty("line.separator");
			
			}
			//Suchergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis);
		}
		/*Wenn Vorname und Nachname eingegeben wurden, dann werden in der Tabelle personen_db alle Personen gesucht, die diesen
		Vornamen und Nachnamen haben */
		if(!vorname.equals("") && !nachname.equals(""))
		{
			String sup = "SELECT vorname, nachname,strasse, wohnort, kundennummer FROM personen_db where nachname = '" +
					nachname + "' and vorname = '" + vorname + "'";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			while(rs.next())
			{
				Person p = new Person(rs.getString("vorname"),rs.getString("nachname"),rs.getString("strasse"),rs.getString("wohnort"),rs.getString("kundennummer"));
				ergebnis = ergebnis + p.to_string() + System.getProperty("line.separator");
			
			}
			//Suchergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis);
		}
		//Wenn weder Vorname noch Nachname eingegeben wurden, dann werden alle Personen in personen_db angezeigt	
		if(vorname.equals("") && nachname.equals(""))
		{
			String sup = "SELECT vorname, nachname,strasse, wohnort, kundennummer FROM personen_db ";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			while(rs.next())
			{
				Person p = new Person(rs.getString("vorname"),rs.getString("nachname"),rs.getString("strasse"),rs.getString("wohnort"),rs.getString("kundennummer"));
				ergebnis = ergebnis + p.to_string() + System.getProperty("line.separator");
			
			}
			//Ergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis);
		}
		
		//Textfelder werden geleert
		vorname_text.setText("");
		nachname_text.setText("");
		
			
	}
	
	
		

	//ActionListener-Implementierungen
	
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			//Wenn der Knopf "Suchen" betaetigt wird, dann wird die Funktion person_suchen ausgefuehrt
			if(cmd.equals("Suchen"))
			{
				
				
				try
				{
					person_suchen();
				}
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
