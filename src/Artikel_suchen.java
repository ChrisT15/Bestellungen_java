import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Artikel_suchen extends Frame implements ActionListener, WindowListener
{
	/*Arikel_suchen ist ein Fenster, in das der Artikelname eines Artikels eingegeben werden kann
	 Wenn der Knopf "Suchen" gedrueckt wird, werden alle Artikel, die diesen Artikelnamen haben, in einer
	 Datenbank gesucht*/
	
	//Knopf, Beschriftungen und Textfelder fuer das Fenster werden angelegt
	private Panel name_panel;
	private Label name_label;
	private TextField name_text;
	
	
	private Panel ueberschrift_panel;
	private Label ueberschrift_label;
	
	private Panel ergebnis_panel;
	private TextArea ergebnis_text;
	private Label ergebnis_label;
	
	private Button suchen;
	
	//Konstruktor
	public Artikel_suchen()
	{
		this.setTitle("Artikel suchen");
		this.setSize(800,500);
		this.setVisible(true);
		
		
		//Knopf, Beschriftungen und Textfelder werden zum Fenster hinzugefuegt
		suchen = new Button("Suchen");
		
		ueberschrift_panel = new Panel();
		ueberschrift_label = new Label("Artikelname eingeben:");
		
		name_panel = new Panel();
		name_label = new Label("Artikelname:");
		name_text = new TextField();
		
		
		ergebnis_panel = new Panel();
		ergebnis_text = new TextArea();
		ergebnis_text.setSize(1,5);
		ergebnis_label = new Label("Suchergebnisse:");
		
		
		
		//Komponenten platzieren
		name_panel.setLayout(new GridLayout(2,1));
		name_panel.add(name_label);
		name_panel.add(name_text);
		
		
		
		ueberschrift_panel.setLayout(new GridLayout(1,1));
		ueberschrift_panel.add(ueberschrift_label);
		
		ergebnis_panel.setLayout(new GridLayout(2,1));
		ergebnis_panel.add(ergebnis_label);
		ergebnis_panel.add(ergebnis_text);
		
		
		this.setLayout(new FlowLayout());
		this.add(ueberschrift_panel);
		this.add(name_panel);
		this.add(name_panel);
		this.add(suchen);
		this.add(ergebnis_panel);
		
		//Event-Handler registrieren
		this.addWindowListener(this);
		suchen.addActionListener(this);
	}
	
	public void artikel_suchen() throws ClassNotFoundException, SQLException
	{
		
		Class.forName("com.mysql.jdbc.Driver");
				
		//Verbindung zu mysql wird hergestellt
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Bestellungen","root","");
		Statement st = conn.createStatement();
		
		
		String name = name_text.getText();
		//Wenn ein Artikelname eingegeben wurde, werden in artikel_db alle Artikel mit diesem Namen gesucht
		if(!name.equals(""))
		{
			String sup = "SELECT name, nummer,preis FROM artikel_db where name = '" +
					name + "'";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			while(rs.next())
			{
				Artikel a = new Artikel(rs.getString("name"),rs.getString("nummer"),rs.getString("preis"));
				ergebnis = ergebnis + a.to_string() + System.getProperty("line.separator");
			
				
			}
			//Ergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis);
		}
		//falls kein Artikelname eingegeben wurde, werden alle Artikel ausgegeben
		else
		{
			String sup = "SELECT name, nummer,preis FROM artikel_db";
		
		
			ResultSet rs = st.executeQuery(sup);
			String ergebnis="";
			while(rs.next())
			{
				Artikel a = new Artikel(rs.getString("name"),rs.getString("nummer"),rs.getString("preis"));
				ergebnis = ergebnis + a.to_string() + System.getProperty("line.separator");
			
				
			}
			//Ergebnisse werden in einem Textfeld ausgegeben
			ergebnis_text.setText(ergebnis);
		}
		
		
		//Textfeld wird geleert
		name_text.setText("");
		
			
	}
	
	
		

	//ActionListener-Implementierungen
	
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			/*Wenn der Knopf mit der Beschriftung "Suchen" angeklickt wird,
			wird die Funktion artikel_suchen ausgefuehrt*/
			if(cmd.equals("Suchen"))
			{
				
				
				try
				{
					artikel_suchen();
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
