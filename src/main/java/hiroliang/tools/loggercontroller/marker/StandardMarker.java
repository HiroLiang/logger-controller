package hiroliang.tools.loggercontroller.marker;

import lombok.Getter;
import org.slf4j.Marker;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StandardMarker implements Marker {

    private final String name;
    private final List<Marker> referenceList = new CopyOnWriteArrayList<>();
    @Getter
    private final LocalDateTime timestamp;
    @Getter
    private final Integer expirationDays;


    public StandardMarker(String name, LocalDateTime timestamp, Integer expirationDays) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("A marker name cannot be null");
        } else {
            this.name = name;
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("A marker timestamp cannot be null");
        } else {
            this.timestamp = timestamp;
        }
        this.expirationDays = expirationDays;
    }

    @Override
    public String getName() { return this.name; }

    @Override
    public void add(Marker reference) {
        if(reference == null) {
            throw new IllegalArgumentException("A reference cannot be null");
        } else if (!this.contains(reference)) {
            if (!reference.contains(this))
                this.referenceList.add(reference);
        }
    }

    @Override
    public boolean remove(Marker referenceToRemove) { return this.referenceList.remove(referenceToRemove); }

    @Override
    public boolean hasChildren() { return this.hasReferences(); }

    @Override
    public boolean hasReferences() { return !this.referenceList.isEmpty(); }

    @Override
    public Iterator<Marker> iterator() { return this.referenceList.iterator(); }

    @Override
    public boolean contains(Marker other) {
        if (other == null) {
            throw new IllegalArgumentException("Param marker cannot be null");
        } else if (this.equals(other)) {
            return true;
        } else {
            if(this.hasReferences()) {
                for (Marker reference : referenceList) {
                    if (reference.contains(other))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean contains(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param marker name cannot be null");
        } else if (this.name.equals(name)) {
            return true;
        } else {
            if(this.hasReferences()) {
                for (Marker reference : referenceList) {
                    if (reference.contains(name))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof Marker other)) {
            return false;
        } else {
            return this.name.equals(other.getName());
        }
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean isOverdue() {
        if (this.expirationDays == -1)
            return true;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = timestamp.plusDays(this.expirationDays);
        return now.isAfter(dueDate);
    }

    public StandardMarker addChild(Marker child) {
        this.add(child);
        return this;
    }

    public String toString() {
        if (!this.hasReferences()) {
            return this.getName();
        } else {
            Iterator<Marker> it = this.iterator();
            StringBuilder sb = new StringBuilder(this.getName());
            sb.append(' ').append("[ ");

            while(it.hasNext()) {
                Marker reference = it.next();
                sb.append(reference.getName());
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }

            sb.append(" ]");
            return sb.toString();
        }
    }
}
