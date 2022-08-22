package net.intelie.challenges;

import java.util.Iterator;

public class EventIteratorSolution implements EventIterator {

    private final String eventType;
    private Iterator<Long> iterator;

    private Long currentEvent 	= null;
    private boolean toMove = false;

    public EventIteratorSolution(String type, Iterator<Long> iterator) {
        this.iterator = iterator;
        this.eventType = type;
    }

    @Override
    public boolean moveNext() {

        toMove = true;

        if(iterator.hasNext()) {
            currentEvent = iterator.next();
            return true;
        }
        toMove = false;
        return false;
    }

    @Override
    public Event current() throws IllegalStateException {

        if (isIllegal()) {
            throw new IllegalStateException();
        }
        return new Event(eventType, currentEvent);
    }

    @Override
    public void remove() throws IllegalStateException {

        if (isIllegal()) {
            throw new IllegalStateException();
        }
        iterator.remove();
    }

    @Override
    public void close() throws Exception {
        iterator = null;
    }

    private boolean isIllegal() {
        if (currentEvent == null || !toMove) {
            return true;
    }
        return false;

    }
}
