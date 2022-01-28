package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;

public class BusETASender {

    public void sendETAs(int nu, IBus bus){
        Bericht bericht = new Bericht(bus.getLijn().name(),bus.getBedrijf().name(),bus.getBusID(),nu);

        if (bus.getBijHalte()) {
            ETA eta = new ETA(bus.getLijn().getHalte(bus.getHalteNummer()).name(),bus.getLijn().getRichting(bus.getHalteNummer()),0);
            bericht.ETAs.add(eta);
        }

        Halte.Positie eerstVolgende=bus.getLijn().getHalte(bus.getHalteNummer()+bus.getRichting()).getPositie();
        int tijdNaarHalte=bus.getTotVolgendeHalte()+nu;

        int i=0;
        for (i = bus.getHalteNummer()+bus.getRichting() ; !(i>=bus.getLijn().getLengte()) && !(i < 0); i=i+bus.getRichting() ){
            tijdNaarHalte+= bus.getLijn().getHalte(i).afstand(eerstVolgende);
            ETA eta = new ETA(bus.getLijn().getHalte(i).name(), bus.getLijn().getRichting(i),tijdNaarHalte);
            bericht.ETAs.add(eta);
            eerstVolgende=bus.getLijn().getHalte(i).getPositie();
        }

        bericht.eindpunt=bus.getLijn().getHalte(i-bus.getRichting()).name();
        sendBericht(bericht);
    }

    public void sendLastETA(int nu, IBus bus){
        Bericht bericht = new Bericht(bus.getLijn().name(),bus.getBedrijf().name(),bus.getBusID(),nu);
        String eindpunt = bus.getLijn().getHalte(bus.getHalteNummer()).name();
        ETA eta = new ETA(eindpunt,bus.getLijn().getRichting(bus.getHalteNummer()),0);
        bericht.ETAs.add(eta);
        bericht.eindpunt = eindpunt;
        sendBericht(bericht);
    }

    public void sendBericht(Bericht bericht){
        XStream xstream = new XStream();
        xstream.alias("Bericht", Bericht.class);
        xstream.alias("ETA", ETA.class);
        String xml = xstream.toXML(bericht);
        Producer producer = new Producer();
        producer.sendBericht(xml);
    }
}
