package net.intelie.challenges;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class EventStoreSolution implements EventStore{
    ConcurrentHashMap<String, ConcurrentSkipListSet<Long>> events = new ConcurrentHashMap<>();

    @Override
    public void insert(Event event) {

        ConcurrentSkipListSet<Long> typeList;
        String type = event.type();
        Long timestamp = event.timestamp();

        if (exists(type)) {
            typeList = events.get(type);
            typeList.add(timestamp);
        }
        else {
            typeList = new ConcurrentSkipListSet<>();
            typeList.add(timestamp);

            events.put(type, typeList);
        }
    }

    @Override
    public void removeAll(String type) {
        if (!exists(type)) {
            return;
        }

        events.get(type).clear();
        events.remove(type);
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        Iterator<Long> iterator = events.get(type).subSet(startTime, endTime).iterator();

        return new EventIteratorSolution(type, iterator);
    }

    private boolean exists(String type) {
        if (events.containsKey(type)) {
            return true;
        }

        return false;
    }
}
