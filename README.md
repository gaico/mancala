# Mancala

## Opdracht en ontwerp
Uit de opdracht: *The objective of this assignment is to show how you’d model the game and its logic. The only requirement is that the game of Mancala is implemented with all its rules.*

### Ontwerp en model
Het spel is te beschrijven als een opeenvolging van toestanden waarin het spel zich bevind. Bij zo een toestand zijn een aantal spelelementen van belang. Twee spelers (Player) die per persoon 7 vakjes (Pot) hebben, waarin zich de stenen bevinden, waarvan een de mancala is. Daarnaast zijn het spel en zijn regels (MancalaGame) belangrijk.

Het real world spelbord is een aaneenschakeling van vakjes (Pot) die een ketting vormen. Deze vakjes zijn te verdelen in twee helften. Elke speler (Player) kan een zet doen vanuit een van die helften.

Elke vakje kent zijn opvolgend en tegenoverliggend vakje en het aantal stenen dat het bevat. Elke speler kent zijn vakjes en weet of hij aan de beurt is en/of gewonnen heeft. Het spel kent de regels, de spelers en de vakjes. 


## Techniek en oplevering

### Keuzes
Ik heb ervoor gekozen om een stateless service te maken. Het idee hierachter is dat de state niet bijgehouden hoeft te worden om het spel te kunnen spelen. De interface accepteert een toestand van een spel en een zet en vertaalt deze naar een nieuwe toestand van het spel en retourneert dit. State bijhouden is dus niet nodig. Mocht er later de wens zijn om dat wel te doen dan kan dit gemakkelijk ingebouwd worden, bij voorkeur in de client.

Deze keuze is gemaakt, omdat het de hele applicatie zo licht mogelijk houdt. Geen state, dus geen sessies op de server en geen noodzaak voor security. Dit leidt tot minder geheugengebruik, maar ook tot minder code en dus minder onderhoud.

##### Framework
Er is gekozen voor Spring-Boot, omdat hierin snel een applicatie met een http interface te bouwen is. 

##### Interface
 Ik heb niet gekozen voor een json interface, maar voor een plain text interface. Dit lijkt misschien een rare keus omdat json de de facto standaard is, maar het bespaart de overhead van json in de communicatie en de plain text interface is simpel genoeg gemakkelijk begrepen te kunnen worden.
 
 De interface bestaat uit een string van 16 komma gescheiden integers en beschrijft de toestand van het spel. 
 
 De eerste integer is het nummer van de speler die aan de beurt is (1 of 2).
 
  De tweede is het nummer van de speler die gewonnen heeft (0, 1 of 2). Bij het sturen naar de applicatie moet deze 0 zijn.
 
 De anderen zijn het aantal stenen per pot, waarbij de eerste staat voor de eerste pot van speler 1, links onder, en de laatste voor de mancala van speler 2.
 De zet wordt los meegegeven als parameter en is een getal van 1 tot 6. Dit getal staat voor de pot van de speler die aan de beurt is, geteld vanaf de mancala van zijn opponent.
 
 Voorbeeld
1. Request URL: http://localhost:9090/?move=4
2. Request Method:PUT
3. Request payload: 1,0,4,4,4,4,4,4,0,4,4,4,4,4,4,0
4. Response: 2,0,0,5,5,5,5,4,0,4,4,4,4,4,4,0

### User interface
Er wordt een user interface opgeleverd. Deze is echter bedoeld om de applicatie te testen en moet niet gezien worden als een productierijpe front-end.

### Todo
<del>GameState zou vervangen moeten worden door translator class, want GameState hoort niet in het model. Hierdoor zou vanuit de service deze nieuwe translator gebruikt kunnen worden. De logica in de constructor in MancalaGame die de shorthandGamestate als input heeft, kan dan verplaatst worden naar deze translator.</del>

## Bouwen en runnen

### Uitgangspunten
Recente versies van Java en Maven zijn geinstalleerd.

### Bouwen
Om de applicatie te bouwen kan Windows het **build.bat** gebuikt worden.
Op andere platforms kan in de hoofdirectory het commando *mvn clean install* gegeven worden.

### Runnen
Om de applicatie te runnen kan op Windows het script **start.bat** gebuikt worden.
Op andere platforms kan in de hoofdirectory het commando *java -jar -Dserver.port=9090 target\mancala-0.0.1-SNAPSHOT.jar* gegeven worden.

De applicatie start op poort 9090. Mocht deze in gebruik zijn dan kan de poort worden aangepast door het script of commando aan te passen door 9090 te vervangen door een vrij poortnummer.

De test front-end kan daarna geopend worden in de browser: http://localhost:9090/index.html. Mocht het poortnummer aangepast zijn, doe dat dan ook hier. De plain text interface is aan te roepen op http://localhost:8090/?move=<nummer tussen 1 en 6\>