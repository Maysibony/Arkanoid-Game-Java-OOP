package game;

public class Counter {
    int counter;
    /**
     * Constructor. initialize to number.
     */
    public Counter(int number) {
        this.counter = number;
    }
    /** add number to current count.
     * @param number
     */
    public void increase(int number){
        counter += number;
    }
    /** subtract number from current count.
     * @param number
     */
    public void decrease(int number){
        counter -= number;
    }
    /** get current count.
     * @return
     */
    public int getValue() {
        return counter;
    }
}
