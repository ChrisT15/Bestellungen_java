
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Bestellung_person extends Frame implements ActionListener, WindowListener
{
	/* Bestellung_person ist ein Fenster, in dem man die Kundennummer einer Person eingeben kann
	 * Wenn der Knopf "Speichern" gedrueckt wird, dann werden alle Bestellungen, die die Person
	 * mit dieser Kundennummer bestellt hat, gesucht 
	 * Pro Bestellung werden der Name des bestellten Artikels, der Einzelpreis des Artikels, die Anzahl,
	 * wie oft der Artikel bestellt wurde  und der
	 * Gesamtpreis der Bestellung angezeigt. Ausserdem wird der Preis von allen gefundenen Bestellungen
	 * ausgegeben
	 */
	
	//Knopf, Beschriftungen und Textfelder fuer das Fenster werden angelegt
	private Panel knr_panel;
	private Label knr_label;
	private TextField knr_text;
	
	
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
	
	private Panel ergebnis_panel;
	private TextArea ergebnis_text;
	private Label ergebnis_label;
	
	private Button suchen;
	
	//Konstruktor
	public Bestellung_person()
	{
		this.setTitle("Bestellungen einer Person suchen");
		this.setSize(800,500);
		this.setVisible(true);
		
		
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
		suchen = new Button("Suchen");
		
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Kundennummer einer Person eingeben:");
		
		knr_panel = new Panel();
		knr_label = new Label("Kundennummer:");
		knr_text = new TextField();
		
		
		ergebnis_panel = new Panel();
		ergebnis_text = new TextArea();
		ergebnis_text.setSize(1,5);
		ergebnis_label = new Label("Suchergebnisse:");
		
		
		
		//Komponenten platzieren
		knr_panel.setLayout(new GridLayout(2,1));
		knr_panel.add(knr_label);
		knr_panel.add(knr_text);
		
		
		
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
		
		ergebnis_panel.setLayout(new GridLayout(2,1));
		ergebnis_panel.add(ergebnis_label);
		ergebnis_panel.add(ergebnis_text);
		
		
		this.setLayout(new FlowLayout());
		this.add(ueberschrift_panel);
		this.add(knr_panel);
		this.add(suchen);
		this.add(ergebnis_panel);
		
		//Event-Handler registrieren
		this.addWindowListener(this);
		suchen.addActionListener(this);
	}
	
	public void suchen() throws ClassNotFoundException, SQLException
	{
		
		Class.forName("com.mysql.jdbc.Driver");
				
		//Verbindung zu mysql wird hergestellt
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Bestellungen","root","");
		Statement st = conn.createStatement();
		
		String knr = knr_text.getText();
		/* Falls eine Kundennummer eingegeben wurde, werden alle Bestellungen gesucht, welche von der Person mit dieser Kundennummer
		 * ausgefuehrt wurden. Pro Bestellung wird der Name des bestellten Artikels, der Preis des bestellten Artikels, die Anzahl des
		 * bestellten Artikels und der Gesamtpreis der Bestellung aufgelistet. Ausserdem wird der Preis aller gefundenen Bestellungen
		 * ausgegeben */
		if(!knr.equals(""))
		{
			String sup = "select artikel_db.name, artikel_db.preis, bestellungen_db.anzahl, round(artikel_db.preis*bestellungen_db.anzahl,2) " +
						"as gesamtpreis from artikel_db left outer join bestellungen_db on bestellungen_db.artikelnummer = artikel_db.nummer " +
						"where bestellungen_db.kundennummer = '" + knr + "' order by artikel_db.name";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			//in summe wird der Preis von allen gefundenen Bestellungen gespeichert
			float summe = 0;
			float gesamtpreis;
			while(rs.next())
			{
				gesamtpreis = rs.getFloat("gesamtpreis");
				summe += gesamtpreis;
				ergebnis = ergebnis + rs.getString("artikel_db.name") + " Einzelpreis: " + rs.getString("artikel_db.preis") 
						+ " Anzahl: " + rs.getString("bestellungen_db.anzahl") + " Gesamtpreis: " + rs.getString("gesamtpreis") + System.getProperty("line.separator");
			
				
			}
			//summe wird auf zwei Nachkommastellen gerundet
			summe = (float) Math.round(summe * 100)/100;
			//das Suchergebnis wird in ein Textfeld geschrieben
			ergebnis_text.setText(ergebnis + System.getProperty("line.separator") + "Summe: " + Float.toString(summe));
		}
		else
		{
			ergebnis_text.setText("Es wurde keine Kundennummer eingegeben");
		}
		
		
		//Textfeld wird geleert
		knr_text.setText("");
		
			
	}
	
	
		

	//ActionListener-Implementierungen
	
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			//Wenn der Knopf "Suchen" betaetigt wurde, wird die Funktion suchen ausgefuehrt
			if(cmd.equals("Suchen"))
			{
				
				
				try
				{
					suchen();
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

