package hanze.nl.bussimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.sun.tools.javac.util.List;
import hanze.nl.tijdtools.TijdFuncties;

public class Runner {

	private static HashMap<Integer,ArrayList<Bus>> busStart = new HashMap<Integer,ArrayList<Bus>>();
	private static ArrayList<Bus> actieveBussen = new ArrayList<Bus>();
	private static int interval=1000;
	private static int syncInterval=5;
	
	private static void addBus(int starttijd, Bus bus){
		ArrayList<Bus> bussen = new ArrayList<Bus>();
		if (busStart.containsKey(starttijd)) {
			bussen = busStart.get(starttijd);
		}
		bussen.add(bus);
		busStart.put(starttijd,bussen);
		bus.setbusID(starttijd);
	}
	
	private static int startBussen(int tijd){
		for (Bus bus : busStart.get(tijd)){
			actieveBussen.add(bus);
		}
		busStart.remove(tijd);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}
	
	public static void moveBussen(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			boolean eindpuntBereikt = bus.move();
			if (eindpuntBereikt) {
				bus.sendLastETA(nu);
				itr.remove();
			}
		}		
	}

	public static void sendETAs(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			bus.sendETAs(nu);
		}				
	}
	
	public static int initBussen(){
		List<Bus> busses = createBusses(1);
		addBusses(busses);

		busses = createBusses(2);
		addBusses(busses);
	}

	public static List<Bus> createBusses(int richting){
		Bus bus1=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, richting);
		Bus bus2=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, richting);
		Bus bus3=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, richting);
		Bus bus4=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, richting);
		Bus bus5=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, richting);
		Bus bus6=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, richting);
		Bus bus7=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, richting);
		Bus bus8=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, richting);
		Bus bus9=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, richting);
		Bus bus10=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, richting);

		return List.of(bus1,bus2,bus3,bus4,bus5,bus6,bus7,bus8,bus9,bus10);
	}

	public static void addBusses(List<Bus> busses){
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
