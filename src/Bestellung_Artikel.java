
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Bestellung_Artikel extends Frame implements ActionListener, WindowListener
{
	/*Bestellung_Artikel ist ein Fenster, in das eine Artikelnummer eines Artikels eingegeben werden kann.
	 * Wird der Knopf "Suchen" gedrueckt, so werden alle Bestellungen gesucht, in denen dieser Artikel bestellt wurde
	 * Pro Bestellung wird der Name der Person, welche die Bestellung ausgefuehrt hat, die Anzahl des bestellten Artikels
	 * und der Gesamtpreis angezeigt. Ausserdem wird der Preis aller gefundenen Bestellungen angezeigt*/
	
	//Knopf, Beschriftungen und Textfelder fuer das Fenster werden angelegt
	private Panel anr_panel;
	private Label anr_label;
	private TextField anr_text;
	
	
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
	
	private Panel ergebnis_panel;
	private TextArea ergebnis_text;
	private Label ergebnis_label;
	
	private Button suchen;
	
	
	//Konstruktor
	public Bestellung_Artikel()
	{
		this.setTitle("Bestellungen eines Artikels suchen");
		this.setSize(800,500);
		this.setVisible(true);
		
		
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
		suchen = new Button("Suchen");
		
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Artikelnummer eines Artikels eingeben:");
		
		anr_panel = new Panel();
		anr_label = new Label("Artikelnummer:");
		anr_text = new TextField();
		
		
		ergebnis_panel = new Panel();
		ergebnis_text = new TextArea();
		ergebnis_text.setSize(1,5);
		ergebnis_label = new Label("Suchergebnisse:");
		
		
		
		//Komponenten platzieren
		anr_panel.setLayout(new GridLayout(2,1));
		anr_panel.add(anr_label);
		anr_panel.add(anr_text);
		
		
		
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
		
		ergebnis_panel.setLayout(new GridLayout(2,1));
		ergebnis_panel.add(ergebnis_label);
		ergebnis_panel.add(ergebnis_text);
		
		
		this.setLayout(new FlowLayout());
		this.add(ueberschrift_panel);
		this.add(anr_panel);
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
		
		
		String anr = anr_text.getText();
		/*Wenn eine Artikelnummer eingegeben wurde, werden alle Bestellungen gesucht, in welcher der Artikel
		bestellt wurde. Pro Bestellung werden Name der Person, welche bestellt hat, die Anzahl des Artikels und der
		Gesamtpreis angezeigt. Ausserdem wird der Gesamtpreis aller gefundenen Bestellungen ausgegeben*/
		if(!anr.equals(""))
		{
			String supr = "select preis from artikel_db where nummer = '" + anr + "'";
			ResultSet rs = st.executeQuery(supr);
			float preis=0;
			//Preis des ausgewaehlten Artikels wird ermittelt
			while(rs.next())
			{
				preis = rs.getFloat("preis");
			}
			String suba = "select personen_db.nachname, personen_db.vorname, bestellungen_db.anzahl from personen_db " +
						"left outer join bestellungen_db on personen_db.kundennummer = bestellungen_db.kundennummer " +
						"where bestellungen_db.artikelnummer like '" + anr + "'";
		
			rs = st.executeQuery(suba);
			String ergebnis="";
			float summe = 0;
			float gesamtpreis=1;
			while(rs.next())
			{
				gesamtpreis = rs.getFloat("bestellungen_db.anzahl") *preis;
				gesamtpreis = (float) (Math.round(gesamtpreis * 100.0)/ 100.0);
				summe += gesamtpreis;
				
				ergebnis = ergebnis + rs.getString("personen_db.nachname") + " " + rs.getString("personen_db.vorname") 
						+ " Anzahl: " + rs.getString("bestellungen_db.anzahl") + " Gesamtpreis: " + gesamtpreis  + System.getProperty("line.separator");
			
				
			}
			//Summe wird auf zwei Nachkommastellen gerundet
			summe = (float) Math.round(summe*100)/100;
			//die Suchergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis + System.getProperty("line.separator") + "Summe: " + Float.toString(summe));
		}
		else
		{
			ergebnis_text.setText("Es wurde keine Artikelnummer eingegeben");
		}
		
		
		
		anr_text.setText("");
		
			
	}
	
	
		

	//ActionListener-Implementierungen
	
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			//Wenn der Knopf "Suchen" angeklickt wird, wird die Funktion suchen ausgefuehrt
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


