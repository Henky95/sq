package hanze.nl.bussimulator;

public interface IBus {
    public Bedrijven getBedrijf();
    public Lijnen getLijn();
    public int getHalteNummer();
    public int getTotVolgendeHalte();
    public int getRichting();
    public boolean getBijHalte();
    public String getBusID();

    public void setbusID(int starttijd);

    public void naarVolgendeHalte();

    public boolean halteBereikt();

    public void start();

    public boolean move();
}