package main.comparator;

import net.fortuna.ical4j.model.component.VEvent;

import java.util.Comparator;

public class VEventComparator implements Comparator<VEvent> {
    @Override
    public int compare(VEvent o1, VEvent o2) {
        return o1.getStartDate().getDate().compareTo(o2.getStartDate().getDate());
    }
}
