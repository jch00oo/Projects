public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

<<<<<<< HEAD
    // YOUR CODE HERE
    @Override
    public Date nextDate() {
        int newDay,newMonth,newYear;
        if (dayOfMonth == getMonthLength(month)) {
            newDay = 1;
            newMonth = month + 1;
        } else {
            newDay = dayOfMonth + 1;
            newMonth = month;
        }
        if (dayOfYear()==365){
            newYear = year + 1;
            newDay = 1;
            newMonth=1;
        } else{
            newYear=year;
        }
        return new GregorianDate(newYear, newMonth, newDay);
    }

=======

    public GregorianDate nextDate() {
        int m = month;
        int dOM = dayOfMonth;
        int y = year;
        int dOY = dayOfYear();

        if (dOY == 365) {
            m = 1;
            dOM = 1;
            y += 1;
        } else if (dOM == getMonthLength(m) && m < 12) {
            m += 1;
            dOM = 1;
        } else {
            dOM += 1;
        }
        GregorianDate result = new GregorianDate(y, m, dOM);
        return result;
    }
>>>>>>> f7bba3a1d0780abfb8dd94611308f425d5211328

    @Override
    public int dayOfYear() {
        int precedingMonthDays = 0;
        for (int m = 1; m < month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + dayOfMonth;
        //like out of 365 days of the year aight
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }
}