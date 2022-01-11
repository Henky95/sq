public interface IBus {
    public void setbusID(int starttijd);

    public void naarVolgendeHalte();

    public boolean halteBereikt();

    public void start();

    public boolean move();

    public void sendETAs(int nu);

    public void sendLastETA(int nu);

    public void sendBericht(Bericht bericht);
}