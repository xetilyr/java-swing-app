public class PerformanceData {
    private int numberN;
    private double timeSeconds;
    private long resultNumber;

    public PerformanceData(int numberN, double timeSeconds, long resultNumber) {
        this.numberN = numberN;
        this.timeSeconds = timeSeconds;
        this.resultNumber = resultNumber;
    }

    // Геттеры
    public int getNumberN() { return numberN; }
    public double getTimeSeconds() { return timeSeconds; }
    public long getResultNumber() { return resultNumber; }
}
