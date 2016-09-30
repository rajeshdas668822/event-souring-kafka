package org.springboot.eventbus.model;


import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/28/2016.
 */
public class PersistentEvent implements Comparable<PersistentEvent>{

    private static final String MEMKEY_VERSION = "version";
    private static final String MEMKEY_EVENTTYPE = "type";
    private static final String MEMKEY_ID = "id";
    private static final String MEMKEY_AGGREGATE_ID = "aggregateId";

    private UUID id;
    private UUID aggregateId;
    private Long version;
    private String eventType;
    private Map<String, Object> entries;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, Object> entries) {
        this.entries = entries;
    }

    @Override
    public int compareTo(final PersistentEvent event) {
        int idCompare = this.id.compareTo(event.getId());

        if (idCompare == 0) {
            return (int) (this.version - event.version);
        } else {
            return idCompare;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistentEvent that = (PersistentEvent) o;

        return id.equals(that.id);

    }

    @Override
    public String toString() {
        return "PersistentEvent{" +
                "id=" + id +
                ", aggregateId=" + aggregateId +
                ", version=" + version +
                ", eventType='" + eventType + '\'' +
                ", entries=" + entries +
                '}';
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
