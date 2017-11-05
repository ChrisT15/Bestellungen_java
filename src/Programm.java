import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Programm extends Frame implements ActionListener,WindowListener
{
	/* Programm ist ein Fenster, das ein Menue enthaelt, ueber das man weitere Fenster auswaehlen kann*/
	
	//Bestandteile des Menues werden angelegt
	private MenuBar mb;
	private Menu m_person; 
	private Menu m_artikel;
	private Menu m_bestellung;
	private MenuItem person_suchen; 
	private MenuItem person_speichern; 
	private MenuItem artikel_suchen;
	private MenuItem artikel_speichern;
	private MenuItem bestellung_speichern;
	private MenuItem bestellung_person;
	private MenuItem bestellung_artikel;
	
	
	//Konstruktor
	public Programm()
	{
		//Titel setzen
		this.setTitle("Bestellungen");
		this.setSize(600,400);
		this.setVisible(true);
		this.addWindowListener(this);
			
		//Menue erstellen
		mb = new MenuBar();
		m_person = new Menu("Person bearbeiten");
		m_artikel = new Menu("Artikel bearbeiten");
		m_bestellung = new Menu("Bestellungen bearbeiten");
		
		person_suchen = new MenuItem("Person suchen");
		person_speichern = new MenuItem("Person speichern");
		
		artikel_suchen = new MenuItem("Artikel suchen");
		artikel_speichern = new MenuItem("Artikel speichern");
		
		bestellung_speichern = new MenuItem("Bestellung speichern");
		bestellung_person = new MenuItem("Bestellungen einer Person");
		bestellung_artikel = new MenuItem("Bestellungen eines Artikels");
		
		m_person.add(person_suchen);
		m_person.add(person_speichern);
		m_artikel.add(artikel_suchen);
		m_artikel.add(artikel_speichern);
		m_bestellung.add(bestellung_speichern);
		m_bestellung.add(bestellung_person);
		m_bestellung.add(bestellung_artikel);
		
		
		mb.add(m_person);
		mb.add(m_artikel);
		mb.add(m_bestellung);
		this.setMenuBar(mb);
		
		//Event Handler registrieren
		person_suchen.addActionListener(this);
		person_speichern.addActionListener(this);
		
		artikel_suchen.addActionListener(this);
		artikel_speichern.addActionListener(this);
		
		bestellung_speichern.addActionListener(this);
		bestellung_person.addActionListener(this);
		bestellung_artikel.addActionListener(this);
		
		try
		{
			this.db_anlegen();
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
	
	private void db_anlegen() throws ClassNotFoundException, SQLException
	{
			Class.forName("com.mysql.jdbc.Driver");
		
			//Verbindung zu mysql wird hergestellt
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost","root","");
			Statement st = conn.createStatement();
			//Datenbank Bestellungen anlegen, falls diese noch nicht vorhanden ist	
			st.execute("CREATE DATABASE IF NOT EXISTS Bestellungen");
			//Datenbank Bestellungen wird ausgewaehlt
			st.execute("USE Bestellungen");
			
			//die Tabelle personen_db wird angelegt, falls diese noch nicht existiert
			String ct = "CREATE TABLE IF NOT EXISTS personen_db " +
						"(nr INT AUTO_INCREMENT PRIMARY KEY, " +
						"vorname VARCHAR(30), " +
						"nachname VARCHAR(30), " +
						"strasse VARCHAR(30), " +
						"wohnort VARCHAR(30), " +
						"kundennummer VARCHAR(30)) ";
			st.execute(ct);
			
			//Tabelle artikel_db anlegen, falls diese noch nicht existiert
			ct = "CREATE TABLE IF NOT EXISTS artikel_db (" +
						"nr INT AUTO_INCREMENT PRIMARY KEY," +
						"name VARCHAR(30)," +
						"nummer VARCHAR(30)," +
						"preis FLOAT) ";
			st.execute(ct);
			
			//die Tabelle bestellungen_db wird angelegt, falls sie noch nicht existiert
			ct = "CREATE TABLE IF NOT EXISTS bestellungen_db ( " + 
						"nr INT AUTO_INCREMENT PRIMARY KEY, " +
						"kundennummer VARCHAR(30), " +
						"artikelnummer VARCHAR(30), " +
						"anzahl FlOAT) "; 
			st.execute(ct);
			
	}
	
	//ActionListener-Implementierungen
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		//Je nachdem, welcher Menuepunkt angeklickt wird, werden verschiedene Fenster geoeffnet
		if(cmd.equals("Person suchen"))
		{
			Person_suchen su = new Person_suchen();
		}
		if(cmd.equals("Person speichern"))
		{
			Person_speichern sup = new Person_speichern();
		}
		if(cmd.equals("Artikel speichern"))
		{
			Artikel_speichern sa = new Artikel_speichern();
		}
		if(cmd.equals("Artikel suchen"))
		{
			Artikel_suchen sua = new Artikel_suchen();
		}
		if(cmd.equals("Bestellung speichern"))
		{
			Bestellung_speichern sb = new Bestellung_speichern();
		}
		if(cmd.equals("Bestellungen einer Person"))
		{
			Bestellung_person bp = new Bestellung_person();
		}
		if(cmd.equals("Bestellungen eines Artikels"))
		{
			Bestellung_Artikel ba = new Bestellung_Artikel();
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
		//Programm beenden
		System.exit(0);
	}
	
}
