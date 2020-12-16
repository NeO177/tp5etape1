package main;

import main.comparator.VEventComparator;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ParserException {
        // https://ical4j.github.io/ical4j-user-guide/examples/#parsing-a-calendar-file
        InputStream fin = Main.class.getClassLoader().getResourceAsStream("basic.ics");

        CalendarBuilder builder = new CalendarBuilder();

        Calendar calendar = builder.build(fin);

        // https://ical4j.github.io/ical4j-user-guide/examples/#filtering-events
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        today.clear(java.util.Calendar.MINUTE);
        today.clear(java.util.Calendar.SECOND);

        // create a period starting now with a duration of one (1) day..
        Period period = new Period(new DateTime(today.getTime()), new Dur(99999999, 0, 0, 0));
        Filter filter = new Filter(new PeriodRule(period));

        Collection<VEvent> eventsToday = filter.filter(calendar.getComponents(Component.VEVENT));
        List eventsTodayL = new ArrayList(eventsToday);



        // Comparaison

        Collections.sort(eventsTodayL, new VEventComparator());

        // Formatage de la date au format que je souhaite
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

        for (Object o : eventsTodayL) {
            VEvent e = (VEvent) o;
            System.out.println(
                    String.format(
                            "%s : %s",
                            formater.format(e.getStartDate().getDate()),
                            e.getSummary().getValue()
                    )
            );
        }

    }
}
