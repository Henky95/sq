package hanze.nl.bussimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import hanze.nl.tijdtools.TijdFuncties;

public class Runner {

	private static HashMap<Integer,ArrayList<IBus>> busStart = new HashMap<Integer,ArrayList<IBus>>();
	private static ArrayList<IBus> actieveBussen = new ArrayList<IBus>();
	private static int interval=1000;
	private static int syncInterval=5;
	
	private static void addBus(int starttijd, IBus bus){
		ArrayList<IBus> bussen = new ArrayList<IBus>();
		if (busStart.containsKey(starttijd)) {
			bussen = busStart.get(starttijd);
		}
		bussen.add(bus);
		busStart.put(starttijd,bussen);
		bus.setbusID(starttijd);
	}
	
	private static int startBussen(int tijd){
		for (IBus bus : busStart.get(tijd)){
			actieveBussen.add(bus);
		}
		busStart.remove(tijd);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}
	
	public static void moveBussen(int nu){
		Iterator<IBus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			IBus bus = itr.next();
			boolean eindpuntBereikt = bus.move();
			if (eindpuntBereikt) {
				bus.sendLastETA(nu);
				itr.remove();
			}
		}		
	}

	public static void sendETAs(int nu){
		Iterator<IBus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			IBus bus = itr.next();
			bus.sendETAs(nu);
		}				
	}
	
	public static int initBussen(){
<<<<<<< HEAD
		List<IBus> busses = createBusses(1);
		addBusses(busses);

		busses = createBusses(2);
		addBusses(busses);
	}

	public static List<IBus> createBusses(int richting){
		IBus bus1=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, richting);
		IBus bus2=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, richting);
		IBus bus3=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, richting);
		IBus bus4=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, richting);
		IBus bus5=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, richting);
		IBus bus6=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, richting);
		IBus bus7=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, richting);
		IBus bus8=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, richting);
		IBus bus9=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, richting);
		IBus bus10=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, richting);

		return List.of(bus1,bus2,bus3,bus4,bus5,bus6,bus7,bus8,bus9,bus10);
	}

	public static void addBusses(List<IBus> busses){
		addBus(3, busses.get(0));
		addBus(5, busses.get(1));
		addBus(4, busses.get(2));
		addBus(6, busses.get(3));
		addBus(3, busses.get(4));
		addBus(5, busses.get(5));
		addBus(4, busses.get(6));
		addBus(6, busses.get(7));
		addBus(12, busses.get(8));
		addBus(10, busses.get(9));
	}

=======
		Bus bus1=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
		Bus bus2=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, 1);
		Bus bus3=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, 1);
		Bus bus4=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
		Bus bus5=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
		Bus bus6=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, 1);
		Bus bus7=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, 1);
		Bus bus8=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
		Bus bus9=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
		Bus bus10=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
		addBus(3, bus1);
		addBus(5, bus2);
		addBus(4, bus3);
		addBus(6, bus4);	
		addBus(3, bus5);
		addBus(5, bus6);
		addBus(4, bus7); 
		addBus(6, bus8);	
		addBus(12, bus9); 
		addBus(10, bus10);	
		Bus bus11=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
		Bus bus12=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, -1);
		Bus bus13=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, -1);
		Bus bus14=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
		Bus bus15=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
		Bus bus16=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, -1);
		Bus bus17=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, -1);
		Bus bus18=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
		Bus bus19=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
		Bus bus20=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
		addBus(3, bus11);
		addBus(5, bus12);
		addBus(4, bus13);
		addBus(6, bus14);	
		addBus(3, bus15);
		addBus(5, bus16);
		addBus(4, bus17); 
		addBus(6, bus18);	
		addBus(12, bus19); 
		addBus(10, bus20);	
		return Collections.min(busStart.keySet());
	}
	
>>>>>>> parent of b8e56f3 (wow)
	public static void main(String[] args) throws InterruptedException {
		int tijd=0;
		int counter=0;
		TijdFuncties tijdFuncties = new TijdFuncties();
		tijdFuncties.initSimulatorTijden(interval,syncInterval);
		int volgende = initBussen();
		while ((volgende>=0) || !actieveBussen.isEmpty()) {
			counter=tijdFuncties.getCounter();
			tijd=tijdFuncties.getTijdCounter();
			System.out.println("De tijd is:" + tijdFuncties.getSimulatorWeergaveTijd());
			volgende = (counter==volgende) ? startBussen(counter) : volgende;
			moveBussen(tijd);
			sendETAs(tijd);
			tijdFuncties.simulatorStep();
		}
	}
}
