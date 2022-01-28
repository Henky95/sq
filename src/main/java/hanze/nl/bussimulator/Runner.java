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

	private static BusETASender busETASender = new BusETASender();

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
				busETASender.sendLastETA(nu,bus);
				itr.remove();
			}
		}
	}

	public static void sendETAs(int nu){
		Iterator<IBus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			IBus bus = itr.next();
			busETASender.sendETAs(nu, bus);
		}				
	}
	
	public static int initBussen(){
		List<IBus> busses = createBusses(1);
		addBusses(busses);

		busses = createBusses(2);
		addBusses(busses);

		return Collections.min(busStart.keySet());
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
