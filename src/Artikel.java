
public class Artikel extends Objekt
{
	/* Artikel erbt name und nummer von Objekt. Hier ist nummer die Kundennummer und name der Artikelname*/
	private String preis;
	
	//Konstruktor
	public Artikel(String artikelname, String artikelnummer, String preis)
	{
		this.name = artikelname;
		this.nummer = artikelnummer;
		this.preis = preis;
	}
	
	//Alle Daten von Artikel werden ausgegeben
	public String to_string()
	{
		String ausgabe = "";
		if(!this.name.equals(""))
		{
			ausgabe = ausgabe + "Artikelname: " + this.name;
		}
		if(!this.nummer.equals(""))
		{
			ausgabe = ausgabe + " Artikelnummer: " + this.nummer;
		}
		if(!this.preis.equals(""))
		{
			ausgabe = ausgabe + " Einzelpreis: " + this.preis;
		}
		
		return ausgabe;
	}
}
