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
