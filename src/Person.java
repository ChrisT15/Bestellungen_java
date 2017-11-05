
public class Person extends Objekt
{
	/* von der Klasse Objekt erbt Person die Attribute name und nummer
	 * nummer ist hier die Kundennummer und name ist hier der Nachname */
	 
	private String vorname;
	private String strasse;
	private String wohnort;
	
	//Konstruktor
	public Person(String vorname,String nachname, String strasse, String wohnort, String kundennummer)
	{
		this.vorname = vorname;
		this.name = nachname;
		this.strasse = strasse;
		this.wohnort = wohnort;
		this.nummer = kundennummer;
	}
	
	//alle Daten einer Person werden ausgegeben
	public String to_string()
	{
		String ausgabe = "";
		if(!this.vorname.equals(""))
		{
			ausgabe = ausgabe + this.vorname;
		}
		if(!this.name.equals(""))
		{
			ausgabe = ausgabe + " " + this.name;
		}
		if(!this.strasse.equals(""))
		{
			ausgabe = ausgabe + " Strasse: " + this.strasse;
		}
		
		if(!this.wohnort.equals(""))
		{
			ausgabe = ausgabe + " Wohnort: " + this.wohnort;
		}
		
		if(!this.nummer.equals(""))
		{
			ausgabe = ausgabe + " Kundennummer: " + this.nummer;
		}
		
		
		return ausgabe;
	}
}
